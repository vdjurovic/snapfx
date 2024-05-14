package co.bitshifted.snapfx.eventbus.internal;

import co.bitshifted.snapfx.eventbus.Event;

public record DefaultEvent(Object source) implements Event {}
