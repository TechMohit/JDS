package com.jds.varadhi.jds.Login;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jds.varadhi.jds.AddModules.SelectModuleActivity;
import com.jds.varadhi.jds.PasscodeView;
import com.jds.varadhi.jds.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdMpinFragment extends Fragment implements View.OnClickListener {

    PasscodeView   passcode_View;
    private String userMpin;
    boolean        fingerAuthenticateStatus = true;
    private Dialog               dialog = null;
    ImageView iv_fingerAuthImage, iv_mPinAuthImage;
    Button btnMpin,btnFingerPrint;
    SharedPreferences            defaultStatusForSecurity = null;
    String                       default_authenticate;


    public ThirdMpinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_third_mpin, container, false);
        defaultStatusForSecurity = getActivity().getSharedPreferences("SecurityStatus", Context.MODE_PRIVATE);
        initializePassCodeView(v);

        return v;
    }

    private void initializePassCodeView(View v) {

        passcode_View = v.findViewById(R.id.passcodeView);
        passcode_View.setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess(String number) {
                userMpin = number;
                Log.d("MPINUSER", ""+number);
                checkFingerAuthenticationIsThere();

            }
        });
    }

    private void checkFingerAuthenticationIsThere() {

        SharedPreferences.Editor edt = defaultStatusForSecurity.edit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Fingerprint API only available on from Android 6.0 (M)
            FingerprintManager fingerprintManager = (FingerprintManager)getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
            assert fingerprintManager != null;
            if (!fingerprintManager.isHardwareDetected()) {
                // Device doesn't support fingerprint authentication
                fingerAuthenticateStatus = false;
                openDialogForDefaultSecurity();


            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                // User hasn't enrolled any fingerprints to authenticate with
                fingerAuthenticateStatus = false;
                openDialogForDefaultSecurity();

            } else {
                // Everything is ready for fingerprint authentication
                fingerAuthenticateStatus = true;
                openDialogForDefaultSecurity();
            }
            edt.putBoolean("FingerPrintStatus", fingerAuthenticateStatus);
            edt.apply();

        }
        else {
            edt.putBoolean("FingerPrintStatus", fingerAuthenticateStatus);
            edt.apply();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void openDialogForDefaultSecurity() {

        if(fingerAuthenticateStatus) {

            assert getActivity()!=null;
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.choose_default_authenticate_a);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blurBackground)));

            iv_fingerAuthImage = dialog.findViewById(R.id.fingerAuth);
            iv_mPinAuthImage   = dialog.findViewById(R.id.mPinAuth);
            btnFingerPrint     = dialog.findViewById(R.id.btn_fingerPrint);
            btnMpin            = dialog.findViewById(R.id.btn_mpin);
            btnMpin.setOnClickListener(this);
            btnFingerPrint.setOnClickListener(this);
            iv_fingerAuthImage.setOnClickListener(this);
            iv_mPinAuthImage.setOnClickListener(this);
            dialog.setCancelable(false);
            dialog.show();
        }
        else {

            assert getActivity()!=null;
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.choose_default_authenticate_a);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blurBackground)));
            LinearLayout linearLayoutFingerAuth = dialog.findViewById(R.id.finger_ll);
            View viewVertical = dialog.findViewById(R.id.viewVertical);
            viewVertical.setVisibility(View.GONE);
            LinearLayout linearLayoutFingerbtn = dialog.findViewById(R.id.fingerPrintBtnLayout);
            linearLayoutFingerbtn.setVisibility(View.GONE);
            linearLayoutFingerAuth.setVisibility(View.GONE);
            iv_fingerAuthImage = dialog.findViewById(R.id.fingerAuth);
            iv_mPinAuthImage   = dialog.findViewById(R.id.mPinAuth);
            btnMpin            = dialog.findViewById(R.id.btn_mpin);
            btnMpin.setOnClickListener(this);
            iv_fingerAuthImage.setOnClickListener(this);
            iv_mPinAuthImage.setOnClickListener(this);
            dialog.setCancelable(false);
            dialog.show();


        }

    }

    @Override
    public void onClick(View v) {

        Intent in = new Intent(getActivity(), SelectModuleActivity.class);
        assert getActivity()!=null;
        SharedPreferences.Editor edt = defaultStatusForSecurity.edit();

        switch (v.getId()){
            case R.id.mPinAuth:
                iv_mPinAuthImage.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.check_animation));
                iv_mPinAuthImage.setBackground(getResources().getDrawable(R.drawable.ic_checked));
                iv_fingerAuthImage.setBackground(getResources().getDrawable(R.drawable.ic_fingerprint_grey));
                default_authenticate = "MPIN";
                edt.putString("DefaultSOption", default_authenticate);
                edt.putBoolean("FingerPrintStatus", fingerAuthenticateStatus);
                edt.putString("MPINUSER", userMpin);
                edt.apply();
                dialog.dismiss();
                startActivity(in);
                getActivity().finish();

                break;

            case R.id.fingerAuth:
                iv_fingerAuthImage.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.check_animation));
                iv_fingerAuthImage.setBackground(getResources().getDrawable(R.drawable.ic_checked));
                iv_mPinAuthImage.setBackground(getResources().getDrawable(R.drawable.ic_lock_a));
                default_authenticate = "MPIN";
                edt.putString("DefaultSOption", default_authenticate);
                edt.putBoolean("FingerPrintStatus", fingerAuthenticateStatus);
                edt.putString("MPINUSER", userMpin);
                edt.apply();
                dialog.dismiss();
                startActivity(in);
                getActivity().finish();

                break;
            case R.id.btn_mpin:
                iv_mPinAuthImage.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.check_animation));
                iv_mPinAuthImage.setBackground(getResources().getDrawable(R.drawable.ic_checked));
                iv_fingerAuthImage.setBackground(getResources().getDrawable(R.drawable.ic_fingerprint_grey));
                default_authenticate = "MPIN";
                edt.putString("DefaultSOption", default_authenticate);
                edt.putBoolean("FingerPrintStatus", fingerAuthenticateStatus);
                edt.putString("MPINUSER", userMpin);
                edt.apply();
                dialog.dismiss();
                startActivity(in);
                getActivity().finish();

                break;

            case R.id.btn_fingerPrint:
                iv_fingerAuthImage.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.check_animation));
                iv_fingerAuthImage.setBackground(getResources().getDrawable(R.drawable.ic_checked));
                iv_mPinAuthImage.setBackground(getResources().getDrawable(R.drawable.ic_lock_a));
                default_authenticate = "MPIN";
                edt.putString("DefaultSOption", default_authenticate);
                edt.putBoolean("FingerPrintStatus", fingerAuthenticateStatus);
                edt.putString("MPINUSER", userMpin);
                edt.apply();
                dialog.dismiss();
                startActivity(in);
                getActivity().finish();
                break;

        }
    }
}
