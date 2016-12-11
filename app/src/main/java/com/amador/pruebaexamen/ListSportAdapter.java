package com.amador.pruebaexamen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amador on 11/12/16.
 */

public class ListSportAdapter extends ArrayAdapter<Sport> {


    private ArrayList<Sport> localList;
    private Context context;
    public static final int ORDER_BY_ALF = 0;
    public static final int ALL_SPORTS = 1;

    public ListSportAdapter(Context context, ArrayList<Sport> datas) {
        super(context, R.layout.item_sports, new ArrayList<Sport>(datas));

        this.localList = datas;
        this.context = context;
    }

    public void restoreState(ArrayList<Sport> restoreList){

        clear();
        addAll(restoreList);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rootView = convertView;
        SportsHolder holder;

        if(rootView == null){

            rootView = LayoutInflater.from(context).inflate(R.layout.item_sports, null);
            holder = new SportsHolder();

            holder.imageView = (ImageView)rootView.findViewById(R.id.imvSportIcon);
            holder.textView = (TextView)rootView.findViewById(R.id.txvSportName);
            holder.checkBox = (CheckBox)rootView.findViewById(R.id.check);

            rootView.setTag(holder);

        }else {

            holder = (SportsHolder)rootView.getTag();
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                getItem(position).setLike(b);
            }
        });

        holder.imageView.setImageResource(getItem(position).getIcon());
        holder.textView.setText(getItem(position).getName());
        holder.checkBox.setChecked(getItem(position).isLike());
        holder.checkBox.setFocusable(false);

        return rootView;
    }

    public void removeSport(Sport sport){

        localList.remove(sport);
        remove(sport);


    }

    public void orderBy(int typeOrder){

        switch (typeOrder){

            case ORDER_BY_ALF:
                sort(Sport.ORDER_BY_ALF);
                break;
            case ALL_SPORTS:
                clear();
                addAll(new ArrayList<Sport>(localList));
                break;
        }

    }

    public void filter(String text){

        ArrayList<Sport> tmpList = new ArrayList<Sport>();


        for(int i = 0; i< getCount(); i++){

            if(getItem(i).getName().toUpperCase().startsWith(text.toUpperCase())){

                tmpList.add(getItem(i));
            }
        }

        clear();
        addAll(tmpList);
    }

    class SportsHolder{

        ImageView imageView;
        TextView textView;
        CheckBox checkBox;
    }
}
