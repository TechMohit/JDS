package com.jds.varadhi.jds.AddRoles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jds.varadhi.jds.Constant;
import com.jds.varadhi.jds.R;
import com.jds.varadhi.jds.RecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class RolesFragment extends Fragment implements View.OnClickListener{

    EditText edit_enter;
    ImageButton add_image;
    Button button_added;
    RecyclerView recycler_view;
    ArrayList<Data> list;
    ArrayList<String> checker;
    ArrayList<String> maker;
    RecyclerAdapter recyclerAdapter;
    //DatabaseReference databaseReferenceStaffRolesBarriers;
    ArrayList<String> checkedArrayList;
    ArrayList<String> unCheckedArrayList;
    Spinner rolesSpinner;
    ImageView ivAddRoles;
    DatabaseReference databaseReference;


    HashMap<String , ArrayList<String>> makerCheckerModuleMap;


    String[] roles={"Party President","Vice President","Treasurer","Media Management","Booth Management","District President",
            "Mandal President","Mandal Incharge","Village President","Follower"};

    //private ProgressDialog progressDialog = null;



    public RolesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roles, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Management_Data/Add_Roles");
        list = new ArrayList<>();
        checker = new ArrayList<>();
        checkedArrayList = new ArrayList<>();
        unCheckedArrayList = new ArrayList<>();
        maker=new ArrayList<>();

        edit_enter =view. findViewById(R.id.edit_enter);
        add_image = view.findViewById(R.id.add_image);
        button_added =view. findViewById(R.id.button_added);
        button_added.setOnClickListener(this);
        recycler_view = view.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);
        rolesData();

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = "";
                String editvalue = edit_enter.getText().toString();
                if (editvalue.equals("")) {
                    Toast.makeText(getContext(), "Please enter the Value", LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().toLowerCase().equals(editvalue.toLowerCase())) {
                            value = list.get(i).getName();
                        }
                    }
                    if (value.toLowerCase().equals(editvalue.toLowerCase())) {
                        Toast.makeText(getContext(), "Already Exist", LENGTH_SHORT).show();
                    } else {
                        recyclerAdapter.newValues(editvalue);
                    }
                    edit_enter.setText("");
                }
            }

        });

        return view;
    }

    private void rolesData() {
        for (String aName : roles) {
            list.add(new Data(aName, false));
            Log.d("rolesList", ""+aName);
            recyclerAdapter = new RecyclerAdapter(getContext(),list, Constant.ROLES_FRAG);
            recycler_view.setAdapter(recyclerAdapter);
           // progressDialog.dismiss();

        }
    }


    @Override
    public void onClick(View v) {

        AddRolesActivity addRolesActivity = (AddRolesActivity) getActivity();

        assert addRolesActivity!=null;

        switch (v.getId()){

            case R.id.button_added:
                //addRolesActivity.loadFragment(Constant.ROLES_FRAG);

                checker.clear();
                checkedArrayList.clear();
                unCheckedArrayList.clear();

                for (int i = 0; i < list.size(); i++) {
                    Data data = list.get(i);
                    if (data.isSelect()) {
                        Log.d("Checked", "" + data.getName());

                        checkedArrayList.add(data.getName());
                        checker.add(data.getName());
                        maker.add(data.getName());
                    } else {

                        unCheckedArrayList.add(data.getName());
                        checker.remove(data.getName());
                        maker.remove(data.getName());
                    }
                }
                if (checker.size() == 0) {
                    Toast.makeText(getContext(), "Selectable value is must", LENGTH_SHORT).show();

                } else {

                    Constant.selectedRolesArrayList = new ArrayList<>();
                    Constant.selectedRolesArrayList= checker;
                    Constant.unselectedRolesArrayList = new ArrayList<>();
                    Constant.unselectedRolesArrayList = unCheckedArrayList;

                    for(int i = 0 ; i<checker.size(); i++){
                        Log.d("CheckedArrayList", ""+checker.get(i));
                        databaseReference.child("selected_roles").child(""+i).setValue(checker.get(i));
                    }
                    for(int i = 0 ; i<unCheckedArrayList.size(); i++){
                        Log.d("UnCheckedArrayList", ""+unCheckedArrayList.get(i));
                        databaseReference.child("unselected_roles").child(""+i).setValue(unCheckedArrayList.get(i));

                    }

                    addRolesActivity.loadFragment(Constant.ROLES_FRAG);

                }


                break;

        }

    }
}
