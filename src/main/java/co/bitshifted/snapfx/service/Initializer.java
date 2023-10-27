package co.bitshifted.snapfx.service;

import co.bitshifted.snapfx.error.InitializationException;

public interface Initializer {

    void initializeModel() throws InitializationException;
}
