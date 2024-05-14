package co.bitshifted.snapfx.property;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ObjectFieldProperty<T> extends SimpleObjectProperty<T> {

  private final Consumer<T> consumer;
  private ChangeListener<T> listener;
  private ObservableValue<? extends T> observableValue;

  public ObjectFieldProperty(Supplier<T> supplier, Consumer<T> consumer) {
    super(supplier.get());
    this.consumer = consumer;
    this.listener = new BackingFieldChangeListener<>(this.consumer);
  }

  @Override
  public void set(T value) {
    super.set(value);
    consumer.accept(value);
  }

  @Override
  public void setValue(T value) {
    super.setValue(value);
    consumer.accept(value);
  }

  @Override
  public void bind(ObservableValue<? extends T> observableValue) {
    super.bind(observableValue);
    this.observableValue = observableValue;
    this.observableValue.addListener(listener);
  }

  @Override
  public void unbind() {
    super.unbind();
    if (observableValue != null) {
      this.observableValue.removeListener(listener);
    }
  }
}
