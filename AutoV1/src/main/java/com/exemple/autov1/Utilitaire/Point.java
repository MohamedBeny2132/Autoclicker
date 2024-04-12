package com.exemple.autov1.Utilitaire;

import java.io.Serializable;

public class Point implements Serializable
{
    int x,y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getY()
    {
        return this.y;
    }

    public int getX() {
        return this.x;
    }
}
