package co.bitshifted.snapfx.l10n;

import co.bitshifted.snapfx.prefs.StringPreferenceEntry;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class DefaultResourceBundleManager implements ResourceBundleManager {

  private final LocaleManager localeManager;
  private final Map<String, ObservableResourceBundle> cache;
  private final StringPreferenceEntry localePreference;

  @Inject
  public DefaultResourceBundleManager(LocaleManager localeManager) {
    this.localeManager = localeManager;
    this.cache = new HashMap<>();
    this.localePreference = localeManager.getCurrentLocale();
    localePreference
        .getProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observableValue, String s, String t1) {
                var newLocale = localeManager.localeFromString(t1);
                System.out.println("NEw locale: " + newLocale);
                cache
                    .entrySet()
                    .forEach(
                        entry -> {
                          var newBundle = ResourceBundle.getBundle(entry.getKey(), newLocale);
                          entry.getValue().setResourceBundle(newBundle);
                        });
              }
            });
  }

  @Override
  public ObservableResourceBundle loadResourceBundle(String name) {
    var localeString = localePreference.getValue();
    var locale = localeManager.localeFromString(localeString);
    var bundle = ResourceBundle.getBundle(name, locale);
    var observableBundle = cache.get(name);
    if (observableBundle != null) {
      observableBundle.setResourceBundle(bundle);
    } else {
      observableBundle = new ObservableResourceBundle(bundle);
    }
    cache.put(name, observableBundle);
    return observableBundle;
  }
}
