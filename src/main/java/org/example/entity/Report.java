package org.example.entity;

import java.math.BigDecimal;
import java.util.List;

public class Report {
    private String phoneNumber;
    private String tariffIndex;
    private List<Call> calls;
    private BigDecimal totalCost;

    public Report(String phoneNumber, String tariffIndex, List<Call> calls, BigDecimal totalCost) {
        this.phoneNumber = phoneNumber;
        this.tariffIndex = tariffIndex;
        this.calls = calls;
        this.totalCost = totalCost;
    }

    public void saveToFile() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTariffIndex() {
        return tariffIndex;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}
