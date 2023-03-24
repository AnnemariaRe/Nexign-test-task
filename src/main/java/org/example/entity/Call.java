package org.example.entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class Call {
    private String callType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration duration;
    private BigDecimal cost;

    public Call(String callType, LocalDateTime startTime, LocalDateTime endTime) {
        this.callType = callType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = Duration.between(startTime, endTime);
        this.cost = BigDecimal.ZERO;
    }

    public void calculateCost(long timeSpent, String tariffType) {
        long callDuration = duration.getSeconds();
        long totalTime = 0;
        long extraTime = 0;

        switch (tariffType) {
            case "06":
                totalTime = timeSpent + callDuration;
                extraTime = totalTime - (300 * 60);

                if (extraTime <= 0) {
                    this.cost = new BigDecimal(0);
                } else {
                    if (extraTime >= callDuration) {
                        this.cost = new BigDecimal(Math.ceil(callDuration / 60 + 0.9));
                    } else {
                        this.cost = new BigDecimal(Math.ceil(extraTime / 60 + 0.9));
                    }
                }
                break;
            case "03":
                this.cost = new BigDecimal(Math.ceil(callDuration / 60 + 0.9) * 1.5);
                break;
            case "11":
                if (this.callType == "02") {
                    this.cost = new BigDecimal(0);
                } else if (this.callType == "01") {
                    totalTime = timeSpent + callDuration;
                    extraTime = totalTime - (100 * 60);

                    if (extraTime <= 0) {
                        this.cost = new BigDecimal(Math.ceil(callDuration / 60 + 0.9) * 0.5);
                    } else {
                        if (extraTime >= callDuration) {
                            this.cost = new BigDecimal(Math.ceil(callDuration / 60 + 0.9) * 1.5);
                        } else {
                            this.cost = new BigDecimal((Math.ceil(extraTime / 60 + 0.9) * 1.5)
                                    + ((Math.ceil((callDuration - extraTime) / 60 + 0.9) * 0.5)));
                        }
                    }
                }
                break;
        }
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getCallType() {
        return callType;
    }
}
