package com.apexon.stock_analysis.services;


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

    public List<StockPerformanceDto> getStockDetails(String clientId) {
        String mockJson = "[\n" +
                "  {\n" +
                "    \"clientId\": 12345,\n" +
                "    \"stockSymbol\": \"IBM\",\n" +
                "    \"orderType\": \"LIMIT\",\n" +
                "    \"quantity\": 100,\n" +
                "    \"price\": 145.3,\n" +
                "    \"timeInForce\": \"GTC\",\n" +
                "    \"status\": \"EXECUTED\",\n" +
                "    \"transactionType\": \"BUY\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"clientId\": 12346,\n" +
                "    \"stockSymbol\": \"GOOGL\",\n" +
                "    \"orderType\": \"MARKET\",\n" +
                "    \"quantity\": 50,\n" +
                "    \"price\": 2725.5,\n" +
                "    \"timeInForce\": \"DAY\",\n" +
                "    \"status\": \"REQUEST\",\n" +
                "    \"transactionType\": \"SELL\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"clientId\": 12347,\n" +
                "    \"stockSymbol\": \"AMZN\",\n" +
                "    \"orderType\": \"LIMIT\",\n" +
                "    \"quantity\": 30,\n" +
                "    \"price\": 3450.2,\n" +
                "    \"timeInForce\": \"DAY\",\n" +
                "    \"status\": \"CANCELLED\",\n" +
                "    \"transactionType\": \"BUY\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"clientId\": 12348,\n" +
                "    \"stockSymbol\": \"TSLA\",\n" +
                "    \"orderType\": \"LIMIT\",\n" +
                "    \"quantity\": 200,\n" +
                "    \"price\": 720.8,\n" +
                "    \"timeInForce\": \"GTC\",\n" +
                "    \"status\": \"EXECUTED\",\n" +
                "    \"transactionType\": \"SELL\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"clientId\": 12349,\n" +
                "    \"stockSymbol\": \"MSFT\",\n" +
                "    \"orderType\": \"MARKET\",\n" +
                "    \"quantity\": 150,\n" +
                "    \"price\": 299.9,\n" +
                "    \"timeInForce\": \"DAY\",\n" +
                "    \"status\": \"REQUEST\",\n" +
                "    \"transactionType\": \"BUY\"\n" +
                "  }\n" +
                "]";

        String mockMarketData = "{\n" +
                "    \"Meta Data\": {\n" +
                "        \"1. Information\": \"Daily Prices (open, high, low, close) and Volumes\",\n" +
                "        \"2. Symbol\": \"IBM\",\n" +
                "        \"3. Last Refreshed\": \"2025-04-29\",\n" +
                "        \"4. Output Size\": \"Compact\",\n" +
                "        \"5. Time Zone\": \"US/Eastern\"\n" +
                "    },\n" +
                "    \"Time Series (Daily)\": {\n" +
                "        \"2025-04-29\": {\n" +
                "            \"1. open\": \"237.0000\",\n" +
                "            \"2. high\": \"239.9800\",\n" +
                "            \"3. low\": \"236.1400\",\n" +
                "            \"4. close\": \"239.3900\",\n" +
                "            \"5. volume\": \"3426508\"\n" +
                "        },\n" +
                "        \"2025-04-28\": {\n" +
                "            \"1. open\": \"232.8600\",\n" +
                "            \"2. high\": \"236.6300\",\n" +
                "            \"3. low\": \"232.0700\",\n" +
                "            \"4. close\": \"236.1600\",\n" +
                "            \"5. volume\": \"3653461\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        List<StockPerformanceDto> stockPerformanceList = new ArrayList<>();
        try {
            List<TradeDto> trades = objectMapper.readValue(mockJson, new TypeReference<List<TradeDto>>() {});
            JsonNode marketDataNode = objectMapper.readTree(mockMarketData).path("Time Series (Daily)").path("2025-04-29");

            double currentPrice = Double.parseDouble(marketDataNode.path("4. close").asText());

            for (TradeDto trade : trades) {
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
                }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return stockPerformanceList;
    }
}
