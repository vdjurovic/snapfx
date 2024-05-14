package co.bitshifted.snapfx.eventbus;

import co.bitshifted.snapfx.annotations.EventBusSubscriptionHandler;

public class EventReceiver {

  private int count = 0;
  private int fooCount = 0;

  @EventBusSubscriptionHandler
  public String eventReceived(TestEvent event) {
    System.out.println("Receiving event");
    count++;
    return event.getName();
  }

  @EventBusSubscriptionHandler
  public void fooEventReceived(FooEvent event) {
    fooCount++;
  }

  public int getCount() {
    return count;
  }

  public int getFooCount() {
    return fooCount;
  }
}
