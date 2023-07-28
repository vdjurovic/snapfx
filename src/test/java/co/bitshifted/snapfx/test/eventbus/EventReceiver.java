package co.bitshifted.snapfx.test.eventbus;


import co.bitshifted.snapfx.annotations.EventHandler;

public class EventReceiver {

    private int count = 0;

    @EventHandler
    public String eventReceived(TestEvent event) {
        System.out.println("Receiving event");
        count++;
        return event.getName();
    }

    public int getCount() {
        return count;
    }
}
