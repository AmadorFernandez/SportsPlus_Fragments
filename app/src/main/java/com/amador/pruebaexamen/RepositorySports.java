package com.amador.pruebaexamen;

import java.util.ArrayList;

/**
 * Created by amador on 11/12/16.
 */

public class RepositorySports extends ArrayList<Sport> {

    private static RepositorySports instance;

    private RepositorySports(){

        add(new Sport("Baloncesto", R.drawable.icon_american_football, false));
        add(new Sport("Beisbol", R.drawable.icon_american_football, false));
        add(new Sport("Dardos", R.drawable.icon_american_football, false));
        add(new Sport("Petanca", R.drawable.icon_american_football, false));
        add(new Sport("Ajedrez", R.drawable.icon_american_football, false));
        add(new Sport("Rugby", R.drawable.icon_american_football, false));
        add(new Sport("Tenis", R.drawable.icon_american_football, false));
        add(new Sport("El TETO", R.drawable.icon_american_football, false));
        add(new Sport("La piragüa", R.drawable.icon_american_football, false));
        add(new Sport("Otro deporte", R.drawable.icon_american_football, false));
    }

    public static RepositorySports getInstance(){

        if(instance == null){

            instance = new RepositorySports();
        }

        return instance;
    }

    public void updateSport(Sport oldSport, Sport newSport){

        int index = indexOf(oldSport);
        remove(oldSport);
        add(index, newSport);
    }
}
