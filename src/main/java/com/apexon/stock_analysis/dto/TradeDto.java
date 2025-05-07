package com.apexon.stock_analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
public class TradeDto {
    private long clientId;
    private String stockSymbol;
    private int quantity;
    private double price;

    public TradeDto(){

    }
}
