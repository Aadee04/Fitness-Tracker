package com.example.demofitness;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Node;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;


public class mainController implements Initializable {
    List <String> f_name = new ArrayList<String>();
    List <String> protein = new ArrayList<String>();
    List <String> total_fat = new ArrayList<String>();
    List <String> cholestrol = new ArrayList<String>();
    List <String> carbohydrate = new ArrayList<String>();
    List <String> fiber = new ArrayList<String>();
    List <String> Sugars = new ArrayList<String>();
    List <String> Calories = new ArrayList<String>();
    public String username = "";
    public int user_id = 0;

    @FXML
    private TextField meal_search;

    @FXML
    private AnchorPane Exercises;

    @FXML
    private AnchorPane Home;

    @FXML
    private AnchorPane Meals;

    @FXML
    private Button addMealsButton;

    @FXML
    private Text caloriesNutrition;

    @FXML
    private Text carbsNutrition;

    @FXML
    private AnchorPane dropdownPane;

    @FXML
    private ScrollPane exercisesScrollHome;

    @FXML
    private Text fatsNutrition;

    @FXML
    private VBox foodLog;

    @FXML
    private Button logoutButton;

    @FXML
    private Button mainExercisesButton;

    @FXML
    private VBox mainExercisesList;

    @FXML
    private Button mainHomeButton;

    @FXML
    private Button mainMealsButton;

    @FXML
    private VBox mainMealsList;

    @FXML
    private AnchorPane mainMenu;

    @FXML
    private StackPane mainView;

    @FXML
    private HBox mainWindow;

    @FXML
    private ScrollPane mealsScroll;

    @FXML
    private ScrollPane mealsScrollHome;

    @FXML
    private VBox nutrition;

    @FXML
    private ImageView profilePictureMain;

    @FXML
    private Text proteinNutrition;

    @FXML
    private Text titleHome;

    @FXML
    private Text titleMeals;

    @FXML
    private Text titleMeals1;

    @FXML
    private ChoiceBox<String > muscle_group;//drop down list
    String muscles[]={"Forearms","Quadriceps","Abdominals","Lats","Middle Back","Lower Back","Shoulders","Biceps","Glutes","Triceps","Hamstrings","Neck","Chest","Traps","Calves","Abductors","Adductors"};


    @FXML
    private VBox exercise_list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        muscle_group.getItems().addAll(muscles);
        muscle_group.setOnAction(this::muscle_select);
    }

    @FXML
    public void showHome() {
        mainView.getChildren().clear();
        mainView.getChildren().add(Home);

        refreshHomeLists();
    }

    public void refreshHomeLists(){
        String db_user = "root";
        String db_pwd = "";
        String url = "jdbc:mysql://localhost:3306/fitness";
        try {
            //connecting
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, db_user, db_pwd);

            String sql_query = "SELECT * FROM workouts WHERE user_id=?;";

            PreparedStatement pstmt = con.prepareStatement(sql_query);

            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            List <String> e_name = new ArrayList<String>();

            while(rs.next()){
                e_name.add(rs.getString(3));
            }
            int count = (e_name.size());
            Node [] home_items = new Node[count];

            mainExercisesList.getChildren().clear();
            for (int i = 0; i < count; i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("homeListItem.fxml"));
                home_items[i] = loader.load();
                homeListItemController controller = loader.getController();
                controller.setItem(e_name.get(i), user_id, true);
                mainExercisesList.getChildren().add(home_items[i]);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            //connecting
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, db_user, db_pwd);

            String sql_query = "SELECT * FROM meals WHERE user_id=?;";

            PreparedStatement pstmt = con.prepareStatement(sql_query);

            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            List <String> e_name = new ArrayList<String>();

            while(rs.next()){
                e_name.add(rs.getString(3));
            }
            int count = (e_name.size());
            Node [] home_items = new Node[count];

            mainMealsList.getChildren().clear();
            for (int i = 0; i < count; i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("homeListItem.fxml"));
                home_items[i] = loader.load();
                homeListItemController controller = loader.getController();
                controller.setItem(e_name.get(i), user_id, false);
                mainMealsList.getChildren().add(home_items[i]);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dataBaseExercise(){
        String db_user = "root";
        String db_pwd = "";
        String url = "jdbc:mysql://localhost:3306/fitness";
        try {
            //connecting
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, db_user, db_pwd);

            String sql_query = "SELECT * FROM exercises;";
            PreparedStatement pstmt = con.prepareStatement(sql_query);
            ResultSet rs = pstmt.executeQuery();
            List <String> e_name = new ArrayList<String>();
            List <String> m_gp = new ArrayList<String>();
            List <String> equipment = new ArrayList<String>();

            while(rs.next()){
                e_name.add(rs.getString(2));
                m_gp.add(rs.getString(3));
                equipment.add(rs.getString(4));
            }
            int count = (e_name.size());
            Node [] exercise_items = new Node[count];

            exercise_list.getChildren().clear();
            for (int i = 0; i < count; i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("exerciseListItem.fxml"));
                exercise_items[i] = loader.load();
                exerciseListItemController controller = loader.getController();
                controller.setItem(e_name.get(i), m_gp.get(i), equipment.get(i), user_id);
                exercise_list.getChildren().add(exercise_items[i]);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void dataBaseExercise(String a){
        String db_user = "root";
        String db_pwd = "";
        String url = "jdbc:mysql://localhost:3306/fitness";
        try {
            //connecting
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, db_user, db_pwd);

            String sql_query = "SELECT * FROM exercises WHERE muscle_gp = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query);
            pstmt.setString(1, a);
            ResultSet rs = pstmt.executeQuery();
            List <String> e_name = new ArrayList<String>();
            List <String> m_gp = new ArrayList<String>();
            List <String> equipment = new ArrayList<String>();

            while(rs.next()){
                e_name.add(rs.getString(2));
                m_gp.add(rs.getString(3));
                equipment.add(rs.getString(4));
            }
            int count = (e_name.size());
            Node [] exercise_items = new Node[count];

            exercise_list.getChildren().clear();
            for (int i = 0; i < count; i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("exerciseListItem.fxml"));
                exercise_items[i] = loader.load();
                exerciseListItemController controller = loader.getController();
                controller.setItem(e_name.get(i), m_gp.get(i), equipment.get(i), user_id);
                exercise_list.getChildren().add(exercise_items[i]);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void showExercises() {
        mainView.getChildren().clear();
        mainView.getChildren().add(Exercises);

        dataBaseExercise();
    }

    public void muscle_select(ActionEvent a){
        String m_group = muscle_group.getValue();
        dataBaseExercise(m_group);
    }

    public void dataBaseFood(){
        String db_user = "root";
        String db_pwd = "";
        String url = "jdbc:mysql://localhost:3306/fitness";
        try {
            //connecting
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, db_user, db_pwd);

            String sql_query = "SELECT * FROM food LIMIT 100;";
            PreparedStatement pstmt = con.prepareStatement(sql_query);
            ResultSet rs = pstmt.executeQuery();

            foodLog.getChildren().clear();
            while(rs.next()){
                f_name.add(rs.getString(2));
                Calories.add(String.valueOf(rs.getInt(4)));
                total_fat.add(rs.getString(5));
                protein.add(rs.getString(7));
                carbohydrate.add(rs.getString(8));
            }
            int count = (f_name.size());
            Node [] meal_items = new Node[count];

            for (int i = 0; i < count; i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mealListItem.fxml"));
                meal_items[i] = loader.load();
                mealListItemController controller = loader.getController();
                controller.setItem(f_name.get(i), Calories.get(i), protein.get(i), carbohydrate.get(i), total_fat.get(i), user_id);
                foodLog.getChildren().add(meal_items[i]);
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void dataBaseFood(String meal_name){
        String db_user = "root";
        String db_pwd = "";
        String url = "jdbc:mysql://localhost:3306/fitness";
        try {
            //connecting
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, db_user, db_pwd);

            String sql_query = "SELECT * FROM food WHERE name LIKE ?;";
            PreparedStatement pstmt = con.prepareStatement(sql_query);
            pstmt.setString(1, "%" + meal_name + "%");
            ResultSet rs = pstmt.executeQuery();

            if(!rs.isBeforeFirst()) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("No Meal Found");
                a.setContentText("Sorry!");
                a.showAndWait();
            }
            else{
                while(rs.next()){
                    f_name.add(rs.getString(2));
                    Calories.add(String.valueOf(rs.getInt(4)));
                    total_fat.add(rs.getString(5));
                    protein.add(rs.getString(7));
                    carbohydrate.add(rs.getString(8));
                }
                int count = (f_name.size());
                Node [] meal_items = new Node[count];

                for (int i = 0; i < count; i++){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("mealListItem.fxml"));
                    meal_items[i] = loader.load();
                    mealListItemController controller = loader.getController();
                    controller.setItem(f_name.get(i), Calories.get(i), protein.get(i), carbohydrate.get(i), total_fat.get(i), user_id);
                    foodLog.getChildren().add(meal_items[i]);
                }


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void showMeals() {
        mainView.getChildren().clear();
        mainView.getChildren().add(Meals);
        dataBaseFood();
    }

    @FXML
    public void searchMeals(){
        String meal_name = (meal_search.getText());
        mainView.getChildren().clear();
        mainView.getChildren().add(Meals);
        dataBaseFood(meal_name);
    }
    public void exit(){
        System.exit(0);

    }


    public void display_text(String s, int u){
        username = (s);
        user_id = u;
        titleHome.setText("WELCOME, " + username);
    }
}



