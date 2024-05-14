package co.bitshifted.snapfx.di;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultApplicationConfig implements ApplicationConfig {

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

  @Override
  public boolean executorServiceEnabled() {
    return false;
  }

  @Override
  public ExecutorService executorService() {
    return Executors.newWorkStealingPool();
  }
}
