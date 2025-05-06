package com.apexon.stock_analysis;

import java.util.List;

public class MockDataUtil {

    public static String getTradeMockJson() {
        return "[\n" +
                "  {\n" +
                "    \"clientId\": 12345,\n" +
                "    \"stockSymbol\": \"IBM\",\n" +
                "    \"quantity\": 100,\n" +
                "    \"price\": 145.3\n" +
                "  },\n" +
                "  {\n" +
                "    \"clientId\": 12346,\n" +
                "    \"stockSymbol\": \"GOOGL\",\n" +
                "    \"quantity\": 50,\n" +
                "    \"price\": 2725.5\n" +
                "  },\n" +
                "  {\n" +
                "    \"clientId\": 12347,\n" +
                "    \"stockSymbol\": \"AMZN\",\n" +
                "    \"quantity\": 30,\n" +
                "    \"price\": 3450.2\n" +
                "  },\n" +
                "  {\n" +
                "    \"clientId\": 12348,\n" +
                "    \"stockSymbol\": \"TSLA\",\n" +
                "    \"quantity\": 200,\n" +
                "    \"price\": 720.8\n" +
                "  },\n" +
                "  {\n" +
                "    \"clientId\": 12349,\n" +
                "    \"stockSymbol\": \"MSFT\",\n" +
                "    \"quantity\": 150,\n" +
                "    \"price\": 299.9\n" +
                "  }\n" +
                "]";
    }
    public static List<String> getMockMarketDataList() {
        return List.of(
                "{\n" +
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
                        "}",
                "{\n" +
                        "    \"Meta Data\": {\n" +
                        "        \"1. Information\": \"Daily Prices (open, high, low, close) and Volumes\",\n" +
                        "        \"2. Symbol\": \"GOOGL\",\n" +
                        "        \"3. Last Refreshed\": \"2025-04-29\",\n" +
                        "        \"4. Output Size\": \"Compact\",\n" +
                        "        \"5. Time Zone\": \"US/Eastern\"\n" +
                        "    },\n" +
                        "    \"Time Series (Daily)\": {\n" +
                        "        \"2025-04-29\": {\n" +
                        "            \"1. open\": \"2700.0000\",\n" +
                        "            \"2. high\": \"2730.0000\",\n" +
                        "            \"3. low\": \"2690.0000\",\n" +
                        "            \"4. close\": \"2725.5000\",\n" +
                        "            \"5. volume\": \"1500000\"\n" +
                        "        },\n" +
                        "        \"2025-04-28\": {\n" +
                        "            \"1. open\": \"2680.0000\",\n" +
                        "            \"2. high\": \"2705.0000\",\n" +
                        "            \"3. low\": \"2675.0000\",\n" +
                        "            \"4. close\": \"2695.0000\",\n" +
                        "            \"5. volume\": \"1400000\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}"
        );
    }
}

