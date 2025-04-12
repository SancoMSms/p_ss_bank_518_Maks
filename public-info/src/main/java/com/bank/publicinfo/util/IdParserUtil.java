package com.bank.publicinfo.util;

public class IdParserUtil {

    public static Long parseId(String message, String label) {
        try {
            return Long.valueOf(message);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + label + " id: " + message, e);
        }
    }
}
