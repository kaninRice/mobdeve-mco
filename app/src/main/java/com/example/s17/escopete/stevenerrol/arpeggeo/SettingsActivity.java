package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.materialswitch.MaterialSwitch;

public class SettingsActivity extends AppCompatActivity {
    private MaterialSwitch enableArpeggeo;
    private MaterialSwitch enableSandbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /* adjust padding based on top system bars */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_layout), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(v.getPaddingStart(), systemBars.top, v.getPaddingEnd(), v.getPaddingBottom());
                return insets;
            }
        );

        initializeActivity();
    }

    public void initializeActivity() {
        enableArpeggeo = findViewById(R.id.switch_enable_arpeggeo);
        enableSandbox = findViewById(R.id.switch_enable_sandbox);
    }

    public void closeActivity(View v) {
        finish();
    }
}