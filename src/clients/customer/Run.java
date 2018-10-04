package clients.customer;

/**
 *
 * @author Imanuel
 */
public class Run {
    public static void main(String[] args){
        RestaurantOrderView restaurantOrderView=new RestaurantOrderView();
        RestaurantOrderModel restaurantOrderModel=new RestaurantOrderModel();
        RestaurantOrderController restaurantOrderController=new RestaurantOrderController(restaurantOrderView, restaurantOrderModel);
        restaurantOrderView.setTitle("Restaurant Order");
        restaurantOrderView.setSize(900,500);
        restaurantOrderView.setVisible(true);
    }
}
