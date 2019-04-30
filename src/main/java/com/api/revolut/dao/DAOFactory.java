package com.api.revolut.dao;


public abstract class DAOFactory {


    public static final int H2 = 1;

    public static DAOFactory getDAOFactory(
            int factoryCode) {

        switch (factoryCode) {
            case H2:
                return new H2DAOFactory();
            default:
                //by default using H2 in memory database
                return new H2DAOFactory();
        }
    }

    public abstract UserDAO getUserDAO();

    public abstract AccountDAO getAccountDAO();

    public abstract void populateTestData();
}



