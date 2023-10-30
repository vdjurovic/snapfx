package co.bitshifted.snapfx.property;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StringFieldProperty extends SimpleStringProperty {

    private final Consumer<String> consumer;
    private ChangeListener<String> listener;
    private ObservableValue<? extends String> observableValue;

    public StringFieldProperty(Supplier<String> supplier, Consumer<String> consumer) {
        super(supplier.get());
        this.consumer = consumer;
        this.listener = new BackingFieldChangeListener<>(this.consumer);
    }

    @Override
    public void set(String value) {
        super.set(value);
        consumer.accept(value);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        consumer.accept(value);
    }

    @Override
    public void bind(ObservableValue<? extends String> observableValue) {
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
