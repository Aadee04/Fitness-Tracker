package com.example.demofitness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationController {
    @FXML
    private Label error_msg_reg;
    @FXML
    private TextField name_reg, mail_reg, pwd_reg, number_reg, age_reg, height_reg, weight_reg;
    @FXML
    private Button button_reg;
    @FXML
    private RadioButton male_reg, female_reg;
    public void RegistrationFunction(){
        String name_field = name_reg.getText();
        name_field = name_field.trim();
        String mail_field = mail_reg.getText();
        mail_field = mail_field.trim();
        String password_field = pwd_reg.getText();
        password_field = password_field.trim();
        String contact_field = number_reg.getText();
        contact_field = contact_field.trim();
        String age_field = age_reg.getText();
        age_field = age_field.trim();
        String height_field = height_reg.getText();
        height_field = height_field.trim();
        String weight_field = weight_reg.getText();
        weight_field = weight_field.trim();

        if((name_field).isEmpty()){
            error_msg_reg.setText("Enter Name");
        }
        else if((mail_field).isEmpty()){
            error_msg_reg.setText("Enter Mail");
        }
        else if((password_field).isEmpty()){
            error_msg_reg.setText("Enter Password");
        }
        else if((contact_field).isEmpty()){
            error_msg_reg.setText("Enter Contact");
        }
        else if((age_field.trim()).isEmpty()){
            error_msg_reg.setText("Enter Age");
        }
        else if((!male_reg.isSelected()) && (!female_reg.isSelected())){
            error_msg_reg.setText("Select Gender");
        }
        else if((height_field.trim()).isEmpty()){
            error_msg_reg.setText("Enter Height");
        }
        else if((weight_field.trim()).isEmpty()){
            error_msg_reg.setText("Enter Weight");
        }

        else{
            int age = 0;
            long contact = 0;
            float height = 0, weight = 0;
            boolean gender = male_reg.isSelected();

            Pattern pat = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$");
            if(!(pat.matcher(mail_field).matches())){
                error_msg_reg.setText("Email Invalid");
            }
            else {
                try {
                    contact = Long.parseLong(contact_field);
                    String regex = "^\\d{10}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(contact_field);
                    if(!matcher.matches()){
                        error_msg_reg.setText("Invalid Phone Number");
                    }
                } catch (NumberFormatException e) {
                    error_msg_reg.setText("Phone Number Must be A Number");
                }

                try {
                    age = Integer.parseInt(age_field);
                } catch (NumberFormatException e) {
                    error_msg_reg.setText("Age Must be A Number");
                }

                try {
                    height = Float.parseFloat(height_field);
                } catch (NumberFormatException e) {
                    error_msg_reg.setText("Height Must be A Number");
                }

                try {
                    weight = Float.parseFloat(weight_field);
                } catch (NumberFormatException e) {
                    error_msg_reg.setText("Weight Must be A Number");
                }

                //String data = name_field + " " + mail_field + " " + password_field + " " + contact_field + " " + age_field + " " + gender + " " + height + " "  + weight;
                //System.out.println(data);

                getConnection(name_field, mail_field, password_field, contact, age, gender, height, weight);
            }

        }
    }

    public void switchToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml")); //loading login page
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) button_reg.getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
    }

    public void getConnection(String name_field, String mail_field, String password_field, long contact, int age, boolean gender, float height, float weight){
        String db_user = "root";
        String db_pwd = "";
        String url = "jdbc:mysql://localhost:3306/fitness";
        try {
            //connecting
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, db_user, db_pwd);

            //querying
            String sql_query = "INSERT INTO users (name, mail_id, password, phone_no, age, gender, height, weight)  VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(sql_query);

            //setting parameters for data
            pstmt.setString(1, name_field);
            pstmt.setString(2, mail_field);
            pstmt.setString(3, password_field);
            pstmt.setString(4, String.valueOf((contact)));
            pstmt.setInt(5, (age));
            pstmt.setBoolean(6, (gender));
            pstmt.setInt(7, (int) height);
            pstmt.setFloat(8, (weight));

            //executing query
            pstmt.executeUpdate();

            //Success
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Successful Registration");
            a.setContentText("Proceed to Login");
            a.showAndWait();

            //closing
            pstmt.close();
            con.close();

            //switching back to log in
            switchToLogin();
        }
        catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Invalid Data");
            a.setContentText("Please Enter Data Correctly");
            a.showAndWait();
            e.printStackTrace();
        }
    }
}
