package org.example;

import org.example.entity.Call;
import org.example.entity.CallDataRecord;
import org.example.entity.Subscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String CDR_FILE_NAME = "cdr.txt";

    public static void main(String[] args) {
        List<CallDataRecord> cdrList = new ArrayList<>();

        try {
            cdrList = CDRParser.parse(CDR_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, List<CallDataRecord>> cdrMap = new HashMap<>();
        for (CallDataRecord cdr : cdrList) {
            if (!cdrMap.containsKey(cdr.getPhoneNumber())) {
                cdrMap.put(cdr.getPhoneNumber(), new ArrayList<>());
            }
            cdrMap.get(cdr.getPhoneNumber()).add(cdr);
        }

        List<Subscriber> subscribers = new ArrayList<>();

        for (Map.Entry<String, List<CallDataRecord>> entry : cdrMap.entrySet()) {
            String phoneNumber = entry.getKey();
            List<Call> calls = new ArrayList<>();
            for (CallDataRecord cdr: entry.getValue()) {
                calls.add(new Call(cdr.getCallType(), cdr.getStartTime(), cdr.getEndTime()));
            }

            String tariffIndex = entry.getValue().get(0).getTariffIndex();

            Subscriber subscriber = new Subscriber(phoneNumber, calls, tariffIndex);
            subscriber.calculateCallsCost();
            subscribers.add(subscriber);
        }

        ReportGenerator.generate(subscribers);

    }
}