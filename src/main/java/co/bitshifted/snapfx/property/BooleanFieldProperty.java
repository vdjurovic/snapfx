package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BooleanFieldProperty extends SimpleBooleanProperty {

    private final Consumer<Boolean> consumer;

    public BooleanFieldProperty(Supplier<Boolean> supplier, Consumer<Boolean> consumer) {
        super(supplier.get());
        this.consumer = consumer;
    }


    @Override
    public void set(boolean value) {
        super.set(value);
        consumer.accept(value);
    }

    @Override
    public void setValue(Boolean value) {
        super.setValue(value);
        consumer.accept(value);
    }
}
