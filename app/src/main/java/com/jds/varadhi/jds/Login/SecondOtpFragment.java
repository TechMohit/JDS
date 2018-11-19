package com.jds.varadhi.jds.Login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jds.varadhi.jds.Constant;
import com.jds.varadhi.jds.Mpin;
import com.jds.varadhi.jds.R;
import com.jds.varadhi.jds.SuccessLoadingView;

import java.util.concurrent.TimeUnit;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondOtpFragment extends Fragment implements View.OnClickListener {
    Button btnNextSecondFragment;
    TextView tvUserMobileNumber;
    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog = null;
    Mpin otp;
    Activity activity;
    DatabaseReference mRefCheckDesignationExists;
    DatabaseReference databaseRefNotificationNode;
    private String KEY_TITLE;

    public SecondOtpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Secondotpfrag","onCreateView");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.second_otp_a, container, false);
        mRefCheckDesignationExists = FirebaseDatabase.getInstance().getReference("Management_Data/Checker_Maker/Modules/Notification");
        databaseRefNotificationNode = FirebaseDatabase.getInstance().getReference("Tokens/Notofication");
        activity =(Activity) v.getContext();
        btnNextSecondFragment = v.findViewById(R.id.btnNextSecondFragment);
        tvUserMobileNumber = v.findViewById(R.id.tvUserMobileNumber);
        tvUserMobileNumber.setText(Constant.mobileNumber);
        btnNextSecondFragment.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        otp = v.findViewById(R.id.mPinOtp);
        String phonenumber = Constant.mobileNumber;
        sendVerificationCode(phonenumber);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Secondotpfrag","onSaveInstanceState"+otp.getValue());
        outState.putCharSequence(KEY_TITLE, otp.getValue().toString());
    }


    private void sendVerificationCode(String number) {
        //progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String savedTitle = null;
        if (savedInstanceState != null) {
            savedTitle = savedInstanceState.getString(KEY_TITLE);
            Log.d("Secondotpfrag","onActivityCreated"+savedTitle);
            otp.setValue("KEY_TITLE");



        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnNextSecondFragment:
                if(otp.getValue().length()==6){
                    verifyCode(otp.getValue());
                    hideKeyboard(activity);


                }

                break;
        }
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otp.setValue(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        if(!verificationId.equals("")) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithCredential(credential);
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Verifying...");
        progressDialog.show();

        switch (Constant.ADMIN_OR_MEMBER){

            case "ADMIN":
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    LoginActivity loginActivity = (LoginActivity) getActivity();
                                    assert loginActivity!=null;
                                    Toast.makeText(getActivity(), "Verification Success", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    loginActivity.loadFragment(Constant.THIRD_MPIN_FRAGMENT, null);

                                }

                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;

            case "MEMBER":

                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), ""+Constant.DESIGNATION, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getActivity(), "Continue user Dashboard", Toast.LENGTH_SHORT).show();
                                    checkDesignationExitsInNotification();
                                }

                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;


        }




    }

    private void checkDesignationExitsInNotification() {

        mRefCheckDesignationExists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataSnapshotAA", ""+dataSnapshot.getKey());

                StringBuilder stringBuilder =new StringBuilder();

                for(DataSnapshot postSnapShotA : dataSnapshot.getChildren())
                {

                    Log.d("postSnapShotAA", ""+postSnapShotA.getKey());

                    if (postSnapShotA.getKey().equalsIgnoreCase("CheckerList"))
                    {

                        //checkerlist
                        for(DataSnapshot postSnapShotB : postSnapShotA.getChildren())
                        {

                            Log.d("postSnapShotAB", ""+postSnapShotB.getKey());
                            Log.d("postSnapSddhotAB", ""+Constant.DESIGNATION);

                            if(Constant.DESIGNATION.equals(postSnapShotB.getValue()))
                            {
                                Log.d("postSnapShotAAAa", ""+postSnapShotB.getValue());

                                stringBuilder.append("Checker");

                                Log.d("memberChcek", ""+postSnapShotA.getKey()+"match"+""+postSnapShotA.getValue());

                            }


                        }


                    }
                    else if (postSnapShotA.getKey().equalsIgnoreCase("MakerList"))
                    {

                        for(DataSnapshot postSnapShotB : postSnapShotA.getChildren()){

                            Log.d("postSnapShotAB", ""+postSnapShotB.getKey());

                            if(Constant.DESIGNATION.equals(postSnapShotB.getValue())){

                                if (stringBuilder.length() > 1)
                                {
                                    stringBuilder.append(", Maker");
                                }else if (stringBuilder.length() == 0)
                                {
                                    stringBuilder.append("Maker");
                                }


                                Log.d("memberChcek", ""+postSnapShotA.getKey()+"match"+""+postSnapShotA.getValue());

                            }

                        }

                    }

                }

                Log.d("checkerlistere",stringBuilder.toString());

                String Checker_maker = stringBuilder.toString();


                if (Checker_maker.equalsIgnoreCase("Checker, Maker"))
                {
                    databaseRefNotificationNode.child("Checkers").child(Constant.MEMBERSHIP_ID).child("Token_ID").setValue(FirebaseInstanceId.getInstance().getToken());
                    databaseRefNotificationNode.child("Checkers").child(Constant.MEMBERSHIP_ID).child("Designation").setValue(Constant.DESIGNATION);

                    databaseRefNotificationNode.child("Makers").child(Constant.MEMBERSHIP_ID).child("Token_ID").setValue(FirebaseInstanceId.getInstance().getToken());
                    databaseRefNotificationNode.child("Makers").child(Constant.MEMBERSHIP_ID).child("Designation").setValue(Constant.DESIGNATION);


                    //databaseRefNotificationNode.child()
                    //add to checker and maker
                }
                else if (Checker_maker.equalsIgnoreCase("Checker"))
                {
                    //add to chcker
                    databaseRefNotificationNode.child("Checkers").child(Constant.MEMBERSHIP_ID).child("Token_ID").setValue(FirebaseInstanceId.getInstance().getToken());
                    databaseRefNotificationNode.child("Checkers").child(Constant.MEMBERSHIP_ID).child("Designation").setValue(Constant.DESIGNATION);

                }else if (Checker_maker.equalsIgnoreCase("Maker"))
                {

                    databaseRefNotificationNode.child("Makers").child(Constant.MEMBERSHIP_ID).child("Token_ID").setValue(FirebaseInstanceId.getInstance().getToken());
                    databaseRefNotificationNode.child("Makers").child(Constant.MEMBERSHIP_ID).child("Designation").setValue(Constant.DESIGNATION);

                    //add to maker
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) activity).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }




}
