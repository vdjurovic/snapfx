package co.bitshifted.snapfx.l10n;

import co.bitshifted.snapfx.di.ApplicationConfig;
import co.bitshifted.snapfx.prefs.PreferenceManager;
import co.bitshifted.snapfx.prefs.StringPreferenceEntry;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;

import java.util.List;
import java.util.Locale;

public class DefaultLocaleManager implements LocaleManager {

    private final PreferenceManager preferenceManager;
    private final ApplicationConfig applicationConfig;

    @Inject
    public DefaultLocaleManager(PreferenceManager preferenceManager, ApplicationConfig appConfig) {
        this.preferenceManager = preferenceManager;
        this.applicationConfig = appConfig;
    }

    @Override
    public StringPreferenceEntry getCurrentLocale() {
        var  defaultLocale = Locale.getDefault();
        var sb = new StringBuilder(defaultLocale.getLanguage());
        if(!defaultLocale.getCountry().isEmpty()) {
            sb.append("_").append(defaultLocale.getCountry());
        }
        if(!defaultLocale.getVariant().isEmpty()) {
            sb.append("_").append(defaultLocale.getVariant());
        }
        var localeString = sb.toString();
        System.out.println("Locale string: " + localeString);
        return  preferenceManager.getStringPreferenceEntry(CURRENT_LOCALE_PREFERENCE_NAME, localeString);
    }

    @Override
    public List<Locale> getSupportedLocales() {
        return applicationConfig.supportedLocales();
    }

    @Override
    public Locale localeFromString(String input) {
        var parts = input.split("_");
        if(parts.length == 1) {
            return new Locale(parts[0]);
        }
        if(parts.length == 2) {
            return new Locale(parts[0], parts[1]);
        }
        if(parts.length == 3) {
            return new Locale(parts[0], parts[1], parts[2]);
        }
        throw new IllegalStateException("Invalid locale string: " + input);
    }
}
