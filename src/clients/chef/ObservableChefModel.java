package clients.chef;

public interface ObservableChefModel {
    void addChefModelObserver(ChefModelObserver observer);
    void removeChefModelObserver(ChefModelObserver observer);
}
