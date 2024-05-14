package co.bitshifted.snapfx.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FxListener {
  String name() default "";

  boolean singleton() default true;
}
