package clients.chef;

/**
 * Entry point for the chef client application.
 */
public class ChefApplication {
    public static void main(String[] args){
        ChefView chefView =new ChefView();
        ChefModel chefModel =new ChefModel();
        ChefController chefController =new ChefController(chefView, chefModel);
        chefView.setTitle("Chef Screen Status");
        chefView.setSize(1200,500);
        chefView.setVisible(true);
    }
}
