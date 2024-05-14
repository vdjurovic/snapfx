package co.bitshifted.snapfx.prefs;

import java.util.prefs.Preferences;
import javafx.beans.property.SimpleFloatProperty;

public class FloatPreferenceEntry {

  private final SimpleFloatProperty property;
  private final String root;
  private final String name;
  private final Preferences baseNode;

  public FloatPreferenceEntry(String root, String name, Float defaultValue) {
    this.root = root;
    this.name = name;
    this.property = new SimpleFloatProperty(defaultValue);
    this.baseNode = Preferences.userRoot().node(root);
  }

  public Float getValue() {
    return property.getValue();
  }

  public void save() {
    baseNode.putFloat(name, property.getValue());
  }

  public void save(Float value) {
    baseNode.putFloat(name, value);
    property.setValue(value);
  }

  public SimpleFloatProperty getProperty() {
    return property;
  }
}
