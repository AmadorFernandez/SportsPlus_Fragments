package com.amador.pruebaexamen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by amador on 11/12/16.
 */

public class FragmentLogin extends Fragment {

    private TextInputLayout tilMail, tilName;
    private EditText edtMail, edtName;
    private Button btnLogin;
    private HomeActivity activity;
    private OnCallLoginListener listener;
    public interface OnCallLoginListener{

        void onLoginOk(String userMail, String userName);
    }

    private void setListener(OnCallLoginListener listener){

        this.listener = listener;
    }

    public static FragmentLogin newInstance(OnCallLoginListener listener) {


        FragmentLogin fragment = new FragmentLogin();
        fragment.setListener(listener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, null);

        tilMail = (TextInputLayout) rootView.findViewById(R.id.tilUserMail);
        tilName = (TextInputLayout) rootView.findViewById(R.id.tilUserName);
        edtMail = (EditText) rootView.findViewById(R.id.edtUserMail);
        edtName = (EditText) rootView.findViewById(R.id.edtUserName);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateEmail(edtMail.getText().toString())){

                    tilMail.setError(getString(R.string.incorret_format_mail));

                }else if(!validateName(edtName.getText().toString())){

                    tilName.setError(getString(R.string.incorret_format_name));

                }else {

                    if(listener != null){

                        listener.onLoginOk(edtMail.getText().toString(), edtName.getText().toString());
                    }
                }

            }
        });


        return rootView;
    }

    private boolean validateEmail(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateName(String name) {

        return name.length() > 3;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (HomeActivity) activity;

    }
}
