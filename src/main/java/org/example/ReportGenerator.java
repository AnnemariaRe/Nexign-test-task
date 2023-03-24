package org.example;

import org.example.entity.Call;
import org.example.entity.Report;
import org.example.entity.Subscriber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {
    public static void generate(List<Subscriber> subscribers) {
        for (Subscriber subscriber : subscribers) {
            BigDecimal totalCost = BigDecimal.ZERO;

            for (Call call : subscriber.getCalls()) {
                totalCost = totalCost.add(call.getCost());
            }

            Report report = new Report(subscriber.getPhoneNumber(), subscriber.GetTariffIndex(), subscriber.getCalls(), totalCost);
            try {
                saveToFile(report);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void saveToFile(Report report) throws IOException {
        String filename = "report_" + report.getPhoneNumber() + ".txt";
        File file = new File("reports/" + filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        // определение максимальной длины последнего столбца и всей строки
        int lengthWithoutCost = 73;
        int maxCostLength = 0;
        for (Call call : report.getCalls()) {
            BigDecimal cost = call.getCost();
            if (cost != null) {
                int costLength = cost.setScale(2).toString().length();
                if (costLength > maxCostLength) {
                    maxCostLength = costLength;
                }
            }
        }

        int maxLineLength = "| Call Type |   Start Time        |     End Time        | Duration | Cost|".length() + maxCostLength;
        int maxLastTwoColumnsLength = " Duration | Cost".length() + maxCostLength;

        writer.write("Tariff index: " + report.getTariffIndex() + "\n");
        writer.write("-".repeat(maxLineLength) + "\n");
        writer.write("Report for phone number " + report.getPhoneNumber() + ":\n");
        writer.write("-".repeat(maxLineLength) + "\n");
        writer.write("| Call Type |   Start Time        |     End Time        | Duration | Cost" + " ".repeat(maxCostLength) + "|\n");
        writer.write("-".repeat(maxLineLength) + "\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Call call : report.getCalls()) {
            String callType = call.getCallType();
            String startTime = call.getStartTime().format(formatter);
            String endTime = call.getEndTime().format(formatter);
            Duration duration = call.getDuration();
            String formattedDuration = String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutes() % 60, duration.toSeconds() % 60);
            String cost = (call.getCost() != null) ? call.getCost().setScale(2).toString() : "0.00";

            writer.write(String.format("|     %s    | %s | %s | %s |   %" + maxCostLength + "s  |\n",
                    callType, startTime, endTime, formattedDuration, cost));
        }

        writer.write("-".repeat(maxLineLength) + "\n");

        String totalCost = (report.getTotalCost() != null) ? report.getTotalCost().setScale(2).toString() : "0.00";
        int totalCostValueLength = (totalCost + " rubles ").length();
        int totalCostLength = "Total Cost: |".length();

        writer.write(String.format("|%" + (maxLineLength - maxLastTwoColumnsLength - 4)
                        + "s |" + " ".repeat(maxLastTwoColumnsLength - totalCostValueLength) + "%s rubles |\n",
                        "Total Cost:", totalCost));
        writer.write("-".repeat(maxLineLength) + "\n");
        writer.close();
    }
}
