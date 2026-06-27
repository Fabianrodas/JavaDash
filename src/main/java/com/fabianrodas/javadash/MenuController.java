package com.fabianrodas.javadash;

import com.fabianrodas.models.MenuMusicSingleton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MenuController implements Initializable {

    private MenuMusicSingleton menuMusic = MenuMusicSingleton.getInstance();
    
    @FXML
    private HBox bgMenuHbox, groundMenuHbox; // TODO: Parallax
    
    @FXML
    private ImageView playBtn, garageBtn, creatorBtn, 
            optionsBtn, statsBtn, githubBtn, 
            discordIcon, instaIcon, githubIcon, itsferh;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuMusic.startMusic();
    }
    
    
    // goToss
    
    @FXML
    private void goToMainLevels() throws IOException {
        App.setRoot("mainLevelSelection");
    }
    
    @FXML
    private void goToGarage() throws IOException {
        App.setRoot("garage");
    }
    
    @FXML
    private void goToCreator() throws IOException {
        App.setRoot("creator");
    }
    
}
