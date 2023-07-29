package co.bitshifted.snapfx.di;

import java.util.List;
import java.util.Locale;

public class DefaultApplicationConfig implements ApplicationConfig{

    public static final String DEFAULT_LOCALE = "en_US";

    @Override
    public String defaultPreferenceRootNode() {
        return this.getClass().getPackageName();
    }

    @Override
    public String preferredLocale() {
        return DEFAULT_LOCALE;
    }

    @Override
    public List<Locale> supportedLocales() {
        return List.of(Locale.ENGLISH);
    }

    @Override
    public boolean eventBusEnabled() {
        return false;
    }
}
