package ba.unsa.etf.rs.projekat;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class LoginDAO {
    private static LoginDAO instance;
    private Connection conn;

    private PreparedStatement getUsersUpit;
    private PreparedStatement getAdminsUpit;
    private PreparedStatement getUsernamesAdminsUpit;
    private PreparedStatement getUsernamesUsersUpit;

    public static LoginDAO getInstance() {
        if (instance == null) instance = new LoginDAO();
        return instance;
    }

    private LoginDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:logins.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            getUsersUpit = conn.prepareStatement("SELECT * FROM users");
            getAdminsUpit = conn.prepareStatement("SELECT * FROM admins");
            getUsernamesAdminsUpit = conn.prepareStatement("SELECT username FROM users");
            getUsernamesUsersUpit = conn.prepareStatement("SELECT username FROM admins");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public ArrayList<Login> getUsers(){
        ArrayList<Login> ret = new ArrayList<>();

        try {
            ResultSet rs = getUsersUpit.executeQuery();

            while(rs.next()){
                Login l = new Login(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                ret.add(l);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return ret;
    }

    public ArrayList<Login> getAdmins(){
        ArrayList<Login> ret = new ArrayList<>();

        try {
            ResultSet rs = getAdminsUpit.executeQuery();

            while(rs.next()){
                Login l = new Login(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                ret.add(l);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ret;
    }

    public ArrayList<String> getUsernamesUsers(){
        ArrayList<String> ret = new ArrayList<>();

        try {
            ResultSet rs = getUsernamesUsersUpit.executeQuery();
            while(rs.next()) {
                String usr = rs.getString(1);
                ret.add(usr);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }


    public ArrayList<String> getUsernamesAdmins(){
        ArrayList<String> ret = new ArrayList<>();

        try {
            ResultSet rs = getUsernamesAdminsUpit.executeQuery();
            while(rs.next()) {
                String usr = rs.getString(1);
                ret.add(usr);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }
}
