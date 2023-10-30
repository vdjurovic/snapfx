package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BooleanFieldProperty extends SimpleBooleanProperty {

    private final Consumer<Boolean> consumer;
    private ChangeListener<Boolean> listener;
    private ObservableValue<? extends Boolean> observableValue;

    public BooleanFieldProperty(Supplier<Boolean> supplier, Consumer<Boolean> consumer) {
        super(supplier.get());
        this.consumer = consumer;
        this.listener = new BackingFieldChangeListener<>(this.consumer);
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


    @Override
    public void bind(ObservableValue<? extends Boolean> observableValue) {
        super.bind(observableValue);
        this.observableValue = observableValue;
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
