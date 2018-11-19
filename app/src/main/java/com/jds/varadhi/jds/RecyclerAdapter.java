package com.jds.varadhi.jds;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jds.varadhi.jds.AddModules.SelectModuleModel;
import com.jds.varadhi.jds.AddRoles.AddRolesActivity;
import com.jds.varadhi.jds.AddRoles.Data;

import java.util.ArrayList;


/**
 * Created by varadhi10 on 8/5/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    ArrayList<Data> dataArrayList;
    public ArrayList<SelectModuleModel> arrayList;
    private ArrayList<String> checkedArrayList;
    private ArrayList<String> uncheckedArrayList;
    private Button btn_SelectAll;
    private Button btnSave_selectedModule;
    Context mContext;
    Data data;
    String tag;
    DatabaseReference databaseReference;
    public RecyclerAdapter(Context mContext, ArrayList<Data> dataArrayList, String tag) {
        this.mContext =mContext;
        this.dataArrayList = dataArrayList;
        this.tag = tag;
    }

    public RecyclerAdapter(ArrayList<SelectModuleModel> arrayList, Context mContext, Button btn_SelectAll, Button btnSave_selectedModule, String tag) {

        this.arrayList = arrayList;
        this.mContext = mContext;
        checkedArrayList = new ArrayList<>();
        this.btn_SelectAll =btn_SelectAll;
        this.btnSave_selectedModule = btnSave_selectedModule;
        uncheckedArrayList = new ArrayList<>();
        this.tag = tag;
        databaseReference = FirebaseDatabase.getInstance().getReference("Management_Data/Selected_Module");


    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        switch (tag){

            case Constant.ROLES_FRAG:
                return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_card, parent, false));


            case Constant.FIRST_ADD_MODULES_FRAGMENT:
                return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_card, parent, false));


            case Constant.MAKER_ADAPTER:
                return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chip_layout, parent, false));

            case Constant.CHECKER_ADAPTER:
                return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chip_layout, parent, false));



            case Constant.CHECKER_MAKER_FRAG:
                return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_module_checker_maker, parent, false));

        }
        return null;
    }



    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position)
    {
       //This is for if you the view it will change the color

        switch (tag){

            case Constant.ROLES_FRAG:

                holder.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        dataArrayList.get(position).setSelect(isChecked);
                        holder.check_box.setChecked(isChecked);
                        if(isChecked)
                        {
                            holder.check_box.setBackgroundColor(Color.parseColor("#FF63DC67"));
                            holder.check_box.setTextColor(Color.WHITE);
                        }
                        else {

                            holder.check_box.setBackgroundColor(Color.WHITE);
                            holder.check_box.setTextColor(Color.BLACK);
                        }
                    }
                });

                //This is for Whatever click the values that are Selectable

                data= dataArrayList.get(position);
                holder.check_box.setText(data.getName());
                holder.check_box.setChecked(data.isSelect());
                if(data.isSelect())
                {
                    holder.check_box.setBackgroundColor(Color.parseColor("#FF63DC67"));
                    holder.check_box.setTextColor(Color.WHITE);
                    Log.d("Added", "onBindViewHolder: ");
                }
                else
                {
                    holder.check_box.setBackgroundColor(Color.WHITE);
                    holder.check_box.setTextColor(Color.BLACK);
                }
                break;



            case Constant.FIRST_ADD_MODULES_FRAGMENT:

                final SelectModuleModel adapterPojoClass = arrayList.get(position);

                holder.check_box.setText(adapterPojoClass.getModuleNAme());
                boolean flag = adapterPojoClass.isModuleSelected();

                if (flag)
                {
                    holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                    holder.check_box.setTextColor(Color.WHITE);
                    checkedArrayList.add(arrayList.get(position).getModuleNAme());
                    Log.d("AaCheckedArrayList", ""+checkedArrayList.size());

                } else {
                    holder.check_box.setBackgroundColor(Color.WHITE);
                    holder.check_box.setTextColor(Color.BLACK);
                    checkedArrayList.remove(arrayList.get(position).getModuleNAme());
                    Log.d("AaCheckedArrayList1", ""+checkedArrayList.size());



                }

                holder.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(!adapterPojoClass.isModuleSelected())
                        {
                            checkedArrayList.add(holder.check_box.getText().toString());
                            adapterPojoClass.setModuleSelected(true);

                            Log.d("Checked", ""+checkedArrayList.size());
                            holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                            holder.check_box.setTextColor(Color.WHITE);
                        }
                        else {
                            checkedArrayList.remove(holder.check_box.getText().toString());
                            adapterPojoClass.setModuleSelected(false);
                            Log.d("Unchecked", ""+checkedArrayList.size());
                            holder.check_box.setBackgroundColor(Color.WHITE);
                            holder.check_box.setTextColor(Color.BLACK);
                        }

                        Log.d("CheckedItem", ""+checkedArrayList.size());





                    }
                });







                /*btn_SelectAll.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View view) {

                        String status = btn_SelectAll.getText().toString().trim();
                        if (status.equalsIgnoreCase("Select All"))
                        {

                            if(uncheckedArrayList.size()>0){

                                uncheckedArrayList.clear();

                            }
                            btn_SelectAll.setText("Unselect All");
                            btn_SelectAll.setBackground(mContext.getResources().getDrawable(R.drawable.btn_round_shape_green));
                            btn_SelectAll.setTextColor(Color.WHITE);

                            if (checkedArrayList.size()>0)
                                checkedArrayList.clear();

                            for (int i = 0; i < arrayList.size(); i++)
                            {
                                arrayList.get(i).setModuleSelected(true);
                                checkedArrayList.add(arrayList.get(i).getModuleNAme());
                                Log.d("asdasd", ""+checkedArrayList.size());

                            }


                            notifyDataSetChanged();

                        }

                        else if (status.equalsIgnoreCase("Unselect All"))
                        {

                            btn_SelectAll.setText("Select All");
                            btn_SelectAll.setBackground(mContext.getResources().getDrawable(R.drawable.btn_round_shape_grey));
                            // btn_select.setBackgroundColor(R.drawable.draw2);
                            btn_SelectAll.setTextColor(mContext.getResources().getColor(R.color.colorBlack));

                            for (int i = 0; i < arrayList.size(); i++)
                            {

                                arrayList.get(i).setModuleSelected(false);

                            }

                            if (checkedArrayList.size()>0)
                                checkedArrayList.clear();


                            notifyDataSetChanged();


                        }

                    }
                });
*/

                btnSave_selectedModule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Constant.selectedModulesArrayList = new ArrayList<>();
                        Constant.selectedModulesArrayList = checkedArrayList;
                        for(int i = 0 ; i<checkedArrayList.size(); i++){
                            Log.d("CheckedItem", ""+checkedArrayList.get(i));
                            databaseReference.child(""+i).setValue(checkedArrayList.get(i));

                        }
                        //progressDialog.dismiss();
                        Intent intent = new Intent(mContext, AddRolesActivity.class);
                        mContext.startActivity(intent);
                    }


                });

                break;


            case Constant.MAKER_ADAPTER:

                final Data data1=dataArrayList.get(position);
                holder.chip_txt.setText(data1.getChecker_name());
                holder.close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataArrayList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                break;


            case Constant.CHECKER_ADAPTER:

                final Data data=dataArrayList.get(position);
                holder.chip_txt.setText(data.getChecker_name());
                holder.close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataArrayList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                break;


            case Constant.CHECKER_MAKER_FRAG:
                final Data data2=dataArrayList.get(position);
                holder.module.setText(data2.getSpin());
                holder.checker.setText(data2.getMake());
                holder.maker.setText(data2.getCheck());

                break;



        }


    }



    @Override
    public int getItemCount()
    {

        switch (tag){

            case Constant.ROLES_FRAG:
                return dataArrayList.size();

            case Constant.FIRST_ADD_MODULES_FRAGMENT:
                return arrayList.size();

            case Constant.MAKER_ADAPTER:
                return dataArrayList.size();


            case Constant.CHECKER_ADAPTER:
                return dataArrayList.size();

            case Constant.CHECKER_MAKER_FRAG:
                return dataArrayList.size();



        }
        return 0;

    }


    public void newValues( String s)
    {
        dataArrayList.add(new Data(s, true));
        notifyDataSetChanged();
    }

}





