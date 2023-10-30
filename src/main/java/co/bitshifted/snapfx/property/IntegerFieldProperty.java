package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntegerFieldProperty extends SimpleIntegerProperty {

    private final Consumer<Number> consumer;
    private ChangeListener<Number> listener;
    private ObservableValue<? extends Number> observableValue;

    public IntegerFieldProperty(Supplier<Integer> supplier, Consumer<Number> consumer) {
        super(supplier.get());
        this.consumer = consumer;
        this.listener = new BackingFieldChangeListener<>(this.consumer);
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

    @Override
    public void bind(ObservableValue<? extends Number> observableValue) {
        super.bind(observableValue);
        this.observableValue = observableValue ;
        this.observableValue.addListener(listener);
    }

    @Override
    public void unbind() {
        super.unbind();
        if(observableValue != null) {
            this.observableValue.removeListener(listener);
        }
    }
}
