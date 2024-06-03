package nsu.fit.ezaitseva.model.dsl;

import groovy.lang.Closure;

/**
 * The type Delegator.
 */
public class Delegator {
    /**
     * Groovy delegate.
     *
     * @param delegate the delegate
     * @param closure  the closure
     */
    public static void groovyDelegate(Object delegate, Closure<?> closure) {
        closure.setDelegate(delegate);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }
}