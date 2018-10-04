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

/**
 *
 * @author Imanuel
 */
public class ReceptionistScreenController {
    private ReceptionistScreenView receptionistScreenView;
    private ReceptionistScreenModel receptionistScreenModel;
    
    public ReceptionistScreenController(ReceptionistScreenView receptionistScreenView, ReceptionistScreenModel receptionistScreenModel){
        this.receptionistScreenView=receptionistScreenView;
        this.receptionistScreenModel=receptionistScreenModel;
        
        this.receptionistScreenView.addMenuAboutListener(new MenuAboutListener());
        this.receptionistScreenView.addCustomerButtonListener(new AddCustomerButtonListener());
        this.receptionistScreenView.addBillButtonListener(new BillButtonListener());
        this.receptionistScreenView.addExitButtonListener(new ExitButtonListener());
    }
    
    class MenuAboutListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            receptionistScreenView.showMessageDialog("","About Us");
        }
    }
    
    class AddCustomerButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(receptionistScreenView.getCustomerName().equals("") || receptionistScreenView.getTableNumber().equals("Select table number")){
                receptionistScreenView.showErrorDialog("Please input name or select table number", "Input Customer Information");
            }

            Order newOrder = new Order();
            newOrder.setTableNumber();
            newOrder.setCustomerName();
            newOrder.setState(OrderState.WAITING);
        }
        
    }
    
    class BillButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(receptionistScreenView.getCustomerName().equals("") || receptionistScreenView.getTableNumber().equals("Select table number")){
                receptionistScreenView.showErrorDialog("Please input name or select table number", "Input Customer Information");
            }
        }
        
    }
    
    class ExitButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
        
    }
}
