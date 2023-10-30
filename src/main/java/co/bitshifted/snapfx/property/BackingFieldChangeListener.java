package co.bitshifted.snapfx.property;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.function.Consumer;

public class BackingFieldChangeListener<T> implements ChangeListener<T> {

    private final Consumer<T> consumer;

    public BackingFieldChangeListener(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void changed(ObservableValue<? extends T> observableValue, T t, T newValue) {
        consumer.accept(newValue);
    }
}
