package clients.customer;

import java.util.ArrayList;
import java.util.List;
import model.MenuItem.MenuItem;
import model.MenuItem.MenuItemCategory;
import model.MenuItem.MenuItemType;
import server.service.MenuItemService.MenuItemAccessor;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Imanuel
 */
public class RestaurantOrderModel {
    private MenuItemAccessor menuItemAccessor;
    private MenuItem menuItem;
    private List<MenuItem> menuItems;
    private ArrayList<String> customerOrderList;
    
    
    public RestaurantOrderModel(){
        this.menuItemAccessor=new MenuItemAccessor();
        this.menuItem=new MenuItem();
        menuItems = menuItemAccessor.readMenuItems();
        customerOrderList=new ArrayList<>();
    }
    
    public int getFoodSize(){
        return getMenuWithCategory(MenuItemCategory.FOOD).size();
    }
    
    public int getBeverageSize(){
        return getMenuWithCategory(MenuItemCategory.BEVERAGE).size();
    }
    
    public List<MenuItem> getMenuWithType(MenuItemType type) {
        List<MenuItem> menusWithType = new ArrayList<>();
        
        menuItems.forEach(item -> {
            if (item.getType() == type) menusWithType.add(item);
        });
        
        return menusWithType;
    }
    
    public List<MenuItem> getMenuWithCategory(MenuItemCategory category) {
        List<MenuItem> menusWithCategory = new ArrayList<>();
        
        menuItems.forEach(item -> {
            if (item.getCategory() == category) menusWithCategory.add(item);
        });
        
        return menusWithCategory;
    }

    public List<MenuItem> getMenuWithTypeCategory(MenuItemCategory category, MenuItemType type) {
        List<MenuItem> foodsWithTypeCategory = new ArrayList<>();
        
        menuItems.forEach(item -> {
            if (item.getCategory() == category && item.getType() == type) foodsWithTypeCategory.add(item);
        });
        
        return foodsWithTypeCategory;
    }
    
    public void setListCustomerOrder(String customerName, String customerTable, String foodOrder, String beverageOrder){
        System.out.println("CN: "+customerName);
        System.out.println("CT: "+customerTable);
        System.out.println("FO: "+foodOrder);
        System.out.println("BO: "+beverageOrder);
        if(!foodOrder.equals("-------- Select the food --------") && !beverageOrder.equals("-------- Select the beverage --------")){
            for(MenuItem menu: getMenuWithCategory(MenuItemCategory.FOOD)){
                if(menu.getName().equals(foodOrder)){
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA111111111111111111111");
                    customerOrderList.add(orderedCustomer(menu));
                }
            }
            for(MenuItem menu: getMenuWithCategory(MenuItemCategory.BEVERAGE)){
                if(menu.getName().equals(beverageOrder)){
                    System.out.println("AAAAAAAAAAAAAAAAAAAAA22222222222222222222222222222222222");
                    customerOrderList.add(orderedCustomer(menu));
                }
            }
        }else if(beverageOrder.equals("-------- Select the beverage --------")){
            for(MenuItem menu: getMenuWithCategory(MenuItemCategory.FOOD)){
                if(menu.getName().equals(foodOrder)){
                    System.out.println("BBBBBBBBBBBBBBB");
                    customerOrderList.add(orderedCustomer(menu));
                }
            }
        }else if(foodOrder.equals("-------- Select the food --------")){
            for(MenuItem menu: getMenuWithCategory(MenuItemCategory.BEVERAGE)){
                if(menu.getName().equals(beverageOrder)){
                    System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCC");
                    customerOrderList.add(orderedCustomer(menu));
                }
            }
        }
        System.out.println("COList: "+customerOrderList);
    }
    
    public ArrayList<String> getAllOrders(){
        return customerOrderList;
    }
    
    public String orderedCustomer(MenuItem menu){
        return menu.getName()+","+menu.getEnergy()+","+menu.getProtean()+","+menu.getCarbohydrates()+","+menu.getFat()+","+menu.getFibre()+","+menu.getPrice();
    }
}
