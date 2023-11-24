package co.bitshifted.snapfx.property;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.function.Consumer;

public class BackingFieldListChangeListener<T> implements ListChangeListener<T> {

    private final Consumer<ObservableList<T>> consumer;

    public BackingFieldListChangeListener(Consumer<ObservableList<T>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onChanged(Change<? extends T> change) {
        consumer.accept((ObservableList<T>) change.getList());
    }
}
