package clients.customer;

public interface ObservableCustomerOrderModel {
    void addReceptionModelObserver(CustomerOrderModelObserver observer);
    void removeReceptionModelObserver(CustomerOrderModelObserver observer);
}
