package GUI;

import Database.DbHandler;
import Database.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application {
    static Stage stage;
    static Scene scene;
    static GridPane pane;
    static DbHandler handler;
    public static Pages pages;
    public static User user;

    @Override
    public void start(Stage stage) throws Exception {
        handler = new DbHandler();

        SetUp setUp = new SetUp();
        setUp.start(stage);

        pages = new Pages();
        pages.prompt();
    }

    public static void signIn(TextField useNam, TextField pas) {
        String name = useNam.getText();
        String password = pas.getText();

        try {
            if (!handler.connectDb()) {
                return;
            }

            System.out.println("Connected");

            if (!handler.selectUser(name, password)) {
                return;
            }

            System.out.println("Successfully selected user");

            pages.clear();
            pages.homeScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void signUp(TextField useNam, TextField pas01, TextField pas02) {
        String name = useNam.getText();
        String password01 = pas01.getText();
        String password02 = pas02.getText();

        try {
            if (!password01.equals(password02)) {
                return;
            }

            if (!handler.connectDb()) {
                return;
            }

            System.out.println("Connected");

            if (!handler.createUser(name, password01)) {
                return;
            }

            System.out.println("Successfully created user");

            signIn(new TextField(name), new TextField(password01));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}