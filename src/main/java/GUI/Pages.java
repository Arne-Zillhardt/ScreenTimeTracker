package GUI;

import Backend.Clock;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;

public class Pages {
    Clock clock;
    Label timeLabel;
    double time;
    double sec;
    double min;
    double hou;

    public void prompt() {
        Label loginPage = new Label("Welcome");
        loginPage.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Button signIn = new Button("Sign in");
        signIn.setOnAction(Event -> {
            clear();
            signIn();
        });

        Button signUP = new Button("Sign up");
        signUP.setOnAction(Event -> {
            clear();
            signUp();
        });

        GUI.pane.add(loginPage, 1, 0);
        GUI.pane.add(signIn, 0, 1);
        GUI.pane.add(signUP, 2, 1);

        GUI.pane.setHgap(10);
        GUI.pane.setVgap(10);

        sec = 0;
        min = 0;
        hou = 0;
    }

    public void signIn() {
        Label loginPage = new Label("Login Page");
        loginPage.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Label userName = new Label("Username: ");
        Label pw = new Label("Password: ");

        TextField inputUserName = new TextField();
        PasswordField pwField = new PasswordField();

        Button signIn = new Button("Login");
        HBox hBox = new HBox();
        hBox.getChildren().add(signIn);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        signIn.setOnAction(Event -> {
            GUI.signIn(inputUserName, pwField);
        });

        GUI.pane.add(loginPage, 0, 0);
        GUI.pane.add(userName, 0, 1);
        GUI.pane.add(inputUserName, 1, 1);

        GUI.pane.add(pw, 0, 2);
        GUI.pane.add(pwField, 1, 2);

        GUI.pane.add(hBox, 1, 3);

        GUI.pane.setHgap(10);
        GUI.pane.setVgap(10);
    }

    void signUp(){
        Label signUpPage = new Label("Sign Up Page");
        signUpPage.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Label userName = new Label("Username: ");
        Label pw = new Label("Password: ");
        Label pw2 = new Label("Retype password: ");

        TextField inputUserName = new TextField();
        PasswordField pwField01 = new PasswordField();
        PasswordField pwField02 = new PasswordField();

        Button signUp = new Button("Sign Up");
        HBox hBox = new HBox();
        hBox.getChildren().add(signUp);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        signUp.setOnAction(Event -> {
            GUI.signUp(inputUserName, pwField01, pwField02);
        });

        GUI.pane.add(signUpPage, 0, 0);
        GUI.pane.add(userName, 0, 1);
        GUI.pane.add(inputUserName, 1, 1);

        GUI.pane.add(pw, 0, 2);
        GUI.pane.add(pw2, 0, 3);
        GUI.pane.add(pwField01, 1, 2);
        GUI.pane.add(pwField02, 1, 3);

        GUI.pane.add(hBox, 1, 4);

        GUI.pane.setHgap(10);
        GUI.pane.setVgap(10);
    }

    void homeScreen() throws Exception {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(50);

        Label homeScreen = new Label("Home Screen");
        homeScreen.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        pane.add(homeScreen, 0, 0);

        timeLabel = new Label("Time used the device: ");
        GridPane timePane = new GridPane();
        timePane.setAlignment(Pos.CENTER);
        timePane.add(timeLabel, 0, 0);

        MenuButton menu = new MenuButton("Choose a device");
        MenuItem mobile = new MenuItem("Mobile phone");
        MenuItem console = new MenuItem("Console");
        MenuItem pc = new MenuItem("PC/Laptop");
        MenuItem other = new MenuItem("other");
        menu.getItems().addAll(mobile, console, pc, other);
        for (MenuItem item : menu.getItems()) {
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    double width = menu.getWidth();

                    menu.setText(item.getText());

                    GUI.user.device = item.getText();
                    System.out.println(GUI.user.device);

                    setUsedTime();
                    timeLabel.setText("Time used the device: " + time + " min");

                    menu.setPrefWidth(width);
                }
            });
        }
        pane.add(menu, 0, 1);

        GridPane startStop = new GridPane();
        startStop.setAlignment(Pos.CENTER);

        Button start = new Button("Start");
        startStop.add(start, 0, 0);
        start.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clock = new Clock();
                clock.start(Pages.this, hou, min, sec);
            }
        });

        Button stop = new Button("Stop");
        startStop.add(stop, 1, 0);
        stop.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    GUI.handler.setTime(time, GUI.user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                clock.interrupt();
            }
        });

        GUI.pane.add(pane, 0, 0);
        //GUI.pane.add(homeScreen, 0, 0);
        GUI.pane.add(timePane, 0, 1);
        //GUI.pane.add(menu, 0, 1);
        GUI.pane.add(startStop, 0, 2);
        //GUI.pane.add(hBoxStart, 0, 3);
        //GUI.pane.add(hBoxStop, 0, 3);
    }

    void setUsedTime() {
        try {
            time = GUI.handler.usedTime(GUI.user);
            double tmp = time;

            hou = Math.floor(time / 60);
            tmp -= hou * 60;

            min = Math.floor(tmp);
            tmp -= min;

            sec = tmp * 60;

            System.out.println(hou + " " + min + " " + sec);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTime(double h, double m, double s, double t) {
        hou = h;
        min = m;
        sec = s;

        time = t;

        timeLabel.setText(String.format("%.0f:%.0f:%.2f", hou, min, sec));
    }

    void clear() {
        GUI.pane.getChildren().clear();
    }
}
