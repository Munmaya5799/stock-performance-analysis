package com.apexon.stock_analysis.services;

import com.apexon.stock_analysis.dto.InvestorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InvestorService {

    final RestTemplate restTemplate;
    @Value("${investor.api.base-url}")
    String baseUrl;

    @Autowired
    public InvestorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InvestorDto getInvestorDetails(String clientId) {
        String url = baseUrl + "/investors/" + clientId;
        return restTemplate.getForObject(url, InvestorDto.class, clientId);
    }
}
