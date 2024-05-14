package co.bitshifted.snapfx.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;

public class IntegerPreferenceEntry extends NumberPreferenceEntry<Integer> {

  protected IntegerPreferenceEntry(String root, String name, Integer defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<Number> createProperty(Integer defaultValue) {
    return new SimpleIntegerProperty(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.putInt(name, property.getValue().intValue());
  }

  @Override
  protected void doSave(Integer value) {
    baseNode.putInt(name, value);
  }

  @Override
  public Integer getValue() {
    return property.getValue().intValue();
  }

  //    private final SimpleIntegerProperty property;
  //    private final String root;
  //    private final String name;
  //    private final Preferences baseNode;
  //
  //    public IntegerPreferenceEntry(String root, String name, Integer defaultValue) {
  //        this.root = root;
  //        this.name = name;
  //        this.property = new SimpleIntegerProperty(defaultValue);
  //        this.baseNode = Preferences.userRoot().node(root);
  //    }
  //
  //    public Integer getValue() {
  //        return property.getValue();
  //    }
  //
  //    public void save() {
  //        baseNode.putInt(name, property.getValue());
  //    }
  //
  //    public void save(Integer value) {
  //        baseNode.putInt(name, value);
  //        property.setValue(value);
  //    }
  //
  //    public SimpleIntegerProperty getProperty() {
  //        return property;
  //    }
}
