package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleFloatProperty;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FloatFieldProperty extends SimpleFloatProperty  {

    private final Consumer<Float> consumer;

    public FloatFieldProperty(Supplier<Float> supplier, Consumer<Float> consumer) {
        super(supplier.get());
        this.consumer = consumer;
    }

    @Override
    public void set(float value) {
        super.set(value);
        consumer.accept(value);
    }

    @Override
    public void setValue(Number value) {
        super.setValue(value);
        consumer.accept(value.floatValue());
    }
}
