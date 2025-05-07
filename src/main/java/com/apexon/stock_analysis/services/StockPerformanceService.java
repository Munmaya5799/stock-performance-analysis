package com.apexon.stock_analysis.services;


import com.apexon.stock_analysis.MockDataUtil;
import com.apexon.stock_analysis.dto.MarketDataResponseDto;
import com.apexon.stock_analysis.dto.StockPerformanceDto;
import com.apexon.stock_analysis.dto.TradeDetails;
import com.apexon.stock_analysis.dto.TradeDto;
import com.apexon.stock_analysis.exceptions.AppException;
import com.apexon.stock_analysis.util.CalcDateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockPerformanceService {

    final RestTemplate restTemplate;
    @Value("${trade.api.base-url}")
    private String tradeApiBaseUrl;

    @Value("${trade.api.path}")
    private String tradeApiPath;

    @Value("${market.api.base-url}")
    private String marketDataApiBaseUrl;

    @Value("${market.api.path}")
    private String marketDataApiPath;

    @Autowired
    public StockPerformanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<StockPerformanceDto> getStockPerfDetails(String clientId, String inputDate) {
        String url = tradeApiBaseUrl + tradeApiPath + clientId;
        String jsonResponse = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<StockPerformanceDto> stockPerformanceList = new ArrayList<>();
        String marketDataUrl = marketDataApiBaseUrl + marketDataApiPath;

        try {
            // Parse trade details
            Map<String, TradeDetails> tradeMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, TradeDetails>>() {});
            List<String> stockSymbols = new ArrayList<>(tradeMap.keySet());

            // Parse input date and calculate the previous working day
            LocalDate parsedDate = LocalDate.parse(inputDate);
            LocalDate previousWorkingDay = CalcDateUtil.getPreviousWorkingDay(parsedDate);

            String marketDataUrlWithDate = marketDataUrl + "/" + previousWorkingDay;


            // Fetch market data
            ResponseEntity<List<MarketDataResponseDto>> response = restTemplate.exchange(
                    marketDataUrlWithDate,
                    HttpMethod.POST,
                    new HttpEntity<>(stockSymbols),
                    new ParameterizedTypeReference<List<MarketDataResponseDto>>() {}
            );

            // Map stock symbols to their respective close prices
            Map<String, Double> stockPrices = response.getBody().stream()
                    .collect(Collectors.toMap(
                            MarketDataResponseDto::getSymbol,
                            MarketDataResponseDto::getClose
                    ));

            // Process each trade and calculate stock performance
            for (Map.Entry<String, TradeDetails> entry : tradeMap.entrySet()) {
                String stockSymbol = entry.getKey();
                TradeDetails tradeDetails = entry.getValue();
                double purchasePrice = tradeDetails.getPurchaseValue() / tradeDetails.getRemainingQuantity();
                double currentMarketPrice = stockPrices.getOrDefault(stockSymbol, 0.0);

                double totalInvestment = tradeDetails.getAverageBuyPrice() * tradeDetails.getExecutedBuyQuantity();
                double currentValue = tradeDetails.getRemainingQuantity() * currentMarketPrice;
                double profitLoss = tradeDetails.getRealizedProfit();
                double profitLossPercentage = (profitLoss / totalInvestment) * 100;

                StockPerformanceDto stockPerformanceDto = StockPerformanceDto.builder()
                        .symbol(stockSymbol)
                        .currentPrice(currentMarketPrice)
                        .purchasePrice(purchasePrice)
                        .sharesOwned(tradeDetails.getRemainingQuantity())
                        .totalInvestment(totalInvestment)
                        .currentValue(currentValue)
                        .profitLoss(profitLoss)
                        .profitLossPercentage(profitLossPercentage)
                        .build();

                stockPerformanceList.add(stockPerformanceDto);
            }

        } catch (JsonProcessingException e) {
            throw new AppException("Error processing JSON response", e);
        } catch (Exception e) {
            throw new AppException("An unexpected error occurred while fetching stock performance details", e);
        }

        return stockPerformanceList;
    }



    // below method is used to get the stock details of a client using mocked data
    public List<StockPerformanceDto> getStockDetails(String clientIds) {

        String mockTradeJson = MockDataUtil.getTradeMockJson();
        List<String> mockMarketDataList = MockDataUtil.getMockMarketDataList();

        ObjectMapper objectMapper = new ObjectMapper();
        List<StockPerformanceDto> stockPerformanceList = new ArrayList<>();
        try {
            List<TradeDto> trades = objectMapper.readValue(mockTradeJson, new TypeReference<List<TradeDto>>() {
            });
            for (TradeDto trade : trades) {
                if (clientIds.contains(String.valueOf(trade.getClientId()))) {
                    for (String marketDataJson : mockMarketDataList) {
                        JsonNode rootNode = objectMapper.readTree(marketDataJson);
                        String marketSymbol = rootNode.path("Meta Data").path("2. Symbol").asText();

                        if (trade.getStockSymbol().equalsIgnoreCase(marketSymbol)) {
                            JsonNode marketDataNode = rootNode.path("Time Series (Daily)").path("2025-04-29");
                            double currentPrice = Double.parseDouble(marketDataNode.path("4. close").asText());

                            double totalInvestment = trade.getPrice() * trade.getQuantity();
                            double currentValue = currentPrice * trade.getQuantity();
                            double profitLoss = currentValue - totalInvestment;
                            double profitLossPercentage = (profitLoss / totalInvestment) * 100;

                            StockPerformanceDto stockPerformanceDto = StockPerformanceDto.builder()
                                    .symbol(trade.getStockSymbol())
                                    .currentPrice(currentPrice)
                                    .purchasePrice(trade.getPrice())
                                    .sharesOwned(trade.getQuantity())
                                    .totalInvestment(totalInvestment)
                                    .currentValue(currentValue)
                                    .profitLoss(profitLoss)
                                    .profitLossPercentage(profitLossPercentage)
                                    .build();

                            stockPerformanceList.add(stockPerformanceDto);
                            break;
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            throw new AppException("Error processing JSON data", e);
        } catch (Exception e) {
            throw new AppException("An unexpected error occurred while fetching stock details", e);
        }

        return stockPerformanceList;
    }
}
