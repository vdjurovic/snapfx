package co.bitshifted.snapfx.view;

import co.bitshifted.snapfx.error.ViewLoadException;
import javafx.scene.Node;

public interface FxViewLoader {

  Node loadView(FxViewAware view) throws ViewLoadException;

  Node loadView(Object viewObject) throws ViewLoadException;

  <T extends Node> T loadView(Object viewObject, Class<T> viewType) throws ViewLoadException;
}
