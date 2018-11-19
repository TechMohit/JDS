package com.jds.varadhi.jds.Splash;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jds.varadhi.jds.Constant;
import com.jds.varadhi.jds.Login.LoginActivity;
import com.jds.varadhi.jds.R;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreen extends Fragment implements View.OnClickListener {
    LinearLayout linearLayout, nameLayout;
    TextView name;
    PulsatorLayout pulsator;
    Button btnNextFirstFragment;
    EditText etMobileNumber;
    DatabaseReference databaseReferenceAdmin,databaseReferenceMember;
    ProgressDialog progressDialog;
    boolean adminOrMemberExists = false;
    private String KEY_TITLE;


    public SplashScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initViews(view);
        Animation animFadeIn = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        name.startAnimation(animFadeIn);
        linearLayout.startAnimation(animFadeIn);
        pulsator.start();
        btnNextFirstFragment.setOnClickListener(this);
        return view;
    }

    private void initViews( View view) {

        linearLayout =  view.findViewById(R.id.loginlayout);
        nameLayout   =  view.findViewById(R.id.namelayout);
        name         =  view.findViewById(R.id.name);
        pulsator     =  view.findViewById(R.id.pulsator);
        btnNextFirstFragment = view.findViewById(R.id.btnNextFirstFragment);
        databaseReferenceAdmin = FirebaseDatabase.getInstance().getReference("Admin");
        databaseReferenceMember = FirebaseDatabase.getInstance().getReference("MemberSnapShot");
        etMobileNumber = view.findViewById(R.id.etMobileNumber);



    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("mohit","onSaveInstanceState");
        outState.putCharSequence(KEY_TITLE, etMobileNumber.getText().toString());
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("mohit","onActivityCreated");
        String savedTitle = null;
        if (savedInstanceState != null) {
            savedTitle = savedInstanceState.getString(KEY_TITLE);
            etMobileNumber.setText(savedTitle);

        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                linearLayout.setVisibility(View.VISIBLE);
                nameLayout.setVisibility(View.INVISIBLE);

            }
        }, 100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNextFirstFragment:
                String number = etMobileNumber.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    etMobileNumber.setError("Valid number is required");
                    etMobileNumber.requestFocus();
                    return;
                }
                else {
                    getAdminMemberFromFirebase("+" + "91" +number);
                    Log.d("mobile", number);

                }

                //Constant.mobileNumber = "+" + "91" + number;
                //loginActivity.loadFragment(Constant.SECOND_OTP_FRAGMENT, null);
                break;
        }
    }

    private void getAdminMemberFromFirebase(final String mobileNumber) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Searching User");
        progressDialog.show();

        databaseReferenceAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("User", ""+dataSnapshot.getKey());
                for(DataSnapshot postSnapShotA : dataSnapshot.getChildren()) {
                    Log.d("JDhkj", ""+postSnapShotA.getKey());
                    if (mobileNumber.equals(postSnapShotA.getKey())) {
                        Constant.ADMIN_OR_MEMBER = "ADMIN";
                        adminOrMemberExists = true;
                        Constant.mobileNumber = mobileNumber;
                        LoginActivity loginActivity = (LoginActivity) getActivity();
                        progressDialog.dismiss();
                        getDetails(Constant.ADMIN_OR_MEMBER);

                        //assert loginActivity!=null;
                        //loginActivity.loadFragment(Constant.SECOND_OTP_FRAGMENT, null);

                    } else {

                        databaseReferenceMember.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot postSnapShotA : dataSnapshot.getChildren()){
                                    Log.d("postSnapShotA", ""+postSnapShotA.getKey());
                                    for(DataSnapshot postSnapShotB : postSnapShotA.getChildren()){
                                        Log.d("postSnapShotB", ""+postSnapShotB.getKey());
                                        if (mobileNumber.equals(postSnapShotB.getKey())) {
                                            Log.d("MemberID", ""+postSnapShotA.getKey());
                                            Constant.MEMBERSHIP_ID = postSnapShotA.getKey();
                                            Constant.ADMIN_OR_MEMBER = "MEMBER";
                                            adminOrMemberExists = true;
                                            Constant.mobileNumber = mobileNumber;
                                            progressDialog.dismiss();
                                            getDetails(Constant.ADMIN_OR_MEMBER);
                                            for(DataSnapshot postSnapShotC : postSnapShotB.getChildren()){
                                                Log.d("postSnapShotC", ""+postSnapShotC.getKey());
                                                Constant.DESIGNATION = postSnapShotC.getKey();

                                            }
                                        }



                                    }
                                    progressDialog.dismiss();
                                    if(!adminOrMemberExists){
                                        Toast.makeText(getActivity(), "No user found", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        progressDialog.dismiss();
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getDetails(String adminOrMember) {

        Constant.ADMIN_OR_MEMBER = adminOrMember;

        LoginActivity loginActivity = (LoginActivity) getActivity();
        assert loginActivity!=null;
        Toast.makeText(getActivity(), ""+adminOrMember, Toast.LENGTH_SHORT).show();

        switch (adminOrMember){

            case "ADMIN":
                loginActivity.loadFragment(Constant.SECOND_OTP_FRAGMENT, null);
                break;

            case "MEMBER":
                loginActivity.loadFragment(Constant.SECOND_OTP_FRAGMENT, null);
                break;
        }
    }
}
