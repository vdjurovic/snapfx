package co.bitshifted.snapfx.property;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

public class MapFieldProperty<K, V> extends SimpleMapProperty<K, V> {

  private final Consumer<ObservableMap<K, V>> consumer;
  private final ChangeListener<ObservableMap<K, V>> listener;
  private final MapChangeListener<K, V> mapChangeListener;
  private ObservableValue<? extends ObservableMap<K, V>> observableValue;

  public MapFieldProperty(
      Supplier<ObservableMap<K, V>> supplier, Consumer<ObservableMap<K, V>> consumer) {
    super(supplier.get());
    this.consumer = consumer;
    this.listener = new BackingFieldChangeListener<>(this.consumer);
    this.mapChangeListener = new BackingFieldMapChangeListener<>(this.consumer);
  }

  @Override
  public void set(ObservableMap<K, V> value) {
    super.set(value);
    consumer.accept(value);
  }

  @Override
  public void setValue(ObservableMap<K, V> value) {
    super.setValue(value);
    consumer.accept(value);
  }

  @Override
  public void bind(ObservableValue<? extends ObservableMap<K, V>> observableValue) {
    super.bind(observableValue);
    this.observableValue = observableValue;
    this.observableValue.addListener(listener);
    this.observableValue.getValue().addListener(mapChangeListener);
  }

  @Override
  public void unbind() {
    super.unbind();
    if (observableValue != null) {
      this.observableValue.removeListener(listener);
      this.observableValue.getValue().removeListener(mapChangeListener);
    }
  }
}
