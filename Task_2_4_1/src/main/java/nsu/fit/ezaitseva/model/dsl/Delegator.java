package nsu.fit.ezaitseva.model.dsl;

import groovy.lang.Closure;

public class Delegator {
    public static void groovyDelegate(Object delegate, Closure<?> closure) {
        closure.setDelegate(delegate);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }
}