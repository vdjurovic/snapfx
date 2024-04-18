package co.bitshifted.snapfx.handlers;

import javafx.beans.binding.Binding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;

import java.util.Optional;

public abstract class DefaultActionEventHandler implements EventHandler<ActionEvent> {

    private final SimpleBooleanProperty disabledProperty;
    private final Node graphics;
    private final KeyCombination acceleratorKey;

    protected DefaultActionEventHandler() {
        this(null, null, null);
    }

    protected DefaultActionEventHandler(Binding disabledBinding) {
        this(disabledBinding, null, null);
    }

    protected DefaultActionEventHandler(Node graphics) {
        this(null, graphics, null);
    }

    protected DefaultActionEventHandler(KeyCombination acceleratorKey) {
        this(null, null, acceleratorKey);
    }

    protected DefaultActionEventHandler(Binding disabledBinding, Node graphics, KeyCombination acceleratorKey) {
        this.disabledProperty = new SimpleBooleanProperty();
        this.graphics = graphics;
        this.acceleratorKey = acceleratorKey;
        calculateDisabledProperty(Optional.ofNullable(disabledBinding));
    }

    private void calculateDisabledProperty(Optional<Binding> binding) {
        binding.ifPresent(b -> disabledProperty.bind(b));
    }


    public Node getGraphics() {
        return graphics;
    }

    public BooleanProperty disabledProperty() {
        return disabledProperty;
    }

    public KeyCombination getAcceleratorKey() {
        return acceleratorKey;
    }
}
