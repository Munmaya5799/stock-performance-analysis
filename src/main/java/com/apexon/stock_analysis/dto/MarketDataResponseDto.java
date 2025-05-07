package com.apexon.stock_analysis.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class MarketDataResponseDto {
    private Long id;
    private String symbol;
    private Date date;
    private double open;
    private double high;
    private double low;
    private double close;
    private int volume;

}
