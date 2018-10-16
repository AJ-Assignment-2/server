/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.reception;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Imanuel
 */
public class BillController {
    private BillView billView;
    
    public BillController(BillView billView) {
        this.billView = billView;
        
        this.billView.addPrintBillButtonListener(new BillController.PrintBillButtonListener());
    }
    
    private class PrintBillButtonListener implements ActionListener { 

        @Override
        public void actionPerformed(ActionEvent e) {
            billView.showMessageDialog("Print processing", "Print Bill");
            billView.setVisible(false);
        }
        
    }
}
