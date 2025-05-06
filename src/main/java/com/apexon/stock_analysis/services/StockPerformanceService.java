package com.apexon.stock_analysis.services;


import com.apexon.stock_analysis.MockDataUtil;
import com.apexon.stock_analysis.dto.StockPerformanceDto;
import com.apexon.stock_analysis.dto.TradeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockPerformanceService {

    final RestTemplate restTemplate;
    @Value("${trade.api.base-url}")
    private String apiBaseUrl;

    @Value("${trade.api.path}")
    private String tradeApiPath;

    @Autowired
    public StockPerformanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*public TradeDto[] getTrades(String clientId) {
        String url = apiBaseUrl + tradeApiPath + clientId;
        return restTemplate.getForObject(url, TradeDto[].class);

    }*/

    public List<StockPerformanceDto> getStockDetails(List<String> clientIds) {

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
                                    .averagePurchasePrice(trade.getPrice())
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
            e.printStackTrace();
        }

        return stockPerformanceList;
    }
}
