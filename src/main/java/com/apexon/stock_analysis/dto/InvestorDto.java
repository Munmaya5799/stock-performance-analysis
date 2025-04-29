package com.apexon.stock_analysis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestorDto {

    String id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String address;
    String accountNumber;
    String accountType;
    String kycStatus;
    String riskToleranceLevel;
    boolean emailNotifications;
    boolean smsNotifications;

}
