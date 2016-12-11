package com.amador.pruebaexamen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by amador on 11/12/16.
 */

public class FragmentMenu extends Fragment {

    private TextView txvDeleteDataUser, txvList, txvSeeDataUser;
    private HomeActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_menu, null);

        txvDeleteDataUser = (TextView)rootView.findViewById(R.id.txvDeleteDataUser);
        txvDeleteDataUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadConfirmDialog();

            }
        });

        txvSeeDataUser = (TextView)rootView.findViewById(R.id.txvDataUser);
        txvSeeDataUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.loadPref();
            }
        });

        txvList = (TextView)rootView.findViewById(R.id.txvList);
        txvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.loadListFragment();
            }
        });

        return rootView;
    }

    private void loadConfirmDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(getString(R.string.delete_user_title));
        builder.setMessage(getString(R.string.delete_user_msg));

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                UserPreferences.saveUserName(null, activity);
                UserPreferences.saveUserMail(null, activity);
                SportsPreferences.removeAllPref(activity);
                activity.loadLogin();

            }

        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        }).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (HomeActivity)activity;
    }
}
