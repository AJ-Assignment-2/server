package model.MenuItem;

/**
 * Represents the different types of menu items.
 */
public enum MenuItemType {
    BREAKFAST,
    LUNCH,
    DINNER;

    @Override
    public String toString() {
        switch (this) {
            case BREAKFAST:
                return "Breakfast";
            case LUNCH:
                return "Lunch";
            case DINNER:
                return "Dinner";
            default:
                return "Undefined";

        }
    }
}
