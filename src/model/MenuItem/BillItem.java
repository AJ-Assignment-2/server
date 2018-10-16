/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MenuItem;

import java.io.Serializable;

/**
 *
 * @author Imanuel
 */
public class BillItem implements Serializable {
    private int quantity;
    private String name;
    private int price;
    private int energy;
    private double protean;
    private double carbohydrates;
    private double fat;
    private double fibre;
    private float total;
    
    public BillItem(int quantity, String name, int price, int energy, double protean, double carbohydrates,
                    double fat, double fibre, float total) {
        this.quantity =  quantity;
        this.name = name;
        this.price = price;
        this.energy = energy;
        this.protean = protean;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.fibre = fibre;
        this.total = total;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
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

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setTotalPrice(float total) {
        this.total = total;
    }
    
    public float getTotalPrice() {
        return total;
    }

    BillItem get(int row) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
