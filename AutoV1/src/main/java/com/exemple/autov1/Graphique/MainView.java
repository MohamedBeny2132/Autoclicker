package com.exemple.autov1.Graphique;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("AutoClikeur V1");
        stage.setHeight(400);
        stage.setWidth(200);
        stage.setAlwaysOnTop(true);
        stage.setScene(new Scene(new ChoixMode()));
        stage.setResizable(false);

        stage.show();
    }
}


