package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterController {
    public Button btnCancel;
    public PasswordField registerPassword;
    public TextField registerUsername;
    public Button btnCreateAcc;
    public Label registerUserValidat;
    private LoginDAO dao;
    public ArrayList<String> userUsernames;
    public ArrayList<String> adminUsernames;


    @FXML
    public void initialize() throws SQLException {
        dao = LoginDAO.getInstance();

        userUsernames=dao.getUsernamesUsers();
        adminUsernames=dao.getUsernamesAdmins();
    }



    public void btnCreateAccKlik(ActionEvent actionEvent) throws IOException {
        if(isDuplicate())
            registerUserValidat.setText("Username already exists! Try another one.");
        else if(!validateUsername(registerUsername.getText()))
            registerUserValidat.setText("       Username can only contain:" + "\nletters, numbers and 5-15 characters!");
        else if(!registerPassword.getText().matches("^.{8,32}$"))
            registerUserValidat.setText("Password must be between 8 and 32 characters!");
        else if(!validatePassword(registerPassword.getText()))
            registerUserValidat.setText("                Password has to contain:" + "\n1 number, 1 lowercase and 1 uppercase letter");
        else if(!isDuplicate() && validatePassword(registerPassword.getText()) && validateUsername(registerUsername.getText())) {
            registerUserValidat.setText("");
            registerNewUser(registerUsername.getText(), registerPassword.getText());

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Registration successful");
            a.setHeaderText(null);
            a.setContentText("You have successfully registered your new account: "+registerUsername.getText());
            a.showAndWait();

            Stage mystage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            mystage.setTitle("Log in");
            mystage.setScene(new Scene(root, 350, 380));
            mystage.setResizable(false);
            mystage.show();

            Stage stage = (Stage) registerUserValidat.getScene().getWindow();
            stage.close();


        }


    }

    public void btnCancelKlik(ActionEvent actionEvent) throws IOException {
        Stage mystage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        mystage.setTitle("Log in");
        mystage.setScene(new Scene(root, 350, 380));
        mystage.setResizable(false);
        mystage.show();

        Stage stage = (Stage) registerUserValidat.getScene().getWindow();
        stage.close();
    }

    public boolean validateUsername(String username){
        return username.matches("^[A-Za-z]\\w{4,15}$");
    }

    public boolean validatePassword(String password){
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$");
    }

    public boolean isDuplicate(){

        for(String x : userUsernames){
            if(x.equals(registerUsername.getText()))
                return true;
        }

        for(String x : adminUsernames){
            if(x.equals(registerUsername.getText()))
                return true;
        }

        return false;
    }

    public void registerNewUser(String username, String password){
        dao.registerUser(username, password);
    }
}
