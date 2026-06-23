package com.fabianrodas.javadash;

import com.fabianrodas.models.MenuMusicSingleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class MenuController implements Initializable {

    private MenuMusicSingleton menuMusic = MenuMusicSingleton.getInstance();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuMusic.startMusic();
    }

    
}
