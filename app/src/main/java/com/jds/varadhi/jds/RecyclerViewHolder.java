package com.jds.varadhi.jds;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jds.varadhi.jds.R;


/**
 * Created by varadhi on 6/10/18.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    CheckBox check_box;

    //----------------------------Checker&Maker Adpater--------
    TextView chip_txt;
    ImageView close;
    LinearLayout linear;
    TextView module,checker,maker;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        check_box=itemView.findViewById(R.id.check_box);

        //---------------------------Checker&Maker-----------------

        chip_txt=itemView.findViewById(R.id.chip_txt);
        close=itemView.findViewById(R.id.close);
        linear=itemView.findViewById(R.id.linear);

        module=itemView.findViewById(R.id.module);
        checker=itemView.findViewById(R.id.checker);
        maker=itemView.findViewById(R.id.maker);
    }
}
