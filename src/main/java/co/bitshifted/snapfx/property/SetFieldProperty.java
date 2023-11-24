package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleSetProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SetFieldProperty<T> extends SimpleSetProperty<T> {

    private final Consumer<ObservableSet<T>> consumer;
    private final ChangeListener<ObservableSet<T>> listener;
    private final SetChangeListener<T> setChangeListener;
    private ObservableValue<? extends ObservableSet<T>> observableValue;

    public SetFieldProperty(Supplier<ObservableSet<T>> supplier, Consumer<ObservableSet<T>> consumer) {
        super(supplier.get());
        this.consumer = consumer;
        this.listener =  new BackingFieldChangeListener<>(this.consumer);
        this.setChangeListener = new BackingFieldSetChangeListener<>(this.consumer);
    }


    @Override
    public boolean add(T t) {
        var status = super.add(t);
        consumer.accept(get());
        return status;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        var status = super.addAll(collection);
        consumer.accept(get());
        return status;
    }

    @Override
    public void set(ObservableSet<T> value) {
        super.set(value);
        consumer.accept(value);
    }

    @Override
    public void setValue(ObservableSet<T> value) {
        super.setValue(value);
        consumer.accept(value);
    }

    @Override
    public void bind(ObservableValue<? extends ObservableSet<T>> observableValue) {
        super.bind(observableValue);
        this.observableValue = observableValue;
        this.observableValue.addListener(listener);
        this.observableValue.getValue().addListener(setChangeListener);
    }

    @Override
    public void unbind() {
        super.unbind();
        if(observableValue != null) {
            this.observableValue.removeListener(listener);
            this.observableValue.getValue().removeListener(setChangeListener);
        }
    }
}
