package com.example.demofitness;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class exerciseListItemController {

    @FXML
    private Button addButton;

    @FXML
    private Text exerciseTitle;

    @FXML
    private Text exerciseTitle1;

    @FXML
    private Text exerciseTitle11;

    @FXML
    private AnchorPane exerciseListItemPane;

    private int user_id;

    public void setItem(String a, String b, String c, int u){
        exerciseTitle.setText(a);
        exerciseTitle1.setText(b);
        exerciseTitle11.setText(c);
        this.user_id = u;
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
            String sql_query = "INSERT IGNORE INTO workouts (user_id, workout)  VALUES (?, ?);";
            PreparedStatement pstmt = con.prepareStatement(sql_query);

            //setting parameters for data
            pstmt.setInt(1, user_id);
            pstmt.setString(2, exerciseTitle.getText());

            //executing query
            pstmt.executeUpdate();

            //Success
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Successfully Added");
            a.setContentText("Workout Added");
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
