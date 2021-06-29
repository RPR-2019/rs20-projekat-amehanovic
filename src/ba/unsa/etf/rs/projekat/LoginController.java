package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {

    public TextField loginUsername;
    public PasswordField loginPassword;
    public Label loginErrorLabel;
    private LoginDAO dao;

    private ArrayList<Login> users, admins;

    private Login currentUser;



    @FXML
    public void initialize() throws SQLException {
        dao = LoginDAO.getInstance();
        users = dao.getUsers();
        admins = dao.getAdmins();

        loginUsername.setPromptText("Enter username");
        loginPassword.setPromptText("Enter password");
    }

    public void registerKlik(ActionEvent actionEvent) throws IOException {
        Stage mystage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        mystage.setTitle("Home");
        mystage.setScene(new Scene(root, 350, 350));
        mystage.setResizable(false);
        mystage.show();
    }

    public void loginKlik(ActionEvent actionEvent) throws IOException {
        boolean logged = false;

        for(Login x : users){
            if((x.getUsername().equals(loginUsername.getText())) && (x.getPassword().equals(loginPassword.getText()))){
                logged = true;
                currentUser = x;
                break;
            }
        }

        if(logged){
            Stage mystage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
            mystage.setTitle("Home");
            mystage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));

            Stage stage = (Stage) loginUsername.getScene().getWindow(); stage.close();

            mystage.show();
        }else{
            if(loginUsername.getText().isEmpty())
                loginErrorLabel.setText("Please enter your username!");

            if(loginPassword.getText().isEmpty())
                loginErrorLabel.setText("Please enter your password!");


            if(loginPassword.getText().isEmpty() && loginUsername.getText().isEmpty())
                loginErrorLabel.setText("Please enter your username and password!");


            if(!(loginPassword.getText().isEmpty()) && !(loginUsername.getText().isEmpty()))
                loginErrorLabel.setText("Wrong credentials. Try again, or register!");
        }
    }
}
