package com.api.revolut.application;


import com.api.revolut.dao.DAOFactory;
import com.api.revolut.services.AccountService;
import com.api.revolut.services.ServiceExceptionMapper;
import com.api.revolut.services.TransactionService;
import com.api.revolut.services.UserService;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;


class MoneyTransferApp {
    private static Logger log = Logger.getLogger(MoneyTransferApp.class);


    public static void main(String[] args) throws Exception {
        //Initialize H2 database with demo data
        log.info("Initialize demo .....");
        DAOFactory h2DaoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
        h2DaoFactory.populateTestData();
        log.info("Initialisation Complete....");
        //Host service on jetty
        startService();

    }

    private static void startService() throws Exception {
        Server server = new Server(8084);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                UserService.class.getCanonicalName() + "," +
                        AccountService.class.getCanonicalName() + "," +
                        ServiceExceptionMapper.class.getCanonicalName() + "," +
                        TransactionService.class.getCanonicalName());

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

}
