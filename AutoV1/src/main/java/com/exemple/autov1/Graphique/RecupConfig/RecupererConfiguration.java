package com.exemple.autov1.Graphique.RecupConfig;

import com.exemple.autov1.Clicker;
import com.exemple.autov1.Graphique.ChoixMode;
import com.exemple.autov1.Sauvegarde.Configuration;
import com.exemple.autov1.Sauvegarde.SauvegardeConfig;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


import java.util.List;

public class RecupererConfiguration extends Pane {
    private Label consigne;
    private TableView<Configuration> tableau;
    private List<Configuration> listDeConfiguration;
    private TableCell<Configuration, String> derniereCelluleSelectionnee;
    private Button valider;
    private Button menu;

    public RecupererConfiguration()
    {
        this.consigne = new Label("Choisissez la configuration souhaité");
        this.listDeConfiguration = SauvegardeConfig.recupererTouteLesConfiguration();
        this.derniereCelluleSelectionnee = null;
        this.valider = new Button("Valider");
        this.menu = new Button("Menu");

        initButton();

        initTableau();


        VBox vBox = new VBox(10);
        consigne.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(consigne,tableau,valider,menu);
        getChildren().add(vBox);
    }



    private void initButton()
    {
        this.valider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                Scene scene = getScene();
                Configuration configSelectionnee = tableau.getItems().get(derniereCelluleSelectionnee.getIndex());

                if (configSelectionnee != null)
                {
                    Clicker clicker = new Clicker(configSelectionnee);

                    try
                    {
                        GlobalScreen.registerNativeHook();
                        GlobalScreen.addNativeKeyListener(clicker);
                        scene.setRoot(clicker);
                    }
                    catch (NativeHookException ex)
                    {
                        System.exit(1);
                    }



                }

            }
        });



        this.menu.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                getScene().setRoot(new ChoixMode());
            }
        });
    }
    private void initTableau()
    {
        tableau = new TableView<>();
        TableColumn<Configuration, String> colonne = new TableColumn<>("Configurations");

        tableau.getColumns().add(colonne);
        ObservableList<Configuration> ol = FXCollections.observableList(listDeConfiguration);
        tableau.setItems(ol);

        colonne.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colonne.setPrefWidth(150);

        colonne.setCellFactory(tc ->
        {
            TableCell<Configuration, String> cell = new TableCell<>()
            {
                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if (empty || item == null)
                    {
                        setText(null);
                        setStyle("");
                    }
                    else
                    {
                        setText(item);
                        setStyle("-fx-text-fill: black;");
                    }
                }
            };


            cell.setOnMouseClicked(event ->
            {
                if (!cell.isEmpty())
                {

                    if (derniereCelluleSelectionnee != null)
                        derniereCelluleSelectionnee.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

                    cell.setBackground(new Background(new BackgroundFill(Color.CYAN, null, null)));
                    derniereCelluleSelectionnee = cell;
                }
            });
            return cell;
        });

        this.tableau.setMaxHeight(300);
        this.tableau.setMaxSize(300,250);

        this.getChildren().add(tableau);
    }
}
