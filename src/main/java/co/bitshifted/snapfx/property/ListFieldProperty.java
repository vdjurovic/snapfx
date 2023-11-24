package co.bitshifted.snapfx.property;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.SetChangeListener;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ListFieldProperty<T> extends SimpleListProperty<T> {

    private final Consumer<ObservableList<T>> consumer;
    private final ChangeListener<ObservableList<T>> listener;
    private final ListChangeListener<T> listChangeListener;
    private ObservableValue<? extends ObservableList<T>> observableValue;

    public ListFieldProperty(Supplier<ObservableList<T>> supplier, Consumer<ObservableList<T>> consumer) {
        super(supplier.get());
        this.consumer = consumer;
        this.listener =  new BackingFieldChangeListener<>(this.consumer);
        this.listChangeListener = new BackingFieldListChangeListener<>(this.consumer);
    }


    @Override
    public boolean add(T t) {
        var status = super.add(t);
        consumer.accept(get());
        return status;
    }


        @Override
    public void set(ObservableList<T> value) {
        super.set(value);
        consumer.accept(value);
    }

    @Override
    public void setValue(ObservableList<T> value) {
        super.setValue(value);
        consumer.accept(value);
    }

    @Override
    public void bind(ObservableValue<? extends ObservableList<T>> observableValue) {
        super.bind(observableValue);
        this.observableValue = observableValue;
        this.observableValue.addListener(listener);
        this.observableValue.getValue().addListener(listChangeListener);
    }

    @Override
    public void unbind() {
        super.unbind();
        if(observableValue != null) {
            this.observableValue.removeListener(listener);
            this.observableValue.getValue().removeListener(listChangeListener);
        }
    }
}
