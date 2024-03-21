package com.example.demofitness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class mealListItemController {

    @FXML
    private Button addFoodButton;

    @FXML
    private Text foodName, foodName1, foodName2, foodName3, foodName4;

    @FXML
    private AnchorPane mealListItemPane;

    private int Index;
    private String ca, pr, car, f;

    private int user_id;

    public void setItem(String s, String cal, String pro, String carbs, String fat, int user_id){
        foodName.setText(s);
        foodName1.setText(cal);
        foodName2.setText(pro);
        foodName3.setText(carbs);
        foodName4.setText(fat);
        this.user_id = user_id;
    }

    public void addItem(){

        String db_user = "root";
        String db_pwd = "";
        String url = "jdbc:mysql://localhost:3306/fitness";
        try {
            //connecting
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, db_user, db_pwd);

            //querying
            String sql_query = "INSERT INTO meals (user_id, meal)  VALUES (?, ?);";
            PreparedStatement pstmt = con.prepareStatement(sql_query);

            //setting parameters for data
            pstmt.setInt(1, user_id);
            pstmt.setString(2, foodName.getText());

            //executing query
            pstmt.executeUpdate();

            //Success
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Successfully Added");
            a.setContentText("Meal Added");
            a.showAndWait();

            //closing
            pstmt.close();
            con.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
