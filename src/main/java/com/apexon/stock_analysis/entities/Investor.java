package com.apexon.stock_analysis.entities;


import com.apexon.stock_analysis.enums.AccountType;
import com.apexon.stock_analysis.enums.KYCStatus;
import com.apexon.stock_analysis.enums.RiskToleranceLevel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "investors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private KYCStatus kycStatus;

    @Enumerated(EnumType.STRING)
    private RiskToleranceLevel riskToleranceLevel;

    private boolean emailNotifications;
    private boolean smsNotifications;
}
