package clients.chef;

/**
 * The contract for observable chef model's.
 */
public interface ObservableChefModel {
    /**
     * Register a chef model observer.
     * @param observer Chef model observer to register.
     */
    void addChefModelObserver(ChefModelObserver observer);

    /**
     * Unregister a chef model observer.
     * @param observer Chef model observer to unregister.
     */
    void removeChefModelObserver(ChefModelObserver observer);
}
