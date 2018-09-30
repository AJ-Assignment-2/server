package model.MenuItem;

/**
 * Object to represent a single menu item.
 */
public class MenuItem {
    private String name;
    private MenuItemType type;
    private MenuItemCategory category;
    private int id;
    private int price;
    private int energy;
    private double protean;
    private double carbohydrates;
    private double fat;
    private double fibre;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public double getProtean() {
        return protean;
    }

    public void setProtean(double protean) {
        this.protean = protean;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getFibre() {
        return fibre;
    }

    public void setFibre(double fibre) {
        this.fibre = fibre;
    }

    public MenuItemType getType() {
        return type;
    }

    public void setType(MenuItemType type) {
        this.type = type;
    }

    public MenuItemCategory getCategory() {
        return category;
    }

    public void setCategory(MenuItemCategory category) {
        this.category = category;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MenuItem(int id, String name, int price, int energy, double protean, double carbohydrates,
                    double fat, double fibre, MenuItemCategory category, MenuItemType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.energy = energy;
        this.protean = protean;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.fibre = fibre;
        this.type = type;
        this.category = category;
    }

    public MenuItem() {
    }
}
