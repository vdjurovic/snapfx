package co.bitshifted.snapfx.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;

public class DoublePreferenceEntry extends NumberPreferenceEntry<Double> {

  protected DoublePreferenceEntry(String root, String name, Double defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<Number> createProperty(Double defaultValue) {
    return new SimpleDoubleProperty(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.putDouble(name, property.getValue().doubleValue());
  }

  @Override
  protected void doSave(Double value) {
    baseNode.putDouble(name, value);
  }

  @Override
  public Double getValue() {
    return property.getValue().doubleValue();
  }
}
