package main;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizeManager {
    private static Locale currentLocale = new Locale("en");
    private static ResourceBundle bundle = ResourceBundle.getBundle("bundles.Bundle", currentLocale);

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static void setLocale(Locale locale) {
        if (locale == null) return;
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("bundles.Bundle", currentLocale);
    }

    // Удобный метод для получения строк по ключу
    public static String getString(String key) {
        return bundle.getString(key);
    }
}
