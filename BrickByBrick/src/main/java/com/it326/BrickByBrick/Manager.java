package com.it326.BrickByBrick;

public interface Manager<T> {
    
    public T createInDatabase(T object);

    public T deleteInDatabase(T object);
}
