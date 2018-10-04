package clients.chef;

/**
 *
 * @author Imanuel
 */
public class Run {
    public static void main(String[] args){
        ChefScreenView chefScreenView=new ChefScreenView();
        ChefScreenModel chefScreenModel=new ChefScreenModel();
        ChefScreenController chefScreenController=new ChefScreenController(chefScreenView, chefScreenModel);
        chefScreenView.setTitle("Chef Screen Status");
        chefScreenView.setSize(1200,300);
        chefScreenView.setVisible(true);
    }
}
