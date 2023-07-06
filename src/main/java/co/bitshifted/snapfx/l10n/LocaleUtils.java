package co.bitshifted.snapfx.l10n;

import java.util.List;
import java.util.Locale;

public final class LocaleUtils {

    private LocaleUtils() {

    }

    public static String localeDisplayString(Locale locale, List<Locale> supported) {
        var sb = new StringBuilder();
        sb.append(locale.getDisplayLanguage());
        var country = locale.getDisplayCountry();
        if(!country.isEmpty() && includeCountry(locale, supported)) {
            sb.append(" (").append(country).append(")");
        }
        var variant = locale.getDisplayVariant();
        if(!variant.isEmpty() && includeVariant(locale, supported)) {
            sb.append(" (").append(variant).append(")");
        }
        return sb.toString();
    }

    private static boolean includeCountry(Locale locale, List<Locale> supported) {
        return supported.stream()
                .anyMatch(loc -> loc.getLanguage().equals(locale.getLanguage()) && loc.getCountry().equals(locale.getCountry()));
    }

    private static boolean includeVariant(Locale locale, List<Locale> supported) {
        return supported.stream()
                .anyMatch(loc -> loc.getLanguage().equals(locale.getLanguage())
                        && loc.getCountry().equals(locale.getCountry())
                        && loc.getVariant().equals(locale.getVariant()));
    }

//    public static String localeDisplayString(Locale locale, List<Locale> supported) {
//        if(supported.contains(locale)) {
//            System.out.println("Locale supported: " + locale);
//            return localeDisplayString(locale);
//        } else {
//            System.out.println("Locale not supported: " + locale);
//            var opt = supported.stream().filter(loc -> locale.getLanguage().equals(loc.getLanguage())).findFirst();
//            if(opt.isPresent()) {
//                System.out.println("Found langfuage match: " + opt.get());
//
//            }
//        }
//        return null;
//    }
}
