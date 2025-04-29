package com.apexon.stock_analysis.controller;

import com.apexon.stock_analysis.dto.InvestorDto;
import com.apexon.stock_analysis.services.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {

    private final InvestorService investorService;

    @Autowired
    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<InvestorDto> getInvestorDetails(@PathVariable String clientId) {
        InvestorDto investorDto = investorService.getInvestorDetails(clientId);
        return ResponseEntity.ok(investorDto);
    }
}
