package com.exemple.autov1.Graphique.CreationConfig.Popup;

import com.exemple.autov1.Cibles.Cible;
import com.exemple.autov1.Graphique.CreationConfig.CreeConfiguration;
import com.exemple.autov1.Utilitaire.Point;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PopUpCustomCible extends Stage {
    private Pane panel;
    private Label consigne;
    private Label delaie;
    private Spinner<Integer> spinnerDelai;
    private Label nombreClick;
    private Spinner<Integer> spinnerNombreClick;
    private Label tourLabel;
    private Spinner<Integer> spinnerTour;
    private Button valider;

    private Point coordonne;

    public PopUpCustomCible(CreeConfiguration config) {
        setTitle("Customisez votre cible");
        initModality(Modality.APPLICATION_MODAL);
        initOwner(config.getScene().getWindow());

        this.panel = new Pane();
        this.consigne = new Label("Mettez les informations nécessaires");
        this.nombreClick = new Label("Nombre de clics avant désactivation");
        this.delaie = new Label("Délai avant de cliquer");
        this.tourLabel = new Label("Debut à quelle tour");
        this.valider = new Button("Valider");
        initSpinner();
        this.coordonne = config.getPosition();


        this.valider.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                try
                {
                    int nombreDeClick = spinnerNombreClick.getValue();
                    int delai = spinnerDelai.getValue();
                    int tour = spinnerTour.getValue();

                    config.getConfig().addCible(new Cible(coordonne, delai, nombreDeClick, tour));
                    config.setEtatClique(false);

                    close();
                }
                catch (NumberFormatException e)
                {
                    spinnerDelai.getEditor().setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
                    spinnerNombreClick.getEditor().setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
                }
            }
        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.TOP_CENTER);

        HBox hBox1 = new HBox(10);
        hBox1.getChildren().addAll(nombreClick, spinnerNombreClick);

        HBox hBox2 = new HBox(10);
        hBox2.getChildren().addAll(delaie, spinnerDelai);

        HBox hBox3 = new HBox(10);
        hBox3.getChildren().addAll(tourLabel, spinnerTour);

        vBox.getChildren().addAll(consigne, hBox1, hBox2, hBox3, valider);

        panel.getChildren().add(vBox);

        this.setScene(new Scene(panel));

        show();
    }



    private void initSpinner()
    {
        this.spinnerNombreClick = new Spinner<>();
        SpinnerValueFactory.IntegerSpinnerValueFactory nombreClickValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 10000, 1, 1);
        nombreClickValueFactory.setConverter(new StringConverter<Integer>()
        {
            @Override
            public String toString(Integer value) {
                return value == -1 ? "Infini" : value.toString();
            }

            @Override
            public Integer fromString(String string) {
                return null;
            }
        });
        this.spinnerNombreClick.setValueFactory(nombreClickValueFactory);


        this.spinnerDelai = new Spinner<>();
        SpinnerValueFactory.IntegerSpinnerValueFactory delaiValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1000, 200);
        this.spinnerDelai.setValueFactory(delaiValueFactory);


        this.spinnerTour = new Spinner<>();
        SpinnerValueFactory.IntegerSpinnerValueFactory tourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0, 1);
        this.spinnerTour.setValueFactory(tourValueFactory);

    }









}
