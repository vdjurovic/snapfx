package co.bitshifted.snapfx.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FxEventHandler {
    String name() default "";
    boolean singleton() default true;
}
