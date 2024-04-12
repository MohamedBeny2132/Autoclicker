package com.exemple.autov1.Graphique;

import com.exemple.autov1.Graphique.CreationConfig.CreeConfiguration;
import com.exemple.autov1.Graphique.RecupConfig.RecupererConfiguration;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ChoixMode extends Pane
{

    private final int HAUTEUR = 370;
    private final int LARGEUR = 200;
    private Button creeNouvelleConfiguration;
    private Button recupererAncienneConfiguration;
    private VBox vbox;

    public ChoixMode()
    {
        setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
        configureButton();
    }

    private void configureButton()
    {
        this.vbox = new VBox(2);
        initButton();

        this.vbox.getChildren().addAll(creeNouvelleConfiguration,recupererAncienneConfiguration);
        this.getChildren().add(vbox);
    }


    private void initButton()
    {
        this.creeNouvelleConfiguration = new Button("\t Créer une \n nouvelle configuration.");
        this.recupererAncienneConfiguration = new Button("\t Récuperez une \n ancienne configuration");


        this.creeNouvelleConfiguration.setPrefSize(LARGEUR-16,HAUTEUR/2-4);
        this.recupererAncienneConfiguration.setPrefSize(LARGEUR-16,HAUTEUR/2-4);

        Background background = new Background(new BackgroundFill(Color.rgb(33,33,33),null,null));
        this.creeNouvelleConfiguration.setBackground(background);
        this.recupererAncienneConfiguration.setBackground(background);

        this.creeNouvelleConfiguration.setTextFill(Color.WHITE);
        this.recupererAncienneConfiguration.setTextFill(Color.WHITE);




        this.creeNouvelleConfiguration.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                CreeConfiguration cf = new CreeConfiguration();

                try
                {
                    if (!GlobalScreen.isNativeHookRegistered())
                        GlobalScreen.registerNativeHook();

                    GlobalScreen.addNativeMouseListener(cf);
                    GlobalScreen.addNativeMouseMotionListener(cf);
                    getScene().setRoot(cf);
                }
                catch (NativeHookException e) {
                    e.printStackTrace();
                }


            }
        });

        this.recupererAncienneConfiguration.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                RecupererConfiguration recupConfig = new RecupererConfiguration();
                getScene().setRoot(recupConfig);
            }
        });
    }


}
