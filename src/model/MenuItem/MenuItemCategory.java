package model.MenuItem;

/**
 * Represents the different categories of a menu item.
 */
public enum MenuItemCategory {
    FOOD,
    BEVERAGE;

    @Override
    public String toString() {
        switch (this) {
            case FOOD:
                return "Food";
            case BEVERAGE:
                return "Beverage";
            default:
                return "undefined";
        }
    }
}
