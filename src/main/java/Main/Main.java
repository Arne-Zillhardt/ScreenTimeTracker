package Main;

import GUI.GUI;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {
        launch(GUI.class, args);

        //System.out.println(Math.floor(10.9999999));

        /*Backend.Clock clock = new Backend.Clock();
        clock.start();

        try {
            Thread.sleep(1000);
            clock.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }

         */
    }
}
