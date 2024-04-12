package com.exemple.autov1.Cibles;


import com.exemple.autov1.Utilitaire.Point;

import java.io.Serializable;

public  class Cible implements Serializable {
    private Point coordonnee;
    private float delaiEntreClick;

    private int tourDebut;

    private int cliqueAfaire;

    private boolean estInfini;
    public Cible(Point coordonnee,float delaiEntreClick,int cliqueAfaire,int tourDebut)
    {
        this.coordonnee = coordonnee;
        this.delaiEntreClick = delaiEntreClick;
        this.tourDebut = tourDebut;
        this.cliqueAfaire = cliqueAfaire;

        this.estInfini = cliqueAfaire == -1;
    }


    public Point getCoordonnee()
    {
        return this.coordonnee;
    }


    public boolean peuCliquer(int tourActuelle)
    {
        return resteDesCliques() && estActive(tourActuelle) ;
    }

    public boolean resteDesCliques()
    {
        return this.estInfini || this.cliqueAfaire > 0;
    }

    private boolean estActive(int tourActuelle)
    {
        return tourActuelle >= this.tourDebut ;
    }

    public void aCliquer()
    {
        if (!this.estInfini)
            this.cliqueAfaire--;
    }

    public void setNewCoordonnee(Point co)
    {
        this.coordonnee = co;
    }

    public int getTourDebut()
    {
        return this.tourDebut;
    }


    public int getDelai()
    {
        return (int) this.delaiEntreClick;
    }

    public void setNewDelai(int d)
    {
        this.delaiEntreClick = d;
    }

    public Cible copy() {
        return new Cible(coordonnee, delaiEntreClick, cliqueAfaire, tourDebut);
    }

}
