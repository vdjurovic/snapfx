package co.bitshifted.snapfx.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Optional;
import java.util.prefs.Preferences;

public abstract class NumberPreferenceEntry<T extends Number> implements PendingValuePreference<T> {

    protected final Property<Number> property;
    protected final String name;
    protected final Preferences baseNode;
    protected Optional<T> pendingValue;

    protected NumberPreferenceEntry(String root, String name, T defaultValue) {
        this.name = name;
        this.baseNode = Preferences.userRoot().node(root);
        this.property = createProperty(defaultValue);
        property.addListener((observableValue, oldValue, newValue) -> save());
        this.pendingValue = Optional.empty();
    }

    protected abstract Property<Number> createProperty(T defaultValue);

    protected abstract void doSave();

    protected abstract void doSave(T value);

    public abstract T getValue();

    @Override
    public void save() {
        if(pendingValue != null && pendingValue.isPresent()) {
            property.setValue(pendingValue.get());
            pendingValue = Optional.empty();
        }
        doSave();
    }

    public void save(T value) {
        if(pendingValue.isPresent()) {
            pendingValue = Optional.empty();
        }
        doSave(value);
        property.setValue(value);
    }

    public Property<Number> getProperty() {
        return property;
    }

    @Override
    public void setPendingValue(T value) {
        this.pendingValue = Optional.ofNullable(value);
    }

    @Override
    public boolean hasPendingValue() {
        return this.pendingValue.isPresent();
    }


    @Override
    public void clearPendingValue() {
        this.pendingValue = Optional.empty();
    }
}
