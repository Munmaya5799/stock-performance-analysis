package com.apexon.stock_analysis.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class TradeDetails {


    private int executedSellQuantity;
    private int remainingQuantity;
    private double purchaseValue;
    private double realizedProfit;
    private int executedTradeCount;
    private int executedBuyQuantity;
    private double averageBuyPrice ;
}
