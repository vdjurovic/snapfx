package co.bitshifted.snapfx.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

public class StringPreferenceEntry extends BasePreferenceEntry<String> {

  public StringPreferenceEntry(String root, String name, String defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<String> createProperty(String defaultValue) {
    return new SimpleStringProperty(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.put(name, property.getValue());
  }

  @Override
  protected void doSave(String value) {
    baseNode.put(name, value);
  }
}
