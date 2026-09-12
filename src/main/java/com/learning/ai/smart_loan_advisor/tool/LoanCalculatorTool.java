package com.learning.ai.smart_loan_advisor.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

/**
 * Created by Ranjit Soni on 12-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@Component
public class LoanCalculatorTool {

    @Tool(description = """
            Calculate monthly EMI for a loan.
            Use this tool when the user provides loan amount, annual interest rate,
            and loan tenure in years or months.
            The calculation must be performed by this tool instead of guessing.
            """, name = "calculateEmi")
    public Record calculateEmi(double loanAmount, double annualInterestRate, int tenureInYears) {
         record LoanCalculationResult(
                 double loanAmount,
                 double annualInterestRate,
                 int tenureInYears,
                 double emi,
                 double totalInterest,
                 double totalPayment){};

        int totalMonths = tenureInYears * 12;

        double monthlyRate =
                annualInterestRate / 12 / 100;

        double emi;

        if (monthlyRate == 0) {
            emi = loanAmount / totalMonths;
        } else {
            emi = loanAmount
                    * monthlyRate
                    * Math.pow(1 + monthlyRate, totalMonths)
                    / (Math.pow(1 + monthlyRate, totalMonths) - 1);
        }

        double totalPayment = emi * totalMonths;
        double totalInterest = totalPayment - loanAmount;

        return new LoanCalculationResult(
                loanAmount,
                annualInterestRate,
                tenureInYears,
                round(emi),
                round(totalInterest),
                round(totalPayment)
        );
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
