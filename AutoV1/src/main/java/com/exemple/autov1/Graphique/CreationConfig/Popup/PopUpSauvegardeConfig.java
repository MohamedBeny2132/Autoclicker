package com.exemple.autov1.Graphique.CreationConfig.Popup;

import com.exemple.autov1.Graphique.CreationConfig.CreeConfiguration;
import com.exemple.autov1.Sauvegarde.Configuration;
import com.exemple.autov1.Sauvegarde.SauvegardeConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PopUpSauvegardeConfig extends Stage
{
    private Pane panel;
    private Label consigne;

    private TextField infoNom;
    private Spinner<Integer> spinner;
    private Button valider;
    private Configuration configuration;
    public PopUpSauvegardeConfig(CreeConfiguration config)
    {
        this.panel = new Pane();
        this.consigne = new Label("Entrez le nom de la configuration et le nombre de répetition");
        this.infoNom = new TextField();
        this.valider = new Button("Valider");
        this.configuration = config.getConfig();


        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 100000, 0, 1);

        StringConverter<Integer> sc = new StringConverter<Integer>()
        {
            @Override
            public String toString(Integer value) {
                return value == -1 ? "Infini" : value.toString();
            }

            @Override
            public Integer fromString(String string) {
                return null;
            }
        };


        valueFactory.setConverter(sc);

        spinner = new Spinner<>();
        spinner.setValueFactory(valueFactory);


        this.valider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                String nom = infoNom.getText();

                if (nom.length() > 3)
                {
                    configuration.setName(nom);
                    config.setEtatClique(false);
                    configuration.setRepetition(spinner.getValue());
                    SauvegardeConfig.ecrireConfigurationDansFichier(configuration);
                    close();
                }
                else
                {
                    infoNom.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
                    consigne.setText("Entrez le nom de la configuration de longueur 3 minimum");
                }
            }
        });




        VBox vBox = new VBox(10,consigne,infoNom,spinner,valider);
        this.panel.getChildren().addAll(vBox);



        setTitle("Enregistrer Config");
        setWidth(250);
        setHeight(250);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(config.getScene().getWindow());
        this.setScene(new Scene(panel));
        show();
    }

}
