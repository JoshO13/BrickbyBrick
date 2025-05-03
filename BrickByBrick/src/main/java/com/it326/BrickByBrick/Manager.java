package com.it326.BrickByBrick;

public interface Manager<T> {
    /**
     * create object in database
     * 
     * @param object
     */
    public boolean createInDatabase(T object);

    /**
     * delete object in database
     * 
     * @param object
     */
    public boolean deleteInDatabase(T object);

    /**
     * edit object in database
     * 
     * @param object
     */
    public boolean editInDatabase(T object);
}
