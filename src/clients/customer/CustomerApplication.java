package clients.customer;

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
