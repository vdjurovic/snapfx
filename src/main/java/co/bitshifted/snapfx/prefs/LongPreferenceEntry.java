package co.bitshifted.snapfx.prefs;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.prefs.Preferences;

public class LongPreferenceEntry {

    private final SimpleLongProperty property;
    private final String root;
    private final String name;
    private final Preferences baseNode;

    public LongPreferenceEntry(String root, String name, Long defaultValue) {
        this.root = root;
        this.name = name;
        this.property = new SimpleLongProperty(defaultValue);
        this.baseNode = Preferences.userRoot().node(root);
    }

    public Long getValue() {
        return property.getValue();
    }

    public void save() {
        baseNode.putLong(name, property.getValue());
    }

    public void save(Integer value) {
        baseNode.putLong(name, value);
        property.setValue(value);
    }

    public SimpleLongProperty getProperty() {
        return property;
    }
}
