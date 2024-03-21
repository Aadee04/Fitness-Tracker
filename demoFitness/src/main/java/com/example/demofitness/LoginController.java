package com.example.demofitness;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML
    private Label error_msg; // login error

    @FXML
    private Button button_signup, button_reg; // login buttons

    @FXML
    private TextField username_login, password_login ; // login text

    public void LoginFunction(){
        String username_field = username_login.getText();
        username_field = username_field.trim();
        String password_field = password_login.getText();
        password_field = password_field.trim();

        //fix alignment for error msg @aadee
        if((username_field).isEmpty()) {
            error_msg.setTextAlignment(TextAlignment.CENTER);
            error_msg.setText("Enter Username!");
        }
        else if((password_field).isEmpty()) {
            error_msg.setTextAlignment(TextAlignment.CENTER);
            error_msg.setText("Enter Password!");
        }

        else { // change for invalid account
            String db_user = "root";
            String db_pwd = "";
            String url = "jdbc:mysql://localhost:3306/fitness";
            try {
                //connecting
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, db_user, db_pwd);

                String sql_query = "SELECT  *  from users WHERE name = ?;";
                PreparedStatement pstmt = con.prepareStatement(sql_query);
                pstmt.setString(1, username_field);
                ResultSet rs = pstmt.executeQuery();
                if(!rs.isBeforeFirst()) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("User Not Found");
                    a.setContentText("User Does Not Exist");
                    a.showAndWait();
                }
                else{
                    while(rs.next()){
                        String user_name_db = rs.getString(2);
                        String user_pwd_db = rs.getString(4);
                        int u_id = rs.getInt(1);
                        if(user_pwd_db.equals(password_field) && user_name_db.equals(username_field)){
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Successful Login");
                            a.setContentText("Proceed!");
                            a.showAndWait();
                            switchToHome(username_field, u_id);
                        }
                        else{
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setTitle("Invalid Information");
                            a.setContentText("Incorrect Name or Password");
                            a.showAndWait();
                        }
                    }
                }
            }
            catch (Exception e){

                e.printStackTrace();
            }
        }
    }

    public void switchToRegistration() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration.fxml")); //loading login page
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) button_signup.getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
    }

    public void switchToHome(String s, int u) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml")); //loading login page
        Parent root = fxmlLoader.load();

        mainController mainController = fxmlLoader.getController();
        mainController.display_text(s, u);

        Stage stage = (Stage) button_signup.getScene().getWindow();
        Scene scene = new Scene(root, 1280, 720);
        stage.setX(250);
        stage.setY(150);
        stage.setScene(scene);
    }

}