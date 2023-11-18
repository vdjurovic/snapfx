package co.bitshifted.snapfx.di;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

public interface ApplicationConfig {

    String defaultPreferenceRootNode();
    String preferredLocale();
    List<Locale> supportedLocales();
    boolean eventBusEnabled();
    boolean executorServiceEnabled();
    ExecutorService executorService();
}
