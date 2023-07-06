package co.bitshifted.snapfx.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FxView {

    String name() default "";
    String resourceBundle() default "";
    String fxmlUrl() default "";
    String charset() default "UTF-8";
}
