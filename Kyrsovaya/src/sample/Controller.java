package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.ConnectionToDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    /**Количество попыток*/
    int numberOfAttempts = 0;

    @FXML
    TextField loginTF;
    @FXML
    TextField passwordTF;

    @FXML
    Button loginBtn;
    @FXML
    Button exitBtn;
    @FXML
    Label timerL;

    @FXML
    public void login(){
        try {
            Connection connection = ConnectionToDB.getNewConnection();
            String login = loginTF.getText();
            String password = passwordTF.getText();

            Statement statement = connection.createStatement();
            String sql = String.format("SELECT * FROM session1_01.users WHERE Email='%1$s' AND Password='%2$s'", login, password);
            ResultSet resultSet = statement.executeQuery(sql);

            //Если нет пользователя с таким логином/паролем
            if (!resultSet.next()){
                numberOfAttempts++;
                alert("INCORRECT LOGIN/PASSWORD", "Некорректные данные");
            } else {
                alert("SUCCESS", "Введены корректные данные");
            }

            if (numberOfAttempts > 3){
                timerL.setVisible(true);
                loginTF.setEditable(false);
                passwordTF.setEditable(false);
                loginBtn.setDisable(true);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    int i = 10;
                    public void run() {
                        //Вывод оставшегося времени
                        Platform.runLater(() -> {
                            timerL.setText(i + "");
                        });

                        i--;
                        if (i < 0) {
                            timer.cancel();
                            timerL.setVisible(false);
                            loginTF.setEditable(true);;
                            passwordTF.setEditable(true);
                            loginBtn.setDisable(false);
                            numberOfAttempts = 0;
                        }
                    }
                }, 0, 1000);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void exit(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    private void alert(String head, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(head);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
