package uet.oop.bomberman;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import uet.oop.bomberman.audio.Music;

public class MenuController implements Initializable {
    private int count = 0;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView myImageView;
    Image firstMenuScene = new Image(getClass().getResourceAsStream("/menuScene1.png"));
    Image secondMenuScene = new Image(getClass().getResourceAsStream("/menusc2.jpg"));

    @FXML
    private Rectangle settingOption;

    @FXML
    private Rectangle scoreboardOption;

    @FXML
    private Rectangle exitOption;

    @FXML
    private Rectangle playOption;

    @FXML
    private Text start;

    @FXML
    private Button startButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button scButton;

    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstMenuSceneFadeOut();
    }

    public boolean checkOptionChoosed() {
        return false;
    }

    public void firstMenuSceneFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(2.5));
        fadeTransition.setNode(myImageView);
        fadeTransition.setCycleCount(1);
        fadeTransition.setInterpolator(Interpolator.LINEAR);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> {
            if(count % 2 == 0) {
                loadMenuScene(secondMenuScene);
                count++;
            } else {
                loadMenuScene(firstMenuScene);
                count++;
            }
            secondMenuSceneFadeIn();
        });
        fadeTransition.play();
    }


    public void secondMenuSceneFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(2.5));
        fadeTransition.setNode(myImageView);
        fadeTransition.setCycleCount(1);
        fadeTransition.setInterpolator(Interpolator.LINEAR);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(event -> {
            firstMenuSceneFadeOut();
        });
        fadeTransition.play();
    }

    public void loadMenuScene(Image img) {
        myImageView.setImage(img);
    }

    //mouse clicked handling
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void startOptionClickedHandle(ActionEvent event) throws IOException {
        //change scene
        new Music(Music.CLICKED).play();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(BombermanGame.lc.getLvScene());
        stage.show();
    }
    @FXML
    public void settingOptionClickedHandle(ActionEvent event) throws IOException {
        new Music(Music.CLICKED).play();
        root = FXMLLoader.load(getClass().getResource("/fxml/Settings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void scoreboardOptionClickedHandle() {
        new Music(Music.CLICKED).play();
        System.exit(0);
    }

    @FXML
    public void exitOptionClickedHandle() {
        new Music(Music.CLICKED).play();
        System.exit(0);
    }
}
