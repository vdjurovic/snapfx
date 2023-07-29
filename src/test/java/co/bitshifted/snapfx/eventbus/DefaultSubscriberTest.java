package co.bitshifted.snapfx.eventbus;

import co.bitshifted.snapfx.eventbus.internal.DefaultEvent;
import co.bitshifted.snapfx.eventbus.internal.DefaultSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Flow;

public class DefaultSubscriberTest {

    private Flow.Subscription mockSubscription;
    private Method mockMethod;
    private EventReceiver receiver;

    @BeforeEach
    void setup() {
        mockSubscription = Mockito.mock(Flow.Subscription.class);
        mockMethod = Mockito.mock(Method.class);
        receiver = new EventReceiver();
    }

    @Test
    void onSubscribeSuccess() {
        var subscriber = new DefaultSubscriber(receiver, mockMethod);
        subscriber.onSubscribe(mockSubscription);
        Mockito.verify(mockSubscription).request(1);
    }

    @Test
    void onNextSuccess() throws Exception {
        var subscriber = new DefaultSubscriber(receiver, mockMethod);
        subscriber.onSubscribe(mockSubscription);
        var event = new DefaultEvent(new TestEvent("test"));
        subscriber.onNext(event);
        Mockito.verify(mockMethod).invoke(receiver, event.source());
        Mockito.verify(mockSubscription, Mockito.times(2)).request(1);
    }

    @Test
    void onNextException() throws Exception {
        var subscriber = new DefaultSubscriber(receiver, mockMethod);
        subscriber.onSubscribe(mockSubscription);
        var event = new DefaultEvent(new TestEvent("test"));
        Mockito.when(mockMethod.invoke(receiver, event.source())).thenThrow(new InvocationTargetException(new Exception("error")));
        Assertions.assertThrows(RuntimeException.class, () -> subscriber.onNext(event));

    }
}
