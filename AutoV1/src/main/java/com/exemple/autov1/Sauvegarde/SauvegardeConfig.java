package com.exemple.autov1.Sauvegarde;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SauvegardeConfig {

    public static void ecrireConfigurationDansFichier(Configuration configuration) {
        Map<String, Configuration> configurationsExistantes = lireConfigurationsDepuisFichier();
        configurationsExistantes.put(configuration.getName(), configuration);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sauvegarde_config"))) {
            oos.writeObject(configurationsExistantes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ecrireConfigurationDansFichier(Map<String, Configuration> configuration) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sauvegarde_config"))) {
            oos.writeObject(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Configuration> lireConfigurationsDepuisFichier() {
        Map<String, Configuration> configurations = new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("sauvegarde_config"))){
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                configurations = (Map<String, Configuration>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Ignorer l'exception si le fichier n'existe pas encore
        }

        return configurations;
    }

    public static Configuration recupConfiguration(String nom)
    {
        return lireConfigurationsDepuisFichier().get(nom);
    }



    public static void supprimerConfiguration(String nomConfig) {
        Map<String, Configuration> configurations = lireConfigurationsDepuisFichier();

        if (configurations.containsKey(nomConfig)) {
            configurations.remove(nomConfig);


            Map<String, Configuration> nouvellesConfigurations = new HashMap<>();
            int nouvelId = 1;

            for (Configuration config : configurations.values()) {
                config.setId(nouvelId++);
                nouvellesConfigurations.put(config.getName(), config);
            }

            ecrireConfigurationDansFichier(nouvellesConfigurations);
        }
    }

    public static List<Configuration> recupererTouteLesConfiguration()
    {
        return new ArrayList<>(lireConfigurationsDepuisFichier().values());
    }
    public static boolean nomExistant(String nom)
    {
        return lireConfigurationsDepuisFichier().containsKey(nom);
    }

    public static boolean configExistant()
    {
        return !lireConfigurationsDepuisFichier().isEmpty();
    }



}
