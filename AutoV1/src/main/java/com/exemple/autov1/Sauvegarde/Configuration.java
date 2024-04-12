package com.exemple.autov1.Sauvegarde;



import com.exemple.autov1.Cibles.Cible;

import java.io.Serializable;
import java.util.*;

public class Configuration implements Serializable
{
    private static int UID = 1;

    private String name;
    private List<Cible> cibles;
    private int repetition;
    private int id;


    public Configuration()
    {
        this.name = null;
        this.cibles = new ArrayList<>();
        this.repetition = 0;
        this.id = UID++;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void addCible(Cible cible)
    {
        this.cibles.add(cible);
    }

    public void setRepetition(int repetition)
    {
        this.repetition = repetition;
    }

    public List<Cible> getCibles()
    {
        return this.cibles;
    }

    public int getRepetition()
    {
        return this.repetition;
    }

    public String getName()
    {
        return this.name;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }


    public List<List<Cible>> sousEtape()
    {
        List<List<Cible>> sousEtapes = new ArrayList<>();
        Set<Integer> tourDifferent = differentTour();
        List<Integer> listTrier = new ArrayList<>(tourDifferent);
        Collections.sort(listTrier);

        for (Integer tourDebut : listTrier)
        {
            List<Cible> sousEtape = new ArrayList<>();

            for (Cible cible : this.cibles)
            {
                if (tourDebut.equals(cible.getTourDebut()))
                    sousEtape.add(cible.copy());
            }

            sousEtapes.add(sousEtape);
        }

        return new ArrayList<>(sousEtapes);
    }


    private Set<Integer> differentTour()
    {
        Set<Integer> list = new HashSet<>();

        for (Cible cible : this.cibles)
        {
            list.add(cible.getTourDebut());
        }

        return list;
    }


    @Override
    public String toString()
    {
        return this.name+" -> "+this.cibles.size()+"cible(s) -> répétition :"+ this.repetition;
    }
}
