package com.jds.varadhi.jds.AddMember;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jds.varadhi.jds.R;

public class AddMemberFragment extends Fragment {



    public AddMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_member, container, false);
        return v;
        
    }

}
