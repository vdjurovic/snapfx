package co.bitshifted.snapfx.test.eventbus;

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
}
