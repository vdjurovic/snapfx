package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleObjectProperty;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ObjectFieldProperty<T> extends SimpleObjectProperty<T> {

    private final Consumer<T> consumer;

    public ObjectFieldProperty(Supplier<T> supplier, Consumer<T> consumer) {
        super(supplier.get());
        this.consumer = consumer;
    }

    @Override
    public void set(T value) {
        super.set(value);
        consumer.accept(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
        consumer.accept(value);
    }
}
