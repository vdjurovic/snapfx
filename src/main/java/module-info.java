module co.bitshifted.snapfx {
    exports co.bitshifted.snapfx;
    exports co.bitshifted.snapfx.annotations;
    exports co.bitshifted.snapfx.di;
    exports co.bitshifted.snapfx.error;
    exports co.bitshifted.snapfx.l10n;
    exports co.bitshifted.snapfx.l10n.ui;
    exports co.bitshifted.snapfx.prefs;
    exports co.bitshifted.snapfx.view;
    exports co.bitshifted.snapfx.service;
    exports co.bitshifted.snapfx.eventbus;
    exports co.bitshifted.snapfx.eventbus.internal to com.google.guice;

    requires java.base;
    requires java.prefs;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.inject;
    requires com.google.guice;
    requires org.slf4j;
}