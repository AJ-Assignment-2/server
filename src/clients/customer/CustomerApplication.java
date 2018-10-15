package clients.customer;

/**
 * The entry point for the customer client application.
 */
public class CustomerApplication {
    public static void main(String[] args){
        CustomerOrderView customerOrderView =new CustomerOrderView();
        CustomerOrderModel customerOrderModel =new CustomerOrderModel();
        CustomerOrderController customerOrderController =new CustomerOrderController(customerOrderView, customerOrderModel);
        customerOrderView.setTitle("Restaurant Order");
        customerOrderView.setSize(1100,500);
        customerOrderView.setVisible(true);
    }
}
