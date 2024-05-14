package co.bitshifted.snapfx.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventBusSubscriptionHandler {
  boolean singleton() default true;
}
