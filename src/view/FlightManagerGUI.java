package view;
import javax.swing.SwingUtilities;

import controller.ControllerFlightGUI;

public class FlightManagerGUI {
    public static void main(String[] args) throws InterruptedException {
    // SwingUtilities.invokeNow(new Runnable() {
    //     @Override
    //     public void run(){

    //     }
    // });
        ControllerFlightGUI demo = new ControllerFlightGUI();
        demo.showGUI();
        demo.tryThis();
    }
}
