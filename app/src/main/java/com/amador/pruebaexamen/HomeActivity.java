package com.amador.pruebaexamen;

import android.app.Application;
import android.app.Fragment;
import android.preference.Preference;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    private FragmentLogin fragmentLogin;
    private FragmentMenu fragmentMenu;
    private FragmentList fragmentList;
    private FragmentFrm fragmentFrm;
    private FragmentPreferences fragmentPreferences;
    public static final String TAG_LOGIN = "login";
    public static final String TAG_MENU = "menu";
    public static final String TAG_PREF = "pref";
    public static final String TAG_LIST = "list";
    public static final String TAG_FRM = "frm";
    private User user;

    private FragmentLogin.OnCallLoginListener callLoginListener = new FragmentLogin.OnCallLoginListener() {
        @Override
        public void onLoginOk(String userMail, String userName) {

            user.setUserMail(userMail);
            user.setUserName(userName);
            UserPreferences.saveUserMail(userMail, HomeActivity.this);
            UserPreferences.saveUserName(userName, HomeActivity.this);
            replaceFormMenu(fragmentLogin);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = User.getUser();
        user.setUserMail(UserPreferences.getUserMail(this));
        user.setUserName(UserPreferences.getUserName(this));

        if(savedInstanceState == null) {

            if (user.getUserName() != null && user.getUserMail() != null) {

                loadMenu();

            } else {

                loadLogin();
            }
        }


    }

    public void loadLogin(){

        fragmentLogin = (FragmentLogin)getFragmentManager().findFragmentByTag(TAG_LOGIN);

        if(fragmentLogin == null){

            fragmentLogin = FragmentLogin.newInstance(callLoginListener);


        }

        getFragmentManager().beginTransaction().replace(R.id.activity_home, fragmentLogin, TAG_LOGIN).commit();

    }

    public void replaceFormMenu(Fragment fragment){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(fragment).commit();
        loadMenu();
    }

    public void loadMenu(){

        fragmentMenu = (FragmentMenu) getFragmentManager().findFragmentByTag(TAG_MENU);

        if(fragmentMenu == null){

            fragmentMenu = new FragmentMenu();

            getFragmentManager().beginTransaction().add(R.id.activity_home, fragmentMenu, TAG_MENU)
                    .addToBackStack(null).commit();

        }else {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.activity_home, fragmentMenu, TAG_MENU);
            ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {

        if(fragmentMenu != null){

            if(fragmentMenu.isVisible()) {
                getFragmentManager().beginTransaction().remove(fragmentMenu).commit();
                super.onBackPressed();
            }

        }else if(fragmentLogin != null){

            if(fragmentLogin.isVisible()){

                getFragmentManager().beginTransaction().remove(fragmentLogin).commit();
                super.onBackPressed();
            }
        }
        super.onBackPressed();
    }

    public void loadPref(){

        fragmentPreferences = new FragmentPreferences();
        getFragmentManager().beginTransaction().replace(R.id.activity_home, fragmentPreferences, TAG_PREF)
                .addToBackStack(null).commit();

    }

    public void loadForm(Bundle args){

        if(args == null){

            fragmentFrm = new FragmentFrm();

        }else {

            fragmentFrm = FragmentFrm.newInstance(args);
        }

        getFragmentManager().beginTransaction().replace(R.id.activity_home, fragmentFrm, TAG_FRM).addToBackStack(null).commit();


    }

    public void loadListFragment(){

        fragmentList = (FragmentList) getFragmentManager().findFragmentByTag(TAG_LIST);

        if(fragmentList == null){

            fragmentList = new FragmentList();
        }

        getFragmentManager().beginTransaction().replace(R.id.activity_home, fragmentList, TAG_LIST).addToBackStack(null)
                .commit();

    }
}
