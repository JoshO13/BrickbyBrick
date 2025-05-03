package com.it326.BrickByBrick;

public interface Manager<T> {
    /**
     * create object in database
     * 
     * @param object
     */
    public void createInDatabase(T object);

    /**
     * delete object in database
     * 
     * @param object
     */
    public void deleteInDatabase(T object);

    /**
     * edit object in database
     * 
     * @param object
     */
    public void editInDatabase(T object);
}
