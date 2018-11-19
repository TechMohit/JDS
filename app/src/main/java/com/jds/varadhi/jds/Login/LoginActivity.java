package com.jds.varadhi.jds.Login;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jds.varadhi.jds.AppPermissions;
import com.jds.varadhi.jds.Constant;
import com.jds.varadhi.jds.R;
import com.jds.varadhi.jds.Splash.SplashScreen;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String[] ALL_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_SMS


    };
    private static final int ALL_REQUEST_CODE = 0;
    SharedPreferences pref = null;
    private AppPermissions mRuntimePermission;
    private Fragment mFragment;
    private String fragment_Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mRuntimePermission = new AppPermissions(this);
        takeAllpermissionHere();
        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        checkLoadingFirstTime();


        if (savedInstanceState!=null)
        {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState,"lastfragment");

            getSupportFragmentManager().beginTransaction().replace(R.id.loginContainer, mFragment,mFragment.getTag()).commit();

        }




    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);
        if (mFragment != null) {


            getSupportFragmentManager().putFragment(outState, "lastfragment", mFragment);
        }
    }

    private void takeAllpermissionHere() {
        if (mRuntimePermission.hasPermission(ALL_PERMISSIONS)) {
            Toast.makeText(this, "All permission already given", Toast.LENGTH_SHORT).show();
        } else {
            mRuntimePermission.requestPermission(this, ALL_PERMISSIONS, ALL_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_REQUEST_CODE:
                List<Integer> permissionResults = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults.add(grantResult);
                }
                if (permissionResults.contains(PackageManager.PERMISSION_DENIED)) {
                    // Toast.makeText(this, "All Permissions not granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "All Permissions granted", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void checkLoadingFirstTime() {

        //loadFragment(Constant.FIRST_MOBILE_FRAGMENT, null);
        loadFragment(Constant.SPLASH_SCREEN_FRAGMENT, null);
       /* if (pref.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        } else {

            loadFragment(Constant.FIRST_CHOOSE_AUDIENCE_FRAGMENT, null);

        }*/
    }


    public void loadFragment(String fragmentString, Bundle bundle) {

        this.fragment_Tag = fragmentString;

        switch (fragmentString) {

            case Constant.SPLASH_SCREEN_FRAGMENT:
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.loginContainer, new SplashScreen(), Constant.SPLASH_SCREEN_FRAGMENT).commit();
                break;

            case Constant.FIRST_MOBILE_FRAGMENT:
                callFragment(new FirstMobileNumberFragment(), Constant.FIRST_MOBILE_FRAGMENT, null, null);
                break;

            case Constant.SECOND_OTP_FRAGMENT:
                callFragment(new SecondOtpFragment(), Constant.SECOND_OTP_FRAGMENT, null, null);
                break;

            case Constant.THIRD_MPIN_FRAGMENT:

                callFragment(new ThirdMpinFragment(), Constant.THIRD_MPIN_FRAGMENT, null, null);
                break;


        }
    }

    private void callFragment(Fragment fragment, String tag, String addBackStack, Bundle bundle) {
        if (bundle != null) {
            mFragment = fragment;
            getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.loginContainer, fragment, tag).addToBackStack(addBackStack).commit();
            fragment.getArguments();

        } else {
            mFragment = fragment;
            getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.loginContainer, fragment, tag).addToBackStack(addBackStack).commit();

        }

    }

}
