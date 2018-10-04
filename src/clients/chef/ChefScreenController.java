/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.chef;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Imanuel
 */
public class ChefScreenController {
    private ChefScreenView chefScreenView;
    private ChefScreenModel chefScreenModel;
    
    public ChefScreenController(ChefScreenView chefScreenView, ChefScreenModel chefScreenModel){
        this.chefScreenView=chefScreenView;
        this.chefScreenModel=chefScreenModel;
        
        this.chefScreenView.addPrepareButtonListener(new PrepareButtonListener());
    }
    
    class PrepareButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
        
    }
}
