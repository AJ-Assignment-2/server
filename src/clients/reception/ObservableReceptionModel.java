package clients.reception;

public interface ObservableReceptionModel {
    void addReceptionModelObserver(ReceptionModelObserver observer);
    void removeReceptionModelObserver(ReceptionModelObserver observer);
}
