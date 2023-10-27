package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleDoubleProperty;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DoubleFieldProperty extends SimpleDoubleProperty {

    private final Consumer<Double> consumer;

    public DoubleFieldProperty(Supplier<Double> supplier, Consumer<Double> consumer) {
        super(supplier.get());
        this.consumer = consumer;
    }

    @Override
    public void set(double value) {
        super.set(value);
        consumer.accept(value);
    }

    @Override
    public void setValue(Number value) {
        super.setValue(value);
        consumer.accept(value.doubleValue());
    }
}
