package com.proxym.newsletter.application.enums;


public enum Language {
    FRENCH,
    ARABIC,
    ENGLISH;

    public static boolean equalsIgnoreCase(String language) {
        if (language == null) {
            return false;
        }

        try {

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
