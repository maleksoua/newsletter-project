package com.proxym.newsletter.application.entity;


public enum Language {
    FRENCH,
    ENGLISH;

    public static boolean equalsIgnoreCase(String language) {
        if (language == null) {
            return false;
        }

        try {
            Language languageEnum = Language.valueOf(language.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
