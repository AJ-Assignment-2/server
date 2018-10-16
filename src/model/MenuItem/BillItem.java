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
    private float total;
    
    public BillItem(int quantity, String name, int price, float total) {
        this.quantity =  quantity;
        this.name = name;
        this.price = price;
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
