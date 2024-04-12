package com.exemple.autov1;

import com.exemple.autov1.Cibles.Cible;
import com.exemple.autov1.Graphique.RecupConfig.RecupererConfiguration;
import com.exemple.autov1.Sauvegarde.Configuration;
import com.exemple.autov1.Utilitaire.Point;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import javafx.scene.layout.VBox;

public class Clicker extends Pane implements NativeKeyListener{
    private Configuration configuration;
    private boolean estEnCours;
    private Button start;
    private Button changerConfig;
    private Robot robot;
    private VBox vBox;

    public Clicker(Configuration configuration)
    {
        this.configuration = configuration;
        this.vBox = new VBox(5);
        this.estEnCours = false;
        initButton();

        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        getChildren().add(vBox);
    }

    private void initButton()
    {
        this.start = new Button("Start");
        this.changerConfig = new Button("Changer de configuration");

        this.start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                autoclick();
            }
        });

        this.changerConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getScene().setRoot(new RecupererConfiguration());
            }
        });

        vBox.getChildren().addAll(start,changerConfig);
    }


    private void autoclick()
    {
        this.estEnCours = true;
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                int repetition = configuration.getRepetition();
                boolean estInfini = repetition == -1;

                while (estEnCours && (estInfini || repetition-- > 0))
                {
                    List<List<Cible>> sousEtapes = new ArrayList<>(configuration.sousEtape());
                    for (int i = 0; i < sousEtapes.size() && estEnCours; i++)
                    {
                        List<Cible> sousEtape = sousEtapes.get(i);
                        while (resteDesCibles(sousEtape) && estEnCours)
                        {
                            for (int j = 0; j < sousEtape.size() && estEnCours; j++)
                            {
                                Cible cible = sousEtape.get(j);

                                if (faireUnClick(cible))
                                    cible.aCliquer();

                            }
                        }
                    }
                }
            }
        });
        t.start();
    }

    private boolean resteDesCibles(List<Cible> cibles)
    {
        boolean resteDesCliques = false;

        for (int i = 0; i < cibles.size() && !resteDesCliques; i++)
        {
            Cible cible = cibles.get(i);

            if (cible.resteDesCliques())
                resteDesCliques = true;
        }

        return resteDesCliques;
    }

    private boolean faireUnClick(Cible cible)
    {
        boolean cliqueFait = false;
        Point p = cible.getCoordonnee();
        this.robot.delay(cible.getDelai());

        if (estEnCours && cible.resteDesCliques())
        {
            this.robot.mouseMove(p.getX(), p.getY());
            this.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            this.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            cliqueFait = true;
        }

        return cliqueFait;
    }


    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        stopAutoclick();
        System.out.println("df");
    }

    private void stopAutoclick()
    {
        this.estEnCours = false;
    }
}
