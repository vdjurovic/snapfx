package co.bitshifted.snapfx.view;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import javafx.scene.Node;

public interface FxViewAware {

  String viewName();

  ResourceBundle resourceBundle();

  URL fxmlUrl();

  Node viewRoot();

  default Charset charset() {
    return StandardCharsets.UTF_8;
  }
}
