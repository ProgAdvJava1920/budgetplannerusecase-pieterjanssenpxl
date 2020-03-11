package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private static final Logger LOGGER = LogManager.getLogger(BudgetPlannerImporter.class);
    private PathMatcher csvMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv");

    public Account importCV(Path pad) {
        Account account = new Account();
        if (!csvMatcher.matches(pad)) {
            LOGGER.error("Invalid file: .csv expected. Provided {}",pad);
        }

        if (!Files.exists(pad)) {
            LOGGER.error("File {} does not exist",pad);
        }

        try (BufferedReader reader = Files.newBufferedReader(pad)) {
            String line = null;
            reader.readLine();
            List<Payment> payments = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(",");

                account.setIBAN(lines[1]);
                account.setName(lines[0]);
                DateTimeFormatter datum = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

                Payment payment = new Payment(LocalDateTime.parse(lines[3], datum),Double.parseDouble(lines[4]),lines[5],lines[6]);
                account.setPayments(payments);
                payments.add(payment);
                LOGGER.error(account.toString());
            }
        } catch (IOException e) {
            LOGGER.fatal("An error occured while reading : {}", pad);
        }

        return account;
    }
}