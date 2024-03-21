package com.example.demofitness;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class homeListItemController {

    @FXML
    private Button addButton;

    @FXML
    private Text Title;

    private int user_id;
    private boolean e;

    public void setItem( String name, int user_id, boolean e){
        this.user_id = user_id;
        this.e = e;
        Title.setText(name);
    }

    public void removeItem(){
        String table_name;
        String item;
        if(this.e){
            table_name = "workouts";
            item = "workout";
        }else{
            table_name = "meals";
            item = "meal";
        }

        String db_user = "root";
        String db_pwd = "";
        String url = "jdbc:mysql://localhost:3306/fitness";
        try {
            //connecting
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, db_user, db_pwd);

            //querying
            String sql_query = "DELETE FROM " + table_name + " WHERE user_id = ? AND " + item + " = ?;";

            PreparedStatement pstmt = con.prepareStatement(sql_query);

            //setting parameters for data
            pstmt.setInt(1, user_id);
            pstmt.setString(2, Title.getText());

            System.out.println(pstmt);

            //executing query
            pstmt.executeUpdate();

            //Success
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Successfully Removed");
            a.setContentText(Title.getText() + " removed");
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
