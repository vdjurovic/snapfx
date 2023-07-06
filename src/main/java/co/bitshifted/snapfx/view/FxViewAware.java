package co.bitshifted.snapfx.view;

import javafx.scene.Node;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;

public interface FxViewAware {

    String viewName();
    ResourceBundle resourceBundle();
    URL fxmlUrl();
    Node viewRoot();
    default Charset charset() {
        return StandardCharsets.UTF_8;
    }
}
