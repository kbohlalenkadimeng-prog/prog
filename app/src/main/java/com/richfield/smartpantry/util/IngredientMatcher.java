package com.richfield.smartpantry.util;

import java.util.Locale;

public class IngredientMatcher {
    public static String normalise(String value) {
        if (value == null) return "";
        String s = value.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
        if (s.endsWith("ies") && s.length() > 3) s = s.substring(0, s.length()-3) + "y";
        else if (s.endsWith("oes") && s.length() > 3) s = s.substring(0, s.length()-2);
        else if (s.endsWith("s") && !s.endsWith("ss") && s.length() > 3) s = s.substring(0, s.length()-1);
        return s;
    }
    public static boolean sameIngredient(String pantryName, String requiredName) {
        return normalise(pantryName).equals(normalise(requiredName));
    }
}
