/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.chef;

import model.Order.Order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Imanuel
 */
public class ChefController implements ChefModelObserver{
    private ChefView chefView;
    private ChefModel chefModel;
    
    public ChefController(ChefView chefView, ChefModel chefModel){
        this.chefView = chefView;
        this.chefModel = chefModel;
        this.chefModel.addChefModelObserver(this);
        
        this.chefView.addPrepareButtonListener(new PrepareButtonListener());
    }
    
    class PrepareButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
        
    }

    @Override
    public void waitingOrdersUpdated(List<Order> waitingOrders) {
        System.out.println("test");
        waitingOrders.stream().forEach(order -> System.out.println(order.getCustomerName() + " " + order.getState()));
    }
}
