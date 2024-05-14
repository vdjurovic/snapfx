package co.bitshifted.snapfx.l10n.ui;

import co.bitshifted.snapfx.l10n.LocaleManager;
import co.bitshifted.snapfx.l10n.LocaleUtils;
import java.util.Locale;
import javafx.util.StringConverter;

public class LocaleStringConverter extends StringConverter<Locale> {

  private final LocaleManager localeManager;

  public LocaleStringConverter(LocaleManager localeManager) {
    this.localeManager = localeManager;
  }

  @Override
  public String toString(Locale locale) {
    return LocaleUtils.localeDisplayString(locale, localeManager.getSupportedLocales());
  }

  @Override
  public Locale fromString(String s) {
    return localeManager.localeFromString(s);
  }
}
