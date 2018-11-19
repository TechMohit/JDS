package com.jds.varadhi.jds.AddModules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jds.varadhi.jds.R;

public class SelectModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_module);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerSelectModules, new FirstSelectModuleFragment()).commit();
    }
}
