package co.bitshifted.snapfx.property;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class FloatFieldProperty extends SimpleFloatProperty {

  private final Consumer<Number> consumer;
  private ChangeListener<Number> listener;
  private ObservableValue<? extends Number> observableValue;

  public FloatFieldProperty(Supplier<Float> supplier, Consumer<Number> consumer) {
    super(supplier.get());
    this.consumer = consumer;
    this.listener = new BackingFieldChangeListener<>(this.consumer);
  }

  @Override
  public void set(float value) {
    super.set(value);
    consumer.accept(value);
  }

  @Override
  public void setValue(Number value) {
    super.setValue(value);
    consumer.accept(value.floatValue());
  }

  @Override
  public void bind(ObservableValue<? extends Number> observableValue) {
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
