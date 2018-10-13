package server.service.OrderService;

import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;
import model.Order.Order;
import model.Order.OrderState;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to access orders in the database.
 * This class accesses the RestaurantOrder and RestaurantOrderItem (weak entity) to populate
 * the data of Order objects.
 */
public class OrderAccessor implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(OrderAccessor.class.getName());

    private static final String TABLE_NAME = "RestaurantOrder";

    private static final String COLUMN_CUSTOMER_NAME = "customer_name";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TABLE_NUMBER = "table_number";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            COLUMN_CUSTOMER_NAME + " VARCHAR(50) NOT NULL, " +
            COLUMN_STATE + " VARCHAR(50) NOT NULL, " +
            COLUMN_TABLE_NUMBER + " INTEGER NOT NULL )";

    private Connection connection;

    /**
     * Create the RestaurantOrder and RestaurantOrderItem table.
     */
    public OrderAccessor(Connection connection) {
        this.connection = connection;

        // Order of table creation matters here (due to FK relationship)
        createOrderTable();
        createOrderItemTable();
    }

    /**
     * Return a list of all orders in the database.
     * This class retrieves information from the RestaurantOrder and RestaurantOrderItem tables
     * to initialise an Order object with all possible order information.
     *
     * @return A list of Order objects representing all orders in the database.
     */
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM RestaurantOrder";

        try {
            Statement statement = connection.createStatement();
            ResultSet orderResultSet = statement.executeQuery(sql);

            // For each order in the database create an Order object.
            while (orderResultSet.next()) {
                Order order = new Order();
                order.setId(orderResultSet.getInt(COLUMN_ID));
                order.setCustomerName(orderResultSet.getString(COLUMN_CUSTOMER_NAME));
                order.setTableNumber(orderResultSet.getInt(COLUMN_TABLE_NUMBER));

                String stateString = orderResultSet.getString(COLUMN_STATE);
                Arrays.stream(OrderState.values()).forEach(orderState -> {
                    if (orderState.toString().toLowerCase().compareTo(stateString.toLowerCase()) == 0) {
                        order.setState(orderState);
                    }
                });

                // Query that accesses an orders related menu items via a weak entity.
                final String orderItemQuery = "SELECT RestaurantOrderItem.quantity, " +
                        "MenuItem.id, MenuItem.name, MenuItem.price, MenuItem.energy, MenuItem.protean, MenuItem.carbohydrates, " +
                        "MenuItem.fat, MenuItem.fibre, MenuItem.type, MenuItem.category FROM RestaurantOrderItem " +
                        "INNER JOIN MenuItem ON RestaurantOrderItem.itemId = MenuItem.id WHERE RestaurantOrderItem.orderId = " + order.getId();

                statement = connection.createStatement();
                ResultSet menuItemResultSet = statement.executeQuery(orderItemQuery);

                // For each menu item belonging to the order, populate a
                // menu item object, and add this to the Order object.
                while (menuItemResultSet.next()) {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setId(menuItemResultSet.getInt("id"));
                    menuItem.setName(menuItemResultSet.getString("name"));
                    menuItem.setPrice(menuItemResultSet.getInt("price"));
                    menuItem.setEnergy(menuItemResultSet.getInt("energy"));
                    menuItem.setProtean(menuItemResultSet.getDouble("protean"));
                    menuItem.setCarbohydrates(menuItemResultSet.getDouble("carbohydrates"));
                    menuItem.setFat(menuItemResultSet.getDouble("fat"));
                    menuItem.setFibre(menuItemResultSet.getDouble("fibre"));

                    String categoryString = menuItemResultSet.getString("category");
                    String typeString = menuItemResultSet.getString("type");

                    Arrays.stream(MenuItemCategory.values()).forEach(menuItemCategory -> {
                        if (menuItemCategory.toString().toLowerCase().compareTo(categoryString.toLowerCase()) == 0) {
                            menuItem.setCategory(menuItemCategory);
                        }
                    });

                    Arrays.stream(MenuItemType.values()).forEach(menuItemType -> {
                        if (menuItemType.toString().toLowerCase().compareTo(typeString.toLowerCase()) == 0) {
                            menuItem.setType(menuItemType);
                        }
                    });

                    int menuItemQuantity = menuItemResultSet.getInt("Quantity");
                    for (int i = 0; i < menuItemQuantity; i++) {
                        order.addItem(menuItem);
                    }
                }
                orders.add(order);
            }

        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
        }

        return orders;
    }

    /**
     * Store an Order object in the database.
     * This method inserts values into the RestaurantOrder table.
     * It also populates the RestaurantOrderItem weak entity with the MenuItems of the Order.
     *
     * @param order
     */
    @Override
    public void createOrder(Order order) {
        String orderSql = "INSERT INTO RestaurantOrder (" + COLUMN_CUSTOMER_NAME + ", "
                + COLUMN_STATE + ", " + COLUMN_TABLE_NUMBER + ")" +
                " VALUES (?, ?, ?)";

        try {
            // Insert the Order into the database.
            PreparedStatement statement = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, order.getCustomerName());
            statement.setString(2, order.getState().toString());
            statement.setString(3, Integer.toString(order.getTableNumber()));
            statement.execute();

            // Create entries for the Order's menu item's in the RestaurantOrderItem weak entity.
            ResultSet keysFromInsert = statement.getGeneratedKeys();
            int insertedOrderId;
            if (keysFromInsert.next()) {
                insertedOrderId = keysFromInsert.getInt(1);
                for (MenuItem menuItem : order.getMenuItemSelections().keySet() ) {
                    String orderItemSql = "INSERT INTO RestaurantOrderItem VALUES(" + menuItem.getId() +
                            ", " + insertedOrderId + ", " + order.getMenuItemSelections().get(menuItem) + ")";
                    statement = connection.prepareStatement(orderItemSql);
                    statement.execute();
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
    }

    /**
     * Create the RestaurantOrder table, catch the exception that is thrown if the table is already created.
     */
    private void createOrderTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE);
        } catch (Exception e) {
            LOGGER.log(Level.OFF, "Order table is probably already created");
        }
    }

    /**
     * Create the RestaurantOrderItem weak entity that correlates menu items to orders.
     */
    private void createOrderItemTable() {
        try {
            Statement statement = connection.createStatement();
            String orderItemTableSql = "CREATE TABLE RestaurantOrderItem (" +
                    "itemId INTEGER REFERENCES MenuItem(id), " +
                    "orderId INTEGER REFERENCES RestaurantOrder(id), " +
                    "quantity INTEGER )";
            statement.execute(orderItemTableSql);
        } catch (Exception e) {
            LOGGER.log(Level.OFF, "RestaurantOrderItem table is probably already created");
        }
    }

    @Override
    public void setOrderState(OrderState state, int orderId) {
        try {
            String test = state.toString();
            Statement statement = connection.createStatement();
            String updateStateSql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_STATE + " = '" + state.toString() +
                    "' WHERE " + COLUMN_ID + " = " + orderId;

            statement.execute(updateStateSql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Unable to update order state to " + state.toString() + ": ", e);
        }

    }
}
