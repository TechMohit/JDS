package com.jds.varadhi.jds.AddRoles;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jds.varadhi.jds.AddMember.CustomSpinnerAdapter;
import com.jds.varadhi.jds.Constant;
import com.jds.varadhi.jds.R;
import com.jds.varadhi.jds.RecyclerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCheckerMakerFragment extends Fragment implements View.OnClickListener {

    Spinner spinner_module;
    ImageButton checker_button, maker_button;
    RecyclerView recyclerViewMaker,recylerViewChecker,recycler_module;
    boolean[] maker_boolean, checker_boolean;
    String[] maker_values_a;
    ArrayList<String> makerItems = new ArrayList<>();
    ArrayList<Data> maker_choice,checker_choice;
    ProgressDialog progressDialog = null;
    String[] checker_values_a;
    ArrayList<String> checkerItems = new ArrayList<>();
    Button add_database;
    String maker_values = "", checker_values = "";
    String spinner;
    ArrayList<Data> checkerMakerRole;
    private String myData;
    ArrayList<Data> module_choice;
    ArrayList<String> name = new ArrayList<>();
    DatabaseReference databaseReference;








    public AddCheckerMakerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_checker_maker, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Management_Data/Checker_Maker");
        spinner_module = v.findViewById(R.id.spinner_module);
        checker_button = v.findViewById(R.id.checker_button);
        maker_button = v.findViewById(R.id.maker_button);
        recyclerViewMaker = v.findViewById(R.id.recycler_maker);
        add_database = v.findViewById(R.id.add_database);
        recylerViewChecker = v.findViewById(R.id.recycler_checker);
        recycler_module = v.findViewById(R.id.recycler_module);
        checkerMakerRole = new ArrayList<>();
        maker_choice  = new ArrayList<>();
        checker_choice  = new ArrayList<>();
        module_choice = new ArrayList<>();
        setHasOptionsMenu(true);

        checker_button.setOnClickListener(this);
        maker_button.setOnClickListener(this);
        add_database.setOnClickListener(this);

        maker_values_a = Constant.selectedRolesArrayList.toArray(new String[Constant.selectedRolesArrayList.size()]);
        maker_boolean = new boolean[maker_values_a.length];
        checker_values_a = Constant.selectedRolesArrayList.toArray(new String[Constant.selectedRolesArrayList.size()]);
        checker_boolean = new boolean[checker_values_a.length];


        initModulesSpinner();

        return v;
    }

    private void initModulesSpinner() {

      if(Constant.selectedModulesArrayList.size()!=0){

          name = Constant.selectedModulesArrayList;
          name.add(0,"Select");

          CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),name);
          spinner_module.setAdapter(customSpinnerAdapter);
          spinner_module.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  if(position==0) {


                  }


              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });
      }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.maker_button:
                if (spinner_module.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), "Select Spinner the values", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            builder.setTitle("Select Maker Value : ");


                            builder.setMultiChoiceItems(maker_values_a, maker_boolean, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                                    //maker_boolean=null;
                                    if (isChecked) {
                                        makerItems.add(maker_values_a[position]);
                                    } else {
                                        makerItems.remove(maker_values_a[position]);
                                    }
                                }
                            });
                            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    maker_choice.clear();
                                    for (String maker_name : makerItems) {
                                        maker_choice.add(new Data(maker_name));
                                    }
                                    //makerItems.clear();
                                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
                                    //LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
                                    recyclerViewMaker.setLayoutManager(staggeredGridLayoutManager);
                                    recyclerViewMaker.setHasFixedSize(true);

                                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getActivity(), maker_choice, Constant.MAKER_ADAPTER);
                                    recyclerViewMaker.setAdapter(recyclerAdapter);

                                }
                            });
                            builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create();
                            builder.show();
                            recyclerViewMaker.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();

                        }
                    }, 1000);

                }
                break;

            case R.id.checker_button:

                if (spinner_module.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), "Select Spinner the values", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Select Checker Value : ").setMultiChoiceItems(checker_values_a, checker_boolean, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                                    //checker_boolean=null;
                                    if (isChecked) {
                                        checkerItems.add(checker_values_a[position]);

                                        Log.d("dgshg", "" + checker_values_a);
                                    } else {
                                        checkerItems.remove(checker_values_a[position]);
                                    }
                                }
                            }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    checker_choice.clear();
                                    for (String checker_name : checkerItems) {
                                        checker_choice.add(new Data(checker_name));
                                    }
                                    //checkerItems.clear();
                                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
                                    //LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
                                    recylerViewChecker.setLayoutManager(staggeredGridLayoutManager);
                                    recylerViewChecker.setHasFixedSize(true);

                                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getActivity(), checker_choice, Constant.CHECKER_ADAPTER);
                                    recylerViewChecker.setAdapter(recyclerAdapter);


                                }
                            }).setNegativeButton("", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create();
                            builder.show();
                            recylerViewChecker.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();

                        }
                    }, 1000);


                }
                break;


            case R.id.add_database:
                if (checker_choice.isEmpty()) {
                    Toast.makeText(getContext(), " Select Add Button", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < makerItems.size(); i++) {
                        maker_values = maker_values + makerItems.get(i);
                        if (i != makerItems.size() - 1) {
                            maker_values = maker_values + "\n";
                        }
                    }

                    for (int i = 0; i < checkerItems.size(); i++) {
                        checker_values = checker_values + checkerItems.get(i);
                        if (i != checkerItems.size() - 1) {
                            checker_values = checker_values + "\n";
                        }
                    }


                    spinner = spinner_module.getSelectedItem().toString();
                    /*Data data = new Data(spinner, maker_values, checker_values);
                    module_choice.add(data);*/
                    Data data = new Data(spinner, maker_values, checker_values);

                    Data data1 = new Data(checkerItems, makerItems, spinner);
                    checkerMakerRole.add(data1);
                    module_choice.add(data);


                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recycler_module.setLayoutManager(linearLayoutManager);
                    recycler_module.setHasFixedSize(true);
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getActivity(), module_choice, Constant.CHECKER_MAKER_FRAG);
                    recycler_module.setAdapter(recyclerAdapter);

                    for(int i = 0 ; i<checkerItems.size() ; i++){
                        databaseReference.child("Modules").child(spinner).child("CheckerList").child(""+i).setValue(checkerItems.get(i));
                    }

                    for(int i = 0 ; i<makerItems.size() ; i++){
                        databaseReference.child("Modules").child(spinner).child("MakerList").child(""+i).setValue(makerItems.get(i));

                    }

                    myData = spinner_module.getSelectedItem().toString();
                    name.remove(myData);
                    recyclerAdapter.notifyDataSetChanged();



                    //It will go to first position
                    spinner_module.setSelection(0);
                    recylerViewChecker.setVisibility(View.GONE);
                    recyclerViewMaker.setVisibility(View.GONE);
                    checker_values = "";
                    maker_values = "";
                    spinner = "";
                    checker_choice.clear();
                    maker_choice.clear();

                }
                break;

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.finish:
                Toast.makeText(getActivity(), "Home Screen", Toast.LENGTH_SHORT).show();
                return true;

        }

        return false;
    }
}
