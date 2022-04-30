package io.github.amayaframework.core.inject;

import java.lang.annotation.*;

/**
 * An annotation intended to indicate the position of the argument
 * to which the value of to inject annotation will be redirected.
 * <p>Example</p>
 * <p>Custom annotation:</p>
 * <pre>
 * {@literal @}interface MyAnnot {
 *     {@literal @}Position(1)
 *      int value1();
 *     {@literal @}Position
 *      String value0();
 *  }
 * </pre>
 * <p>Custom request:</p>
 * <pre>
 * class MyReq implements HttpRequest {
 *     // Some code
 *    {@literal @}Provider(MyAnnot.class)
 *     public Object getSomething(String str, int intVal) {
 *         // Some code
 *     }
 *     // Some code
 * }
 * </pre>
 * <p>Thus, when extracting the values of the annotation field,</p>
 * <p>they will be redirected to positions 0 and 1, respectively:</p>
 * <p>value0 -{@literal >} str</p>
 * <p>value1 -{@literal >} intVal</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Position {
    int value() default 0;
}
