package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntegerFieldProperty extends SimpleIntegerProperty {

    private final Consumer<Integer> consumer;

    public IntegerFieldProperty(Supplier<Integer> supplier, Consumer<Integer> consumer) {
        super(supplier.get());
        this.consumer = consumer;
    }

    @Override
    public void set(int value) {
        super.set(value);
        consumer.accept(value);
    }

    @Override
    public void setValue(Number number) {
        super.setValue(number);
        consumer.accept(number.intValue());
    }
}
