package co.bitshifted.snapfx.l10n;

import co.bitshifted.snapfx.prefs.StringPreferenceEntry;

import java.util.List;
import java.util.Locale;

public interface LocaleManager {

    String CURRENT_LOCALE_PREFERENCE_NAME = "current-locale";

    StringPreferenceEntry getCurrentLocale();

    List<Locale> getSupportedLocales();

    Locale localeFromString(String input);
}
