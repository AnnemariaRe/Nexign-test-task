package org.example.entity;

import java.time.LocalDateTime;

public class CallDataRecord {
    private String phoneNumber;
    private String callType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tariffIndex;

    public CallDataRecord(String callType, String phoneNumber, LocalDateTime startTime, LocalDateTime endTime, String tariffIndex) {
        this.phoneNumber = phoneNumber;
        this.callType = callType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tariffIndex = tariffIndex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCallType() {
        return callType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getTariffIndex() {
        return tariffIndex;
    }
}
