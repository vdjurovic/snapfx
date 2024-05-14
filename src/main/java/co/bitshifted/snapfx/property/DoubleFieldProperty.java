package co.bitshifted.snapfx.property;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class DoubleFieldProperty extends SimpleDoubleProperty {

  private final Consumer<Number> consumer;
  private ChangeListener<Number> listener;
  private ObservableValue<? extends Number> observableValue;

  public DoubleFieldProperty(Supplier<Double> supplier, Consumer<Number> consumer) {
    super(supplier.get());
    this.consumer = consumer;
    this.listener = new BackingFieldChangeListener<>(this.consumer);
  }

  @Override
  public void set(double value) {
    super.set(value);
    consumer.accept(value);
  }

  @Override
  public void setValue(Number value) {
    super.setValue(value);
    consumer.accept(value.doubleValue());
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
