package com.apexon.stock_analysis.controller;

import com.apexon.stock_analysis.dto.StockPerformanceDto;
import com.apexon.stock_analysis.dto.TradeDto;
import com.apexon.stock_analysis.services.StockPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/investors")
public class StockPermanceController {

    private final StockPerformanceService stockPerformanceService;

    @Autowired
    public StockPermanceController(StockPerformanceService stockPerformanceService) {
        this.stockPerformanceService = stockPerformanceService;
    }

    @GetMapping("/clients/{clientIds}")
    public ResponseEntity<List<StockPerformanceDto>> getStockDetails(@PathVariable String clientId) {


        List<StockPerformanceDto> stockDtoList = stockPerformanceService.getStockDetails(clientId);
        return ResponseEntity.ok(stockDtoList);
    }
}
