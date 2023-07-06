package co.bitshifted.snapfx.l10n.ui;

import co.bitshifted.snapfx.l10n.LocaleManager;
import jakarta.inject.Inject;
import javafx.scene.control.ComboBox;

import java.util.Locale;
import java.util.function.Consumer;

public class LocaleComboBoxInitializer implements Consumer<ComboBox<Locale>> {

    private final LocaleManager localeManager;

    @Inject
    public LocaleComboBoxInitializer(LocaleManager localeManager) {
        this.localeManager = localeManager;
    }

    @Override
    public void accept(ComboBox<Locale> localeComboBox) {
        localeComboBox.setButtonCell(new LocaleListCell(localeManager));
        localeComboBox.setCellFactory(localeListView -> new LocaleListCell(localeManager));
        localeComboBox.setConverter(new LocaleStringConverter(localeManager));
        var currentLocale = localeManager.getCurrentLocale();
        var supportedLocales = localeManager.getSupportedLocales();
        localeComboBox.getItems().addAll(supportedLocales);
        localeComboBox.setValue(localeManager.localeFromString(currentLocale.getValue()));
        localeComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            currentLocale.save(newValue.toString());
        });
    }
}
