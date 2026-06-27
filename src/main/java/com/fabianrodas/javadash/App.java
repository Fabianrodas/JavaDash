package com.fabianrodas.javadash;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class App extends Application {

    private static final double BASE_WIDTH = 960;
    private static final double BASE_HEIGHT = 540;

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        scene = new Scene(
                createGameRoot(loadFXML("menu")),
                BASE_WIDTH,
                BASE_HEIGHT
        );

        stage.setTitle("JavaDash");
        stage.setScene(scene);

        stage.setResizable(true);
        stage.setFullScreenExitHint("");

        
        stage.maximizedProperty().addListener((obs, oldValue, maximized) -> {
            if (maximized && !stage.isFullScreen()) {
                Platform.runLater(() -> {
                    if (stage.isMaximized() && !stage.isFullScreen()) {
                        stage.setFullScreen(true);
                    }
                });
            }
        });

        stage.fullScreenProperty().addListener((obs, oldValue, fullScreen) -> {
            if (!fullScreen) {
                Platform.runLater(() -> {
                    if (stage.isMaximized()) {
                        stage.setMaximized(false);
                    }

                    stage.sizeToScene();
                    stage.centerOnScreen();
                });
            }
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen());
                event.consume();
            }
        });

        stage.show();
    }

    public static void setRoot(String fxmlName) throws IOException {
        scene.setRoot(createGameRoot(loadFXML(fxmlName)));
    }

    private static Parent loadFXML(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource(fxmlName + ".fxml")
        );

        return loader.load();
    }

    private static Parent createGameRoot(Parent fxmlContent) {
        Pane viewport = new Pane();
        viewport.setStyle("-fx-background-color: black;");
        viewport.setPrefSize(BASE_WIDTH, BASE_HEIGHT);

        Group canvas = new Group(fxmlContent);

        Scale scale = new Scale(1, 1);
        canvas.getTransforms().add(scale);

        viewport.getChildren().add(canvas);

        Runnable updateScale = () -> {
            double width = viewport.getWidth();
            double height = viewport.getHeight();

            if (width <= 0 || height <= 0) {
                return;
            }

            // No estira ni recorta el contenido.
            double factor = Math.min(
                    width / BASE_WIDTH,
                    height / BASE_HEIGHT
            );

            scale.setX(factor);
            scale.setY(factor);

            canvas.setLayoutX((width - BASE_WIDTH * factor) / 2);
            canvas.setLayoutY((height - BASE_HEIGHT * factor) / 2);
        };

        viewport.widthProperty().addListener(
                (obs, oldValue, newValue) -> updateScale.run()
        );

        viewport.heightProperty().addListener(
                (obs, oldValue, newValue) -> updateScale.run()
        );

        Platform.runLater(updateScale);

        return viewport;
    }

    public static void main(String[] args) {
        launch();
    }
}