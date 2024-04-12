package com.exemple.autov1.Graphique.CreationConfig;

import com.exemple.autov1.Graphique.ChoixMode;
import com.exemple.autov1.Graphique.CreationConfig.Popup.PopUpCustomCible;
import com.exemple.autov1.Graphique.CreationConfig.Popup.PopUpSauvegardeConfig;
import com.exemple.autov1.Sauvegarde.Configuration;
import com.exemple.autov1.Utilitaire.Point;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class CreeConfiguration extends Pane implements NativeMouseMotionListener, NativeMouseListener
{
    private Label positionCurseur;
    private boolean cliqueFait;
    private Button validerPosition;
    private Button choisirNouvellePosition;
    private Button fini;
    private Button menu;

    private Configuration config;

    private int x,y;


    public CreeConfiguration()
    {
        Background background = new Background(new BackgroundFill(Color.rgb(33,33,33),null,null));
        this.setBackground(background);

        this.positionCurseur = new Label(" 0 : 0");
        this.positionCurseur.setTextFill(Color.WHITE);
        this.positionCurseur.setFont(new Font(25));
        this.positionCurseur.setAlignment(Pos.TOP_RIGHT);

        this.cliqueFait = false;
        this.config = new Configuration();
        initButton();

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(positionCurseur,validerPosition,choisirNouvellePosition,fini,menu);
        getChildren().add(vBox);






    }

    private void changeCoordonneDuLabel(int x, int y)
    {
        this.x = x;
        this.y = y;
        Platform.runLater(() -> positionCurseur.setText(x + " : " +y));
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e)
    {
        if (!cliqueFait)
        {
            changeCoordonneDuLabel(e.getX(),e.getY());
        }
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e)
    {
        this.cliqueFait = true;
    }



    private void initButton()
    {
        this.validerPosition = new Button("Valider Position");
        this.fini = new Button("Enregistrer la Configuration");
        this.choisirNouvellePosition = new Button("Changez de position");
        this.menu = new Button("Menu");








        this.validerPosition.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                apellePopup();
            }
        });

        this.choisirNouvellePosition.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cliqueFait = false;
            }
        });

        this.fini.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                if (!config.getCibles().isEmpty())
                {
                    apellePopupEnregistrement();
                }
            }
        });

        this.menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                getScene().setRoot(new ChoixMode());
                stopNativeHook();
            }
        });


    }

    private void apellePopupEnregistrement()
    {
        PopUpSauvegardeConfig po = new PopUpSauvegardeConfig(this);
    }
    private void apellePopup()
    {
        PopUpCustomCible pop = new PopUpCustomCible(this);
    }

    public void stopNativeHook()
    {
        GlobalScreen.removeNativeMouseListener(this);
        GlobalScreen.removeNativeMouseMotionListener(this);
    }

    public Point getPosition()
    {
        return new Point(x,y);
    }

    public Configuration getConfig() {
        return config;
    }

    public void setEtatClique(boolean b)
    {
        cliqueFait = b;
    }
}
