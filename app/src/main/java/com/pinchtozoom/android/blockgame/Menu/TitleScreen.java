package com.pinchtozoom.android.blockgame.Menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.pinchtozoom.android.blockgame.Library.OpenGLRenderer;
import com.pinchtozoom.android.blockgame.MainActivity;
import com.pinchtozoom.android.blockgame.R;

public class TitleScreen extends AppCompatActivity {

    Button continueButton, levelSelectButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GLSurfaceView view = findViewById(R.id.cube);
        view.setRenderer(new OpenGLRenderer());

        continueButton = findViewById(R.id.continue_button);
        levelSelectButton = findViewById(R.id.level_select_button);

        levelSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TitleScreen.this, LevelSelect.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final int currentLevelID = sharedPref.getInt(getString(R.string.preference_file_key_level_ID), 1);

        if (currentLevelID != 1) {
            continueButton.setText("Continue");
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TitleScreen.this, MainActivity.class).putExtra("level", currentLevelID));
            }
        });

    }
}