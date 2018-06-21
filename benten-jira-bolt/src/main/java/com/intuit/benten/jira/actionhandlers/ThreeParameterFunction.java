package com.intuit.benten.jira.actionhandlers;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@FunctionalInterface
public interface ThreeParameterFunction<T, U,R> {
    public R apply(T t, U u);
}

