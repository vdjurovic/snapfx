package co.bitshifted.snapfx.view;

import co.bitshifted.snapfx.annotations.FxView;
import co.bitshifted.snapfx.annotations.ViewRootNode;
import co.bitshifted.snapfx.error.ViewLoadException;
import co.bitshifted.snapfx.l10n.ResourceBundleManager;
import jakarta.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class DefaultFxViewLoader implements FxViewLoader {

  private final ResourceBundleManager resourceBundleManager;

  @Inject
  public DefaultFxViewLoader(ResourceBundleManager resourceBundleManager) {
    this.resourceBundleManager = resourceBundleManager;
  }

  @Override
  public Node loadView(FxViewAware view) throws ViewLoadException {
    var loader = new FXMLLoader(view.fxmlUrl());
    var bundle = view.resourceBundle();
    if (bundle != null) {
      var observableBundle =
          resourceBundleManager.loadResourceBundle(view.resourceBundle().getBaseBundleName());
      loader.setResources(observableBundle.getResourceBundle());
    }
    loader.setController(view);
    loader.setCharset(view.charset());
    try {
      return loader.load();
    } catch (IOException ex) {
      throw new ViewLoadException(ex);
    }
  }

  @Override
  public Node loadView(Object viewObject) throws ViewLoadException {
    if (viewObject instanceof FxViewAware fxv) {
      return this.loadView(fxv);
    }
    var viewAnnotation = viewObject.getClass().getAnnotation(FxView.class);
    try {
      var fxmlUrl = viewAnnotation.fxmlUrl();
      if (fxmlUrl == null || fxmlUrl.isEmpty()) {
        return findRootNode(viewObject);
      }
      var url = urlFromString(fxmlUrl);
      var loader = new FXMLLoader(url);
      loader.setController(viewObject);
      var resourceBundleName = viewAnnotation.resourceBundle();
      if (resourceBundleName != null
          && !resourceBundleName.isBlank()
          && !resourceBundleName.isEmpty()) {
        var observableBundle = resourceBundleManager.loadResourceBundle(resourceBundleName);
        loader.setResources(observableBundle.getResourceBundle());
      }
      var charset = viewAnnotation.charset();
      if (charset != null && !charset.isBlank() && !charset.isEmpty()) {
        loader.setCharset(Charset.forName(charset));
      } else {
        loader.setCharset(StandardCharsets.UTF_8);
      }
      return loader.load();
    } catch (Throwable th) {
      throw new ViewLoadException(th);
    }
  }

  @Override
  public <T extends Node> T loadView(Object viewObject, Class<T> viewType)
      throws ViewLoadException {
    return (T) loadView(viewObject);
  }

  private URL urlFromString(String urlString) throws MalformedURLException {
    if (urlString == null || urlString.isEmpty() || urlString.isBlank()) {
      throw new ViewLoadException("FXML URL is empty");
    }
    var target = ClassLoader.getSystemResource(urlString);
    if (target == null && Files.exists(Path.of(urlString))) {
      target = new File(urlString).toURI().toURL();
    }
    return target;
  }

  private Node findRootNode(Object viewObject) throws IllegalAccessException {
    var fields = viewObject.getClass().getDeclaredFields();
    Stream.of(fields).forEach(f -> f.setAccessible(true));
    var viewRootField =
        Stream.of(fields)
            .filter(f -> f.getAnnotation(ViewRootNode.class) != null)
            .findFirst()
            .orElseThrow(
                () -> new ViewLoadException("View has no fields annotated with @ViewRootNode"));
    return (Node) viewRootField.get(viewObject);
  }
}
