package co.bitshifted.snapfx.listeners;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class ExtensibleChangeListener<T, V> implements ChangeListener<T> {

    private Set<Consumer<V>> consumers = new HashSet<>();

    @Override
    public void changed(ObservableValue<? extends T> observableValue, T oldVal, T newVal) {
        consumers.stream().forEach(c -> c.accept(fromObservable(newVal)));
    }

    protected abstract V fromObservable(T observableValue);

    public final void register(Consumer<V> consumer) {
        consumers.add(consumer);
    }
}
