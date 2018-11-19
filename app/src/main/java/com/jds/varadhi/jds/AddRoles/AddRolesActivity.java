package com.jds.varadhi.jds.AddRoles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jds.varadhi.jds.R;

public class AddRolesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roles);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.addRolesContainer, new RolesFragment()).commit();

    }


    public void loadFragment(String rolesFrag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.addRolesContainer, new AddCheckerMakerFragment()).commit();
    }
}
