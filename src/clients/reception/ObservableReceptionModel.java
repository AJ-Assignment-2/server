package clients.reception;

/**
 * Contract for observable reception models.
 */
public interface ObservableReceptionModel {
    /**
     * Register a reception model observer
     * @param observer the observer to register
     */
    void addReceptionModelObserver(ReceptionModelObserver observer);

    /**
     * Unregister a reception model observer.
     * @param observer The observer to unregister.
     */
    void removeReceptionModelObserver(ReceptionModelObserver observer);
}
