package uet.oop.bomberman;

import uet.oop.bomberman.audio.Music;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsSceneController implements Initializable {
    private int count1 = 0;
    private int count2 = 0;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button soundButton;

    @FXML
    private Button musicButton;

    @FXML
    private Button backButton;

    @FXML
    private Text soundText;

    @FXML
    private Text musicText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!BombermanGame.muted.isMutedSound()) {
            soundText.setText("Sound: Enabled");
        } else {
            soundText.setText("Sound: Disabled");
        }
        if (!BombermanGame.muted.isMutedMusic()) {
            musicText.setText("Music: Enabled");
        } else {
            musicText.setText("Music: Disabled");
        }
    }

    //mouse clicked handling
    @FXML
    public void soundOptionClickedHandle() {
        new Music(Music.CLICKED).play();
        if (count1 % 2 == 0) {
            soundText.setText("Sound: Disabled");
            BombermanGame.muted.setMutedSound(true);
            count1++;
        } else {
            soundText.setText("Sound: Enabled");
            BombermanGame.muted.setMutedSound(false);
            count1++;
        }
    }
    @FXML
    public void musicOptionClickedHandle() {
        new Music(Music.CLICKED).play();
        if (count2 % 2 == 0) {
            musicText.setText("Music: Disabled");
            BombermanGame.muted.setMutedMusic(true);
            count2++;
        } else {
            musicText.setText("Music: Enabled");
            BombermanGame.muted.setMutedMusic(false);
            count2++;
        }
    }
    @FXML
    public void backOptionClickedHandle(ActionEvent event) throws IOException {
        new Music(Music.CLICKED).play();
        switchToMenu(event);
    }
    @FXML
    public void switchToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}