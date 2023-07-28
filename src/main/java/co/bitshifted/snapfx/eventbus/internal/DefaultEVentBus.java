package co.bitshifted.snapfx.eventbus.internal;

import co.bitshifted.snapfx.annotations.EventHandler;
import co.bitshifted.snapfx.eventbus.Event;
import co.bitshifted.snapfx.eventbus.EventBus;

import java.lang.reflect.Method;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class DefaultEVentBus implements EventBus {

    private static final int BUFFER_MAX_CAPACITY = 16;
    private static final ExecutorService EXECUTOR_SERVICE;

    static {
        EXECUTOR_SERVICE = ForkJoinPool.commonPool();
    }

    private final ConcurrentMap<String, SubmissionPublisher<Event>> publisherMap;

    public DefaultEVentBus() {
        this.publisherMap = new ConcurrentHashMap<>();
    }

    @Override
    public <T> void subscribe(Flow.Subscriber<Event> receiver, Class<T> eventClass) {
        var eventClassName = eventClass.getName();
        if(publisherMap.containsKey(eventClassName)) {
            publisherMap.get(eventClassName).subscribe(receiver);
        } else {
            var publisher = new SubmissionPublisher<Event>(EXECUTOR_SERVICE, BUFFER_MAX_CAPACITY);
            publisher.subscribe(receiver);
            publisherMap.put(eventClassName, publisher);
        }
    }

    @Override
    public <T> void subscribe(Object receiver, Class<T> eventClass) {
        var subscriber = new DefaultSubscriber(receiver, getEventHandlerMethod(receiver, eventClass));
        subscribe(subscriber, eventClass);
    }

    @Override
    public void unsubscribe(Object receiver) {

    }

    @Override
    public <T> void publishEvent(T event) {
        var processed = new DefaultEvent(event);
        var publisher = publisherMap.get(event.getClass().getName());
        if(publisher != null) {
            publisher.submit(processed);
        }
    }

    private Method getEventHandlerMethod(Object receiver, Class eventClass) {
        var methods = receiver.getClass().getDeclaredMethods();
        var method = Stream.of(methods)
                .filter(m -> m.isAnnotationPresent(EventHandler.class))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Failed to find @EventHandler annotation"));
        // check for valid method signature
        var params = method.getParameterTypes();
        if(params.length == 1 && params[0].equals(eventClass)) {
            return method;
        } else {
            throw new IllegalArgumentException("Invalid method signature for event handler method. Expecting single argument of event type");
        }
    }
}
