package co.bitshifted.snapfx.property;

import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

import java.util.function.Consumer;

public class BackingFieldSetChangeListener<T> implements SetChangeListener<T> {

    private final Consumer<ObservableSet<T>> consumer;

    public BackingFieldSetChangeListener(Consumer<ObservableSet<T>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onChanged(Change<? extends T> change) {
        consumer.accept((ObservableSet<T>) change.getSet());
    }
}
