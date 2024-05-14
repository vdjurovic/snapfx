package co.bitshifted.snapfx.property;

import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
