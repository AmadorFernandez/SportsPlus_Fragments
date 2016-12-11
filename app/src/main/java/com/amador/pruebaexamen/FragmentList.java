package com.amador.pruebaexamen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by amador on 11/12/16.
 */

public class FragmentList extends Fragment {

    private ListView listView;
    private ListSportAdapter adapter;
    private RelativeLayout parent;
    private Button button;
    private HomeActivity activity;
    private static final String RESTORE_KEY = "restoreAdapter";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        SportsPreferences.loadLikeSpots(activity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        ArrayList<Sport> restoreList = new ArrayList<Sport>();

        for(int i = 0; i < adapter.getCount(); i++){

            restoreList.add(adapter.getItem(i));
        }

        outState.putParcelableArrayList(RESTORE_KEY, restoreList);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, null);
        parent = (RelativeLayout)rootView.findViewById(R.id.relativeList);

        if(savedInstanceState != null){

            adapter = new ListSportAdapter(activity, RepositorySports.getInstance());
            adapter.restoreState(savedInstanceState.<Sport>getParcelableArrayList(RESTORE_KEY));

        }else {

            adapter = new ListSportAdapter(activity, RepositorySports.getInstance());

        }


        listView = (ListView)rootView.findViewById(R.id.listSports);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);


        button = (Button)rootView.findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SportsPreferences.saveSports(activity);
                loadMsg(getString(R.string.pref_save));
            }
        });

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getActivity().getMenuInflater().inflate(R.menu.context_menu_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){

            case R.id.action_delete_sport:
                loadDeleteDialog(info);
                break;
            case R.id.action_update_sport:
                loadFrmModeUpdate(info);
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void loadFrmModeUpdate(AdapterView.AdapterContextMenuInfo info) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(FragmentFrm.SPORT_KEY, adapter.getItem(info.position));
        activity.loadForm(bundle);
    }

    private void loadDeleteDialog(final AdapterView.AdapterContextMenuInfo info) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(R.string.alert);
        builder.setMessage(getString(R.string.ask_delete));

        builder.setPositiveButton(getString(R.string.text_acept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Sport sport = adapter.getItem(info.position);
                adapter.removeSport(sport);
                SportsPreferences.removePref(activity, sport.getName());

            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        }).show();


    }

    private void loadMsg(String msg) {

        Snackbar.make(parent, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
        this.activity = (HomeActivity)activity;

    }

    private void loadFilterDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.text_dialog, null);
        final TextView textView = (TextView)view.findViewById(R.id.edtFilter);

        builder.setView(view);

        builder.setTitle(getString(R.string.action_filter));

        builder.setPositiveButton(getString(R.string.text_acept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                adapter.filter(textView.getText().toString());
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        }).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_order_alf:
                adapter.orderBy(ListSportAdapter.ORDER_BY_ALF);
                break;
            case R.id.action_filter:
                loadFilterDialog();
                break;
            case R.id.action_all_sports:
                adapter.orderBy(ListSportAdapter.ALL_SPORTS);
                break;
            case R.id.action_add:
                activity.loadForm(null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
}
