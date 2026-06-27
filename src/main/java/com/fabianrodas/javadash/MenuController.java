package com.fabianrodas.javadash;

import com.fabianrodas.models.MenuMusicSingleton;
import java.io.IOException;
import java.net.URL;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class MenuController {

    private final MenuMusicSingleton menuMusic = MenuMusicSingleton.getInstance();

    @FXML
    private Pane menuPane;

    @FXML
    private Pane backgroundLayer;

    @FXML
    private Pane groundLayer;

    // ---------- BACKGROUND ----------

    private static final String BG_PATH =
            "/com/fabianrodas/bg/game_bg_01_001-uhd.png";

    private static final double BG_WIDTH = 976;
    private static final double BG_HEIGHT = 960;
    private static final double BG_START_X = -420;
    private static final double BG_START_Y = -420;
    private static final double BG_SPEED = 40;

    private final HBox bgHbox = new HBox();
    private double bgOffsetX = 0;

    // ---------- GROUND ----------

    private static final String GROUND_PATH =
            "/com/fabianrodas/ground/groundSquare_01_001-uhd.png";

    private static final double GROUND_WIDTH = 150;
    private static final double GROUND_HEIGHT = 150;
    private static final double GROUND_SPEED = 120;

    private final HBox groundHbox = new HBox();
    private double groundOffsetX = 0;

    // ---------- ANIMATION ----------

    private AnimationTimer parallaxTimer;

    @FXML
    private void initialize() {
        menuMusic.startMusic();

        clipMenu();
        createBackground();
        createGround();
        startParallax();
    }

    private void clipMenu() {
        Rectangle clip = new Rectangle();

        clip.widthProperty().bind(menuPane.widthProperty());
        clip.heightProperty().bind(menuPane.heightProperty());

        menuPane.setClip(clip);
    }

    // ---------- CREATE BACKGROUND ----------

    private void createBackground() {
        bgHbox.setSpacing(0);
        bgHbox.setManaged(false);
        bgHbox.setMouseTransparent(true);

        bgHbox.setLayoutX(BG_START_X);
        bgHbox.setLayoutY(BG_START_Y);

        Image bgImage = loadImage(BG_PATH);

        for (int i = 0; i < 2; i++) {
            bgHbox.getChildren().add(createBackgroundImage(bgImage));
        }

        backgroundLayer.getChildren().setAll(bgHbox);
    }

    private ImageView createBackgroundImage(Image image) {
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(BG_WIDTH);
        imageView.setFitHeight(BG_HEIGHT);
        imageView.setPreserveRatio(true);
        imageView.setMouseTransparent(true);

        return imageView;
    }

    // ---------- CREATE GROUND ----------

    private void createGround() {
        groundHbox.setSpacing(0);
        groundHbox.setManaged(false);
        groundHbox.setMouseTransparent(true);

        Image groundImage = loadImage(GROUND_PATH);

        for (int i = 0; i < 8; i++) {
            groundHbox.getChildren().add(createGroundImage(groundImage));
        }

        groundLayer.getChildren().setAll(groundHbox);
    }

    private ImageView createGroundImage(Image image) {
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(GROUND_WIDTH);
        imageView.setFitHeight(GROUND_HEIGHT);
        imageView.setPreserveRatio(false);
        imageView.setMouseTransparent(true);

        return imageView;
    }

    // ---------- LOAD IMAGE ----------

    private Image loadImage(String path) {
        URL resource = getClass().getResource(path);

        if (resource == null) {
            throw new IllegalStateException(
                    "No se encontró la imagen: " + path
            );
        }

        return new Image(resource.toExternalForm());
    }

    // ---------- PARALLAX ----------

    private void startParallax() {
        parallaxTimer = new AnimationTimer() {
            private long lastFrame = -1;

            @Override
            public void handle(long now) {
                if (lastFrame == -1) {
                    lastFrame = now;
                    return;
                }

                double delta = (now - lastFrame) / 1_000_000_000.0;
                lastFrame = now;

                delta = Math.min(delta, 0.05);

                moveBackground(delta);
                moveGround(delta);
            }
        };

        parallaxTimer.start();
    }

    private void moveBackground(double delta) {
        bgOffsetX -= BG_SPEED * delta;

        double imageWidth = bgHbox.getChildren()
                .get(0)
                .getLayoutBounds()
                .getWidth();

        while (BG_START_X + bgOffsetX + imageWidth <= 0) {
            Node firstImage = bgHbox.getChildren().remove(0);
            bgHbox.getChildren().add(firstImage);

            bgOffsetX += imageWidth;
        }

        bgHbox.setTranslateX(bgOffsetX);
    }

    private void moveGround(double delta) {
        groundOffsetX -= GROUND_SPEED * delta;

        while (groundOffsetX + GROUND_WIDTH <= 0) {
            Node firstImage = groundHbox.getChildren().remove(0);
            groundHbox.getChildren().add(firstImage);

            groundOffsetX += GROUND_WIDTH;
        }

        groundHbox.setTranslateX(groundOffsetX);
    }

    // ---------- CHANGE SCENE ----------

    private void changeScreen(String fxmlName) throws IOException {
        if (parallaxTimer != null) {
            parallaxTimer.stop();
        }

        App.setRoot(fxmlName);
    }

    @FXML
    private void goToMainLevels() throws IOException {
        changeScreen("mainLevelSelection");
    }

    @FXML
    private void goToGarage() throws IOException {
        changeScreen("garage");
    }

    @FXML
    private void goToCreator() throws IOException {
        changeScreen("creator");
    }
}