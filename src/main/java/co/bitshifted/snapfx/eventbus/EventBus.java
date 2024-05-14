package co.bitshifted.snapfx.eventbus;

import java.util.concurrent.Flow;

public interface EventBus {

  <T> void subscribe(Flow.Subscriber<Event> receiver, Class<T> eventClass);

  <T> void subscribe(Object receiver, Class<T> eventClass);

  void unsubscribe(Object receiver);

  <T> void publishEvent(T event);
}
