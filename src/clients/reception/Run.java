package clients.reception;

/**
 *
 * @author Imanuel
 */
public class Run {
    public static void main(String[] args){
        ReceptionistScreenView receptionistScreenView=new ReceptionistScreenView();
        ReceptionistScreenModel receptionistScreenModel=new ReceptionistScreenModel();
        ReceptionistScreenController receptionistScreenController=new ReceptionistScreenController(receptionistScreenView, receptionistScreenModel);
        receptionistScreenView.setTitle("Restaurant Reception");
        receptionistScreenView.setSize(750,400);
        receptionistScreenView.setVisible(true);
    }
}
