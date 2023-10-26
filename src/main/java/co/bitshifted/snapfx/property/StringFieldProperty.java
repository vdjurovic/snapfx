package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleStringProperty;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StringFieldProperty extends SimpleStringProperty {

    private final Consumer<String> consumer;

    public StringFieldProperty(Supplier<String> supplier, Consumer<String> consumer) {
        super(supplier.get());
        this.consumer = consumer;
    }

    @Override
    public void set(String value) {
        super.set(value);
        consumer.accept(value);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        consumer.accept(value);
    }
}
