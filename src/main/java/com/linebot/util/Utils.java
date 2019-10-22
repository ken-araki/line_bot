package com.linebot.util;

import java.sql.Timestamp;

public class Utils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static <E extends Exception> void uncheck(Runnable_WithExceptions<E> runnable) {
        try {
            runnable.run();
        } catch(RuntimeException e) {
            throw e;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T, E extends Exception> T uncheck(Supplier_WithExceptions<T, E> supplier) {
        try {
            return supplier.get();
        } catch(Throwable e) {
            throw new RuntimeException(e);
        }
    }
    public static <T, R, E extends Exception> R uncheck(T t, Function_WithExceptions<T, R, E> function) {
        try {
            return function.apply(t);
        } catch(RuntimeException e) {
            throw e;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static <T, E extends Exception> void uncheck(T t, Consumer_WithExceptions<T, E> consumer) {
        try {
            consumer.accept(t);
        } catch(RuntimeException e) {
            throw e;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }


    @FunctionalInterface
    public interface Consumer_WithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface BiConsumer_WithExceptions<T, U, E extends Exception> {
        void accept(T t, U u) throws E;
    }

    @FunctionalInterface
    public interface Function_WithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface Supplier_WithExceptions<T, E extends Exception> {
        T get() throws E;
    }

    @FunctionalInterface
    public interface Runnable_WithExceptions<E extends Exception> {
        void run() throws E;
    }
}
