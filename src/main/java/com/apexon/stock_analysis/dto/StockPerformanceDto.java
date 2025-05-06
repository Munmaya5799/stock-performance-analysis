package com.apexon.stock_analysis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockPerformanceDto {
    private String symbol;
    private double currentPrice;
    private double averagePurchasePrice;
    private int sharesOwned;
    private double totalInvestment;
    private double currentValue;
    private double profitLoss;
    private double profitLossPercentage;

}
