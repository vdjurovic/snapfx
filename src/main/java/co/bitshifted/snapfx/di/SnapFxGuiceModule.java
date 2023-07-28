package co.bitshifted.snapfx.di;

import co.bitshifted.snapfx.NamedValues;
import co.bitshifted.snapfx.error.ModelLoadException;
import co.bitshifted.snapfx.annotations.FxEventHandler;
import co.bitshifted.snapfx.annotations.FxListener;
import co.bitshifted.snapfx.eventbus.EventBus;
import co.bitshifted.snapfx.eventbus.internal.DefaultEVentBus;
import co.bitshifted.snapfx.l10n.*;
import co.bitshifted.snapfx.l10n.ui.LocaleComboBoxInitializer;
import co.bitshifted.snapfx.annotations.ModelData;
import co.bitshifted.snapfx.prefs.DefaultPreferenceManager;
import co.bitshifted.snapfx.prefs.PreferenceManager;
import co.bitshifted.snapfx.view.DefaultFxViewLoader;
import co.bitshifted.snapfx.view.FxViewLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import javafx.scene.control.ComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class SnapFxGuiceModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnapFxGuiceModule.class);


    @Override
    protected final void configure() {
        bind(ApplicationConfig.class).toInstance(applicationConfig());
        bind(PreferenceManager.class).to(DefaultPreferenceManager.class).in(Scopes.SINGLETON);
        bind(FxViewLoader.class).to(DefaultFxViewLoader.class).in(Scopes.SINGLETON);
        bind(LocaleManager.class).to(DefaultLocaleManager.class).in(Scopes.SINGLETON);
        bind(ResourceBundleManager.class).to(DefaultResourceBundleManager.class).in(Scopes.SINGLETON);
        bind(EventBus.class).to(DefaultEVentBus.class).in(Scopes.SINGLETON);
        // UI components and utils
        bind(new TypeLiteral<Consumer<ComboBox<Locale>>>(){})
                .annotatedWith(Names.named(NamedValues.LOCALE_COMBO_INITIALIZER)).to(LocaleComboBoxInitializer.class);
        // bind views
        views().forEach(v -> bind(v));
        // bind event handlers
        bindEventHandlers(eventHandlers());
        // bind listeners
        bindListeners(listeners());
        // bind model
        models().forEach(m -> bindModelData(m));
        // bind custom bindings
        customBindings();

    }

    protected List<Class> views() {
        return List.of();
    }

    protected List<Class> eventHandlers() {
        return List.of();
    }

    protected List<Class> listeners() {return List.of(); }

    protected  List<Object> models() {
        return List.of();
    }

    protected ApplicationConfig applicationConfig() {
        return new DefaultApplicationConfig();
    }

    protected void customBindings() {}

    private void bindEventHandlers(List<Class> eventHandlers) {
        eventHandlers.forEach(h -> {
            var annotation = (FxEventHandler)h.getAnnotation(FxEventHandler.class);
            boolean isSingleton = false;
            if(annotation != null) {
                 isSingleton = annotation.singleton();
            }
            if(isSingleton) {
                bind(h).in(Scopes.SINGLETON);
            } else {
                bind(h);
            }
        });
    }

    private void bindListeners(List<Class> listeners) {
        listeners.forEach(l -> {
            var annotation = (FxListener)l.getAnnotation(FxListener.class);
            boolean isSingleton = false;
            if(annotation != null) {
                isSingleton = annotation.singleton();
            }
            if(isSingleton) {
                bind(l).in(Scopes.SINGLETON);
            } else {
                bind(l);
            }
        });
    }

    private void bindModelData(Object model) {
        var fields = model.getClass().getDeclaredFields();
        for(Field f : fields) {
            f.setAccessible(true);
            var annotation = (ModelData)f.getAnnotation(ModelData.class);
            if(annotation == null) {
                continue;
            }
            var name = annotation.name();
            var fieldType = f.getType();
            try {
                var fieldValue = f.get(model);
                bind((Class<Object>) fieldType).annotatedWith(Names.named(name)).toInstance(fieldValue);
                LOGGER.debug("Bound model data with name {} and type {}", name, fieldType.getName());
            } catch(Throwable th) {
                throw new ModelLoadException(th);
            }

        }
    }

}
