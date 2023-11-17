package co.bitshifted.snapfx.eventbus;

public class FooEvent {

    private final String foo;

    public FooEvent(String name) {
        this.foo = name;
    }

    public String getFoo() {
        return foo;
    }
}
