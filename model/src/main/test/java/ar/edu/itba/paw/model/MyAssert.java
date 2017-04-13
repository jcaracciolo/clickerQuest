package ar.edu.itba.paw.model;
import org.junit.Assert;

/**
 * Created by juanfra on 13/04/17.
 */
public class MyAssert extends Assert{

    static public void assertThrows(Class<? extends Throwable> clazz, Runnable lambda) {
        try {
            lambda.run();
            throw new AssertionError("Expected: " + clazz.getName());
        } catch (Exception e) {
            if ( !clazz.isInstance(e) ) {
                throw new AssertionError("Exception " + e.getClass().getName() +" does not match " + clazz.getName() +
                        " . Error: " + e.getMessage());
            }

        }
    }
}
