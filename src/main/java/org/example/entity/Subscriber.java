package org.example.entity;

import java.util.List;

public class Subscriber {
    private String phoneNumber;
    private List<Call> calls;
    private String tariffIndex;

    public Subscriber(String phoneNumber, List<Call> calls, String tariffIndex) {
        this.phoneNumber = phoneNumber;
        this.calls = calls;
        this.tariffIndex = tariffIndex;
    }

    public void calculateCallsCost() {
        long timeSpent = 0;

        switch (tariffIndex) {
            case "06":
                // Тариф "Безлимит 300"
                for (Call call: calls) {
                    call.calculateCost(timeSpent, tariffIndex);
                    timeSpent += call.getDuration().getSeconds();
                }
                break;
            case "03":
                // Тариф "Поминутный"
                for (Call call: calls) {
                    call.calculateCost(timeSpent, tariffIndex);
                }
                break;
            case "11":
                // Тариф "Обычный"
                for (Call call: calls) {
                    call.calculateCost(timeSpent, tariffIndex);
                    if (call.getCallType() == "01") timeSpent += call.getDuration().getSeconds();
                }
                break;
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public String GetTariffIndex() {
        return tariffIndex;
    }
}
