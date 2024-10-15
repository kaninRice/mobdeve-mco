package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.materialswitch.MaterialSwitch;

public class SettingsActivity extends AppCompatActivity {
    MaterialSwitch enableArpeggeo;
    MaterialSwitch enableSandbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activityLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingStart(), systemBars.top + v.getPaddingTop(), v.getPaddingEnd(), v.getPaddingBottom());
            return insets;
            }
        );

        initializeActivity();
    }

    public void initializeActivity() {
        enableArpeggeo = findViewById(R.id.switchEnableArpeggeo);
        enableSandbox = findViewById(R.id.switchEnableSandbox);
    }

    public void closeActivity(View v) {
        finish();
    }
}