package org.example;

import org.example.entity.CallDataRecord;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CDRParser {
    public static List<CallDataRecord> parse(String fileName) throws IOException {
        List<CallDataRecord> cdrList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String line;

        InputStream inputStream = CDRParser.class.getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");

            String callType = fields[0].trim();
            String phoneNumber = fields[1].trim();
            LocalDateTime startTime = LocalDateTime.parse(fields[2].trim(), formatter);
            LocalDateTime endTime = LocalDateTime.parse(fields[3].trim(), formatter);
            String tariffIndex = fields[4].trim();

            cdrList.add(new CallDataRecord(callType, phoneNumber, startTime, endTime, tariffIndex));
        }
        reader.close();
        cdrList.sort(Comparator.comparing(CallDataRecord::getStartTime));

        return cdrList;
    }
}
