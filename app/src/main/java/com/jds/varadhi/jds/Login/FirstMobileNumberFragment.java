package com.jds.varadhi.jds.Login;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jds.varadhi.jds.Constant;
import com.jds.varadhi.jds.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstMobileNumberFragment extends Fragment implements View.OnClickListener {

    Button btnNextFirstFragment,btnSignUp;
    EditText etMobileNumber;
    String mobileNumber;
    DatabaseReference databaseReferenceAdmin,databaseReferenceMember;
    ProgressDialog progressDialog;
    boolean adminOrMemberExists = false;
    private String KEY_TITLE;


    public FirstMobileNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_first_mobile_number, container, false);
        databaseReferenceAdmin = FirebaseDatabase.getInstance().getReference("Admin");
        databaseReferenceMember = FirebaseDatabase.getInstance().getReference("MemberSnapShot");
        etMobileNumber = v.findViewById(R.id.etMobileNumber);
        btnNextFirstFragment = v.findViewById(R.id.btnNextFirstFragment);
        //btnSignUp            = v.findViewById(R.id.btnSignUp);
        //btnSignUp.setOnClickListener(this);
        btnNextFirstFragment.setOnClickListener(this);

        return v;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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


        /*databaseReferenceMember.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShotA : dataSnapshot.getChildren()){
                    Log.d("postSnapShotA", ""+postSnapShotA.getKey());
                    for(DataSnapshot postSnapShotB : postSnapShotA.getChildren()){
                        Log.d("postSnapShotB", ""+postSnapShotB.getKey());
                        if (mobileNumber.equals(postSnapShotB.getKey())) {
                            Constant.ADMIN_OR_MEMBER = "MEMBER";
                            adminOrMemberExists = true;
                            progressDialog.dismiss();
                        }
                        else {
                            Toast.makeText(getActivity(), "Not Exists", Toast.LENGTH_SHORT).show();
                        }

                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

        /*if(adminOrMemberExists){

            switch (Constant.ADMIN_OR_MEMBER){

                case "ADMIN":
                    Toast.makeText(getActivity(), "Admin", Toast.LENGTH_SHORT).show();
                    break;

                case "MEMBER":
                    Toast.makeText(getActivity(), "Member", Toast.LENGTH_SHORT).show();

                    break;
            }

        }
        else {
            Toast.makeText(getActivity(), "User Not Exists", Toast.LENGTH_SHORT).show();

        }*/


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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("mohit","onSaveInstanceState");
        outState.putCharSequence(KEY_TITLE, etMobileNumber.getText().toString());
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


    @Override
    public void onStart() {
        super.onStart();
        /*if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }*/
    }
}
