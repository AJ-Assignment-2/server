package clients.customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import model.MenuItem.MenuItem;
import static model.MenuItem.MenuItemCategory.BEVERAGE;
import static model.MenuItem.MenuItemCategory.FOOD;
import model.MenuItem.MenuItemType;
import model.Order.Order;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Imanuel
 */
public class RestaurantOrderController {
    private RestaurantOrderView restaurantOrderView;
    private RestaurantOrderModel restaurantOrderModel;
    
    public RestaurantOrderController(RestaurantOrderView restaurantOrderView, RestaurantOrderModel restaurantOrderModel){
        this.restaurantOrderModel=restaurantOrderModel;
        this.restaurantOrderView=restaurantOrderView;            
        
        this.restaurantOrderView.addBreakfastRadioButtonListener(new BreakfastRadioButtonListener());
        this.restaurantOrderView.addLunchRadioButtonListener(new LunchRadioButtonListener());
        this.restaurantOrderView.addDinnerRadioButtonListener(new DinnerRadioButtonListener());
        this.restaurantOrderView.addEnterDataButtonListener(new EnterDataButtonListener());
        this.restaurantOrderView.addDisplayChoicesButtonListener(new DisplayChoicesButtonListener());
        this.restaurantOrderView.addDisplayOrderButtonListener(new DisplayOrderButtonListener());
        this.restaurantOrderView.addClearDisplayButtonListener(new ClearButtonListener());
        this.restaurantOrderView.addQuitButtonListener(new QuitSystem());
    }
    
    class BreakfastRadioButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<MenuItem> foodMenu=restaurantOrderModel.getMenuWithTypeCategory(FOOD, MenuItemType.BREAKFAST);
            List<MenuItem> beverageMenu=restaurantOrderModel.getMenuWithTypeCategory(BEVERAGE, MenuItemType.BREAKFAST);
            restaurantOrderView.addItemComboBox(foodMenu, beverageMenu);
        }
        
    }
    
    class LunchRadioButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<MenuItem> foodMenu=restaurantOrderModel.getMenuWithTypeCategory(FOOD, MenuItemType.LUNCH);
            List<MenuItem> beverageMenu=restaurantOrderModel.getMenuWithTypeCategory(BEVERAGE, MenuItemType.LUNCH);
            restaurantOrderView.addItemComboBox(foodMenu, beverageMenu);
        }
        
    }
    
    class DinnerRadioButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<MenuItem> foodMenu=restaurantOrderModel.getMenuWithTypeCategory(FOOD, MenuItemType.DINNER);
            List<MenuItem> beverageMenu=restaurantOrderModel.getMenuWithTypeCategory(BEVERAGE, MenuItemType.DINNER);
            restaurantOrderView.addItemComboBox(foodMenu, beverageMenu);
        }
        
    }
    
    class EnterDataButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String customerName=restaurantOrderView.getCustomerName();
            String customerTable=restaurantOrderView.getCustomerTable();
            String chosenFood=restaurantOrderView.getChosenFood();
            String chosenBeverage=restaurantOrderView.getChosenBeverage();
            if(!chosenFood.equals("-------- Select the food --------") || !chosenBeverage.equals("-------- Select the beverage --------")){
                ////////////////save to ther server
                System.out.println("SAVE TO SERVER AT THIS TIME");
                System.out.println("CUSTOMER NAME: "+restaurantOrderView.getCustomerName());
                System.out.println("CUSTOMER TABLE: "+restaurantOrderView.getCustomerTable());
                System.out.println("CUSTOMER CHOOSE FOOD: "+chosenFood);
                System.out.println("CUSTOMER CHOOSE BEVERAGE: "+chosenBeverage);
                restaurantOrderModel.setListCustomerOrder(customerName, customerTable, chosenFood, chosenBeverage);
            } else restaurantOrderView.showErrorDialog("Hi "+restaurantOrderView.getCustomerName()+"\nPlease select food and beverage", "Select Menu");         
        }
    }
    
    class DisplayChoicesButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String chosenFood=restaurantOrderView.getChosenFood();
            String chosenBeverage=restaurantOrderView.getChosenBeverage();
            if(!chosenFood.equals("-------- Select the food --------") || !chosenBeverage.equals("-------- Select the beverage --------")){
                restaurantOrderView.displayDetailsChoices();
                
            } else restaurantOrderView.showErrorDialog("Hi "+restaurantOrderView.getCustomerName()+"\nPlease select food and beverage", "Select Menu");         
        }
        
    }
    
    class DisplayOrderButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> allOrders=new ArrayList<>();
            String chosenFood=restaurantOrderView.getChosenFood();
            String chosenBeverage=restaurantOrderView.getChosenBeverage();
            if(restaurantOrderModel.getAllOrders().size()!=0){
                allOrders=restaurantOrderModel.getAllOrders();
                restaurantOrderView.showAllOrders(allOrders);
            } else restaurantOrderView.showErrorDialog("Hi "+restaurantOrderView.getCustomerName()+"\nPlease order at least one food or beverage", "Order Menu");
        }
    }
    
    class ClearButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            restaurantOrderView.setResetScreen();
        }
        
    }
    
    class QuitSystem implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
//            Order newOrder = new Order();
//        
//            newOrder.addItem(menuItem);
            System.exit(0);
        }
        
    }
}
