package co.bitshifted.snapfx.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;

public class MultiValuePreferenceEntry<T> extends BasePreferenceEntry<Collection<T>> {

    public MultiValuePreferenceEntry(String root, String name, Collection<T> defaultValue) {
        super(root, name, defaultValue);
    }

    @Override
    protected Property<Collection<T>> createProperty(Collection<T> defaultValue) {
        return new SimpleObjectProperty<>(defaultValue);
    }

    @Override
    protected void doSave() {
        baseNode.putByteArray(name, getObjectBytes(property.getValue()));
    }

    @Override
    protected void doSave(Collection<T> value) {
        baseNode.putByteArray(name, getObjectBytes(value));
    }

    private byte[] getObjectBytes(Collection<T> object) {
        var bytes = new ByteArrayOutputStream();
        try(var oos = new ObjectOutputStream(bytes)) {
            oos.writeObject(object);
            return bytes.toByteArray();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
