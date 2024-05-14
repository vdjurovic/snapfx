package co.bitshifted.snapfx.l10n.ui;

import co.bitshifted.snapfx.l10n.LocaleManager;
import co.bitshifted.snapfx.l10n.LocaleUtils;
import java.util.Locale;
import javafx.scene.control.ListCell;

public class LocaleListCell extends ListCell<Locale> {

  private final LocaleManager localeManager;

  public LocaleListCell(LocaleManager localeManager) {
    this.localeManager = localeManager;
  }

  @Override
  protected void updateItem(Locale locale, boolean empty) {
    super.updateItem(locale, empty);
    if (locale != null && !empty) {
      setText(LocaleUtils.localeDisplayString(locale, localeManager.getSupportedLocales()));
    } else {
      setText(null);
    }
  }
}
