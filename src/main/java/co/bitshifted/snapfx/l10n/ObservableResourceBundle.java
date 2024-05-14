package co.bitshifted.snapfx.l10n;

import java.util.ResourceBundle;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleObjectProperty;

public class ObservableResourceBundle {

  private final SimpleObjectProperty<ResourceBundle> resourceBundleProperty;

  public ObservableResourceBundle(ResourceBundle source) {
    resourceBundleProperty = new SimpleObjectProperty<>(source);
  }

  public ResourceBundle getResourceBundle() {
    return resourceBundleProperty.getValue();
  }

  public void setResourceBundle(ResourceBundle bundle) {
    resourceBundleProperty.set(bundle);
  }

  public StringBinding getStringBinding(String key) {
    return new StringBinding() {
      {
        bind(resourceBundleProperty);
      }

      @Override
      protected String computeValue() {
        return getResourceBundle().getString(key);
      }
    };
  }
}
