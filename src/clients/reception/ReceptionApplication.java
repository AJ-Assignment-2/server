package clients.reception;

/**
 *
 * @author Imanuel
 */
public class ReceptionApplication {
    public static void main(String[] args){
        ReceptionView receptionView =new ReceptionView();
        ReceptionModel receptionModel =new ReceptionModel();
        ReceptionController receptionController =new ReceptionController(receptionView, receptionModel);
        receptionView.setTitle("Restaurant Reception");
        receptionView.setSize(750,570);
        receptionView.setVisible(true);
    }
}
