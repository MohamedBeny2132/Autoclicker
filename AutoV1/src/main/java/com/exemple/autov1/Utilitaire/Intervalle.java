package com.exemple.autov1.Utilitaire;

import java.io.Serializable;

public class Intervalle implements Serializable
{
    private int v1,v2;

    public Intervalle(int v1, int v2)
    {
        this.v1 = v1;
        this.v2 = v2;
    }


    public boolean estDansIntervalle(int vlr)
    {
        return vlr >= this.v1 && vlr <= this.v2;
    }
}
