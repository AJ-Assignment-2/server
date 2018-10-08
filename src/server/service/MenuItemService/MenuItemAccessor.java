package server.service.MenuItemService;

import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class used to access menu items in the database.
 */
public class MenuItemAccessor implements MenuItemDao {
    private static final Logger LOGGER = Logger.getLogger( MenuItemAccessor.class.getName() );

    private static final String TABLE_NAME = "MenuItem";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_ENERGY = "energy";
    private static final String COLUMN_PROTEAN = "protean";
    private static final String COLUMN_CARBOHYDRATES = "carbohydrates";
    private static final String COLUMN_FAT = "fat";
    private static final String COLUMN_FIBRE = "fibre";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_TYPE = "type";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
            COLUMN_NAME + " VARCHAR(100) NOT NULL, " +
            COLUMN_PRICE + " INTEGER NOT NULL, " +
            COLUMN_ENERGY + " INTEGER NOT NULL, " +
            COLUMN_PROTEAN + " DECIMAL(4,2) NOT NULL, " +
            COLUMN_CARBOHYDRATES + " DECIMAL(4,2) NOT NULL, " +
            COLUMN_FAT + " DECIMAL(4,2) NOT NULL, " +
            COLUMN_FIBRE + " DECIMAL(4,2) NOT NULL, " +
            COLUMN_TYPE + " VARCHAR(50) NOT NULL, " +
            COLUMN_CATEGORY + " VARCHAR(50) NOT NULL )";

    private final Connection connection;

    /**
     * Connect to the database, create the menu item table if it does not exist
     * and populate the table if it contains no records.
     */
    public MenuItemAccessor(Connection connection) {
        this.connection = connection;
        createMenuItemTable();

        if (readMenuItems().isEmpty()) {
            populateMenuItemTable();
        }
    }

    /**
     * Return all menu items contained in the database.
     *
     * @return A list of menu item's contained in the database.
     */
    @Override
    public List<MenuItem> readMenuItems() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<MenuItem> menuItems = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet menuItemResultSet = statement.executeQuery(sql);

            // For each returned menu item in the database, initialise a MenuItem object.
            while (menuItemResultSet.next()) {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(menuItemResultSet.getInt(COLUMN_ID));
                menuItem.setName(menuItemResultSet.getString(COLUMN_NAME));
                menuItem.setPrice(menuItemResultSet.getInt(COLUMN_PRICE));
                menuItem.setEnergy(menuItemResultSet.getInt(COLUMN_ENERGY));
                menuItem.setProtean(menuItemResultSet.getDouble(COLUMN_PROTEAN));
                menuItem.setCarbohydrates(menuItemResultSet.getDouble(COLUMN_CARBOHYDRATES));
                menuItem.setFat(menuItemResultSet.getDouble(COLUMN_FAT));
                menuItem.setFibre(menuItemResultSet.getDouble(COLUMN_FIBRE));

                String categoryString = menuItemResultSet.getString(COLUMN_CATEGORY);
                String typeString = menuItemResultSet.getString(COLUMN_TYPE);

                // Map the record's category String to an enum value.
                Arrays.stream(MenuItemCategory.values()).forEach(menuItemCategory -> {
                    if (menuItemCategory.toString().toLowerCase().compareTo(categoryString.toLowerCase()) == 0) {
                        menuItem.setCategory(menuItemCategory);
                    }
                });

                // Map the record's type string to an enum value.
                Arrays.stream(MenuItemType.values()).forEach(menuItemType -> {
                    if (menuItemType.toString().toLowerCase().compareTo(typeString.toLowerCase()) == 0) {
                        menuItem.setType(menuItemType);
                    }
                });

                menuItems.add(menuItem);
            }
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
        }

        return menuItems;
    }

    /**
     * Inserts a given menu item into the database.
     * A unique ID for the menu item must be supplied.
     *
     * @param itemToAdd The menu item to add to the database.
     */
    @Override
    public void createMenuItem(MenuItem itemToAdd) {
        String sql = "INSERT INTO " + TABLE_NAME +" (" +
                COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_PRICE + ", " + COLUMN_ENERGY + ", " + COLUMN_PROTEAN + ", " +
                COLUMN_CARBOHYDRATES + ", " + COLUMN_FAT + ", " + COLUMN_FIBRE + ", " + COLUMN_CATEGORY + ", " + COLUMN_TYPE + ")" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemToAdd.getId());
            statement.setString(2, itemToAdd.getName());
            statement.setInt(3, itemToAdd.getPrice());
            statement.setInt(4, itemToAdd.getEnergy());
            statement.setDouble(5, itemToAdd.getProtean());
            statement.setDouble(6, itemToAdd.getCarbohydrates());
            statement.setDouble(7, itemToAdd.getFat());
            statement.setDouble(8, itemToAdd.getFibre());
            statement.setString(9, itemToAdd.getCategory().toString());
            statement.setString(10, itemToAdd.getType().toString());

            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
    }

    /**
     * Create the menu item table. If the table exists an exception is thrown and caught.
     */
    private void createMenuItemTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "MenuItem table is probably already created");
        }
    }

    /**
     * Populate the menu item table with values contained in the menu_item_data.csv file.
     */
    private void populateMenuItemTable() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    MenuItemAccessor.class.getResourceAsStream("/server/resources/menu_item_data.csv"), StandardCharsets.UTF_8));

            String line;
            Boolean firstLine = true;

            // Read each line of the CSV, ignore the first line containing the column headings.
            while ((line = reader.readLine()) != null) {
                if (!firstLine) {
                    String[] itemData = line.split(",");
                    MenuItem item = new MenuItem();

                    Arrays.stream(MenuItemCategory.values()).forEach(menuItemCategory -> {
                        if (menuItemCategory.toString().toLowerCase().compareTo(itemData[0].toLowerCase()) == 0) {
                            item.setCategory(menuItemCategory);
                        }
                    });

                    Arrays.stream(MenuItemType.values()).forEach(menuItemType -> {
                        if (menuItemType.toString().toLowerCase().compareTo(itemData[1].toLowerCase()) == 0) {
                            item.setType(menuItemType);
                        }
                    });

                    item.setName(itemData[2]);
                    item.setPrice(Integer.parseInt(itemData[3]));
                    item.setEnergy(Integer.parseInt(itemData[4]));
                    item.setProtean(Double.parseDouble(itemData[5]));
                    item.setCarbohydrates(Double.parseDouble(itemData[6]));
                    item.setFat(Double.parseDouble(itemData[7]));
                    item.setFibre(Double.parseDouble(itemData[8]));
                    item.setId(Integer.parseInt(itemData[9]));

                    createMenuItem(item);
                } else {
                    firstLine = false;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to access or parse menu_item_data.csv: " + e.toString(), e);
        }
    }
}
