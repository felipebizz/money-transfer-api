package com.api.revolut.dao;


import com.api.revolut.dao.impl.AccountDAOImpl;
import com.api.revolut.dao.impl.UserDAOImpl;
import com.api.revolut.utilities.PropertiesUtil;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * H2 DAO
 */
public class H2DAOFactory extends DAOFactory {

    private static final String H_2_DRIVER = PropertiesUtil.getStringProperty("h2_driver");
    private static final String H_2_CONNECTION_URL = PropertiesUtil.getStringProperty("h2_connection_url");
    private static final String H_2_USER = PropertiesUtil.getStringProperty("h2_user");
    private static final String H_2_PASSWORD = PropertiesUtil.getStringProperty("h2_password");
    private static Logger log = Logger.getLogger(H2DAOFactory.class);

    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final AccountDAOImpl accountDAO = new AccountDAOImpl();

    H2DAOFactory() {
        //init: load driver
        DbUtils.loadDriver(H_2_DRIVER);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(H_2_CONNECTION_URL, H_2_USER, H_2_PASSWORD);

    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    @Override
    public void populateTestData() {
        log.info("Populating Test User Table and data ..... ");
        Connection conn = null;
        try {
            conn = H2DAOFactory.getConnection();
            RunScript.execute(conn, new FileReader("src/test/resources/demo.sql"));
        } catch (SQLException e) {
            log.error("populateTestData(): Error populating user data: ", e);
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            log.error("populateTestData(): Error finding test script file ", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }


}
