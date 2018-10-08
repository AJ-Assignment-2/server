/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.reception;

import model.Order.Order;
import model.Order.OrderState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Imanuel
 */
public class ReceptionController implements ReceptionModelObserver {
    private ReceptionView receptionView;
    private ReceptionModel receptionModel;
    
    public ReceptionController(ReceptionView receptionView, ReceptionModel receptionModel){
        this.receptionView = receptionView;
        this.receptionModel = receptionModel;
        this.receptionModel.addReceptionModelObserver(this);
        
        this.receptionView.addMenuAboutListener(new MenuAboutListener());
        this.receptionView.addCustomerButtonListener(new AddCustomerButtonListener());
        this.receptionView.addBillButtonListener(new BillButtonListener());
        this.receptionView.addExitButtonListener(new ExitButtonListener());
    }

    @Override
    public void ordersReceivedFromServer(List<Order> orders) {

    }
    
    private class MenuAboutListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            receptionView.showMessageDialog("","About Us");
        }
    }
    
    private class AddCustomerButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(receptionView.getCustomerName().equals("") || receptionView.getTableNumber().equals("Select table number")){
                receptionView.showErrorDialog("Please input name or select table number", "Input Customer Information");
            } else {
                Order order = new Order();
                order.setCustomerName(receptionView.getCustomerName());
                order.setTableNumber(Integer.parseInt(receptionView.getTableNumber()));
                order.setState(OrderState.WAITING);
               // receptionModel.submitOrder(order);
            }
        }
        
    }
    
    private class BillButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(receptionView.getCustomerName().equals("") || receptionView.getTableNumber().equals("Select table number")){
                receptionView.showErrorDialog("Please input name or select table number", "Input Customer Information");
            }
        }
        
    }
    
    private class ExitButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
        
    }
}
