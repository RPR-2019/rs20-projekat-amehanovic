package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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





    public void btnCreateAccKlik(ActionEvent actionEvent) {
        boolean ok = true;
        for(String x : userUsernames){
            if(x.equals(registerUsername.getText())){
                ok=false;
                break;
            }
        }
        for(String x : adminUsernames){
            if(x.equals(registerUsername.getText())){
                ok=false;
                break;
            }
        }


        if(!ok){
            registerUserValidat.setText("Username already exists! Try another one.");
        }else if(!registerUsername.getText().isEmpty()){
            System.out.println("ok");
        }
    }

    public void btnCancelKlik(ActionEvent actionEvent) {
        Stage stage = (Stage)btnCancel.getScene().getWindow();
        stage.close();
    }
}
