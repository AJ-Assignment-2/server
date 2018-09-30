package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MenuItem.MenuItem;
import model.Order.Order;
import model.Order.OrderState;
import service.MenuItemService.MenuItemAccessor;
import service.MenuItemService.MenuItemDao;
import service.OrderService.OrderAccessor;
import service.OrderService.OrderDao;

import java.util.List;
import java.util.logging.Logger;

public class Main extends Application {
    private static Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        MenuItemDao menuItemAccessor = new MenuItemAccessor();
        Order testOrder = new Order();

        // Add two of each menu item to the test order.
        for (MenuItem item : menuItemAccessor.readMenuItems()) {
            testOrder.addItem(item);
            testOrder.addItem(item);
        }

        testOrder.setCustomerName("Lionel");
        testOrder.setTableNumber(4);
        testOrder.setState(OrderState.WAITING);

        // The test order now contains two of each menu item.
        System.out.println("Order for: " + testOrder.getCustomerName() + " (table number: " + testOrder.getTableNumber() + ")");
        testOrder.getMenuItemSelections().entrySet().forEach((entry) -> {
            System.out.println("Customer requires " + entry.getValue() + " of the: " + entry.getKey().getName());
        });

        OrderDao orderAccessor = new OrderAccessor();
        orderAccessor.createOrder(testOrder); // Store the order in the database.

        // Confirm the order was stored correctly and can be retrieved correctly by retrieving it from the database.
        List<Order> allOrders = orderAccessor.getAllOrders();
        System.out.println("There are/is " + allOrders.size() + " orders in the database.");
        System.out.println("Printing all orders to the console: \n");
        for (Order order : allOrders) {
            System.out.println("Order for " + order.getCustomerName() + " sitting at table " + order.getTableNumber());
            System.out.println("The order contains: ");

            // Loop through all of the Order's menu items. The value of the HashMap represents the quantity of item.
            order.getMenuItemSelections().entrySet().forEach(entry ->
                System.out.println(entry.getValue() + " of the: " + entry.getKey().getName()));

            System.out.println("\n");
        }
        launch(args);
    }
}
