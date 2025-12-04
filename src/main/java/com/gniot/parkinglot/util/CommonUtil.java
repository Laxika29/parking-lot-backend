package com.gniot.parkinglot.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;
import java.util.Random;

@Component
public class CommonUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }

    }

    public static String generateEmployeeId() {
        return "LXP" + generateFiveDigitNumber();
    }

    public static int generateFiveDigitNumber() {
        Random rand = new Random();
        // Generates a number between 10000 and 99999 (inclusive)
        return rand.nextInt(90000) + 10000;
    }

    public static String convertDisplayTime(LocalDateTime datetime) {
        if (datetime == null) return "";
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a", Locale.US);
        return datetime.format(formatter);
    }
}
