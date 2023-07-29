package co.bitshifted.snapfx.di;

import java.util.List;
import java.util.Locale;

public interface ApplicationConfig {

    String defaultPreferenceRootNode();
    String preferredLocale();
    List<Locale> supportedLocales();
    boolean eventBusEnabled();
}
