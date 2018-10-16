package clients.reception;

/**
 * The entry point for the reception application.
 */
public class ReceptionApplication {
    public static void main(String[] args){
        ReceptionView receptionView =new ReceptionView();
        ReceptionModel receptionModel =new ReceptionModel();
        ReceptionController receptionController =new ReceptionController(receptionView, receptionModel);
        receptionView.setTitle("Restaurant Reception");
        receptionView.setSize(1100,600);
        receptionView.setVisible(true);
    }
}
