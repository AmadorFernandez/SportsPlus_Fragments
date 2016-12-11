package com.amador.pruebaexamen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by amador on 11/12/16.
 */

public class FragmentFrm extends Fragment {

    private HomeActivity activity;
    private EditText editText;
    private CheckBox checkBox;
    private Button button;
    private RelativeLayout parent;
    public static final String SPORT_KEY = "sport";
    private Sport oldSport;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }


    public static FragmentFrm newInstance(Bundle args) {

        FragmentFrm fragment = new FragmentFrm();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_form, null);

        editText = (EditText)rootView.findViewById(R.id.edtForm);
        checkBox = (CheckBox)rootView.findViewById(R.id.chkFrm);
        button = (Button)rootView.findViewById(R.id.btnForm);
        parent = (RelativeLayout)rootView.findViewById(R.id.relativeForm);

        if(getArguments() != null){


            oldSport = getArguments().getParcelable(SPORT_KEY);
            editText.setText(oldSport.getName());
            checkBox.setChecked(oldSport.isLike());

        }else {

            oldSport = null;
        }


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                editText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().isEmpty()){

                    editText.setError(getString(R.string.no_empty_name));

                }else {

                    Sport sport = new Sport(editText.getText().toString(),
                            R.drawable.icon_american_football, checkBox.isChecked());

                    if(RepositorySports.getInstance().contains(sport)){

                        editText.setError(getString(R.string.sport_invalid));

                    }else if(oldSport == null && !RepositorySports.getInstance().contains(sport)) {

                        RepositorySports.getInstance().add(sport);
                        Snackbar.make(parent, getString(R.string.add_sport), Snackbar.LENGTH_LONG).show();

                    }else {

                        RepositorySports.getInstance().updateSport(oldSport, sport);
                        Snackbar.make(parent, getString(R.string.update_sport), Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (HomeActivity)activity;
    }
}
