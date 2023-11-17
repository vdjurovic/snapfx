package co.bitshifted.snapfx.eventbus;

import co.bitshifted.snapfx.eventbus.internal.DefaultEVentBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultEventBusTest {

    private DefaultEVentBus eventBus = new DefaultEVentBus();

    @Test
    void eventProcessingSuccess() throws Exception {
        var receiver = new EventReceiver();
        eventBus.subscribe(receiver, TestEvent.class);
        eventBus.publishEvent(new TestEvent("test"));
        Thread.sleep(500);
        Assertions.assertEquals(1, receiver.getCount());
    }

    void multipleEventHandlersTest() throws Exception{
        var receiver = new EventReceiver();
        eventBus.subscribe(receiver, TestEvent.class);
        eventBus.subscribe(receiver, FooEvent.class);
        eventBus.publishEvent(new TestEvent("test"));
        eventBus.publishEvent(new FooEvent("bar"));
        Thread.sleep(500);
        Assertions.assertEquals(1, receiver.getCount());
        Assertions.assertEquals(1, receiver.getFooCount());
    }
}
