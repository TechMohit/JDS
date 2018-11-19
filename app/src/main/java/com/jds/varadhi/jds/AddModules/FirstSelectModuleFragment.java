package com.jds.varadhi.jds.AddModules;


import android.app.ProgressDialog;
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
import android.widget.ImageView;

import com.jds.varadhi.jds.Constant;
import com.jds.varadhi.jds.R;
import com.jds.varadhi.jds.RecyclerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstSelectModuleFragment extends Fragment implements View.OnClickListener {


    private RecyclerView rv_Selectmodules;

    private ArrayList<SelectModuleModel> modulesName;

    private RecyclerAdapter selectModuleAdapterl;

    private Button btnSave_SelectedModule,btn_SelectAll;

    //DatabaseReference mFirebaseSelectedModuleRef;

    ImageView iv_back_btn, iv_downLoadModuleList;

    private ProgressDialog progressDialog = null;

    public FirstSelectModuleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_first_select_module, container, false);
        intializeViews(v);
        intializeListners();
        return v;
    }

    private void intializeViews(View v) {
        //mFirebaseSelectedModuleRef   = FirebaseDatabase.getInstance().getReference("School/SchoolId/ModulesList");
        btn_SelectAll                = v.findViewById(R.id.btnSelectAll);
        rv_Selectmodules             = v.findViewById(R.id.rv_SelectModules);
        btnSave_SelectedModule       = v.findViewById(R.id.btnSaveSelectedModule);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        rv_Selectmodules.setLayoutManager(linearLayoutManager);
        rv_Selectmodules.setHasFixedSize(true);
        modulesName = new ArrayList<>();
        showModuleList();

      //  selectModuleAdapterl = new RecyclerAdapter(modulesName, getActivity(), btn_SelectAll, btnSave_SelectedModule, Constant.FIRST_ADD_MODULES_FRAGMENT);
      //  rv_Selectmodules.setAdapter(selectModuleAdapterl);

    }

    private void showModuleList() {

        if (modulesName.size() > 0) {
            modulesName.clear();
        }

        String arr[] = getActivity().getResources().getStringArray(R.array.ModuleNames);
        for (String anArr : arr) {
            modulesName.add(new SelectModuleModel(anArr, false));
        }
        selectModuleAdapterl = new RecyclerAdapter(modulesName, getActivity(), btn_SelectAll, btnSave_SelectedModule, Constant.FIRST_ADD_MODULES_FRAGMENT);
        rv_Selectmodules.setAdapter(selectModuleAdapterl);
        Log.d("moduleSizeA", "" + modulesName.size());
       // progressDialog.dismiss();

    }

    private void intializeListners() {
        btnSave_SelectedModule.setOnClickListener(this);
        btn_SelectAll.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
