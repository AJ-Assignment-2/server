//package server.service;
//
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class DatabaseUtility {
//    private static DataSource dataSource;
//
//    static {
//        try {
//            dataSource = new InitialContext().lookup("jndifordbconc");
//        }
//        catch (NamingException e) {
//            throw new ExceptionInInitializerError("'jndifordbconc' not found in JNDI", e);
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
//}
