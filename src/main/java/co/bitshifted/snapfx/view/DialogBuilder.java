package co.bitshifted.snapfx.view;

import co.bitshifted.snapfx.l10n.ResourceBundleManager;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;

public final class DialogBuilder<T> {

  private Dialog<T> dialog;
  private ResourceBundleManager resourceBundleManager;
  private ResourceBundle resourceBundle;
  private final Map<ButtonType, EventHandler> buttonEventHandlers;

  private DialogBuilder() {
    buttonEventHandlers = new HashMap<>();
    dialog = new Dialog<>();
  }

  public static <R> DialogBuilder<R> newBuilder(Class<R> clazz) {
    return new DialogBuilder<R>();
  }

  public static DialogBuilder<Void> newBuilder() {
    return new DialogBuilder<>();
  }

  public DialogBuilder<T> withResourceBundleManager(ResourceBundleManager resourceBundleManager) {
    this.resourceBundleManager = resourceBundleManager;
    return this;
  }

  public DialogBuilder<T> withResourceBundleName(String resourceBundleName) {
    this.resourceBundle =
        resourceBundleManager.loadResourceBundle(resourceBundleName).getResourceBundle();
    return this;
  }

  public DialogBuilder<T> withTitle(String title) {
    dialog.setTitle(title);
    return this;
  }

  public DialogBuilder<T> withTitleKey(String key) {
    dialog.setTitle(resourceBundle.getString(key));
    return this;
  }

  public DialogBuilder<T> withButtonTypes(ButtonType... buttonTypes) {
    dialog.getDialogPane().getButtonTypes().addAll(buttonTypes);
    return this;
  }

  public DialogBuilder<T> withContent(Node content) {
    dialog.getDialogPane().setContent(content);
    return this;
  }

  public DialogBuilder<T> withResultConverter(Callback<ButtonType, T> converter) {
    dialog.setResultConverter(converter);
    return this;
  }

  public DialogBuilder<T> withButtonTypeActionHandler(ButtonType type, EventHandler handler) {
    this.buttonEventHandlers.put(type, handler);
    return this;
  }

  public Dialog<T> build() {
    buttonEventHandlers
        .entrySet()
        .forEach(
            entry ->
                dialog
                    .getDialogPane()
                    .lookupButton(entry.getKey())
                    .addEventHandler(ActionEvent.ACTION, entry.getValue()));
    return dialog;
  }
}
