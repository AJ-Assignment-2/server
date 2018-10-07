package server;

import server.rmi.chef.ChefService;
import server.rmi.chef.ChefServiceImpl;
import server.rmi.customer.CustomerService;
import server.rmi.customer.CustomerServiceImpl;
import server.rmi.reception.ReceptionService;
import server.rmi.reception.ReceptionServiceImpl;

import java.rmi.Naming;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApplication {
    private static Logger LOGGER = Logger.getLogger(ServerApplication.class.getName());

    public static void main(String[] args) {
        Connection attemptedConnection = null;
        try {
            attemptedConnection = DriverManager.getConnection("jdbc:derby:RestaurantOrderingDB;create=true");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        try {
            ChefService chefService = new ChefServiceImpl(attemptedConnection);
            ReceptionService receptionService = new ReceptionServiceImpl(attemptedConnection);
            CustomerService customerService = new CustomerServiceImpl(attemptedConnection);

            Naming.rebind("Chef", chefService);
            Naming.rebind("Reception", receptionService);
            Naming.rebind("Customer", customerService);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE , "Unable to bind RMI service", e);
        }
    }
}
