package co.bitshifted.snapfx.property;

import java.util.function.Consumer;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

public class BackingFieldMapChangeListener<K, V> implements MapChangeListener<K, V> {

  private final Consumer<ObservableMap<K, V>> consumer;

  public BackingFieldMapChangeListener(Consumer<ObservableMap<K, V>> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void onChanged(Change<? extends K, ? extends V> change) {
    consumer.accept((ObservableMap<K, V>) change.getMap());
  }
}
