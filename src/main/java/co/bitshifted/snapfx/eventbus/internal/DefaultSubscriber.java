package co.bitshifted.snapfx.eventbus.internal;

import co.bitshifted.snapfx.eventbus.Event;
import java.lang.reflect.Method;
import java.util.concurrent.Flow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSubscriber implements Flow.Subscriber<Event> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSubscriber.class);

  private final Object receiver;
  private final Method handler;
  private Flow.Subscription subscription;

  public DefaultSubscriber(Object receiver, Method handler) {
    this.receiver = receiver;
    this.handler = handler;
  }

  @Override
  public void onSubscribe(Flow.Subscription subscription) {
    this.subscription = subscription;
    subscription.request(1);
  }

  @Override
  public void onNext(Event event) {
    try {
      handler.invoke(receiver, event.source());
      subscription.request(1);
    } catch (Throwable th) {
      LOGGER.error("Failed to invoke event handler");
      throw new RuntimeException(th);
    }
  }

  @Override
  public void onError(Throwable throwable) {
    LOGGER.error("Error receiving event", throwable);
  }

  @Override
  public void onComplete() {
    LOGGER.info("Subscription closed");
  }
}
