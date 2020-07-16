package com.pinchtozoom.android.blockgame.MainApp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pinchtozoom.android.blockgame.Objects.Level;
import com.pinchtozoom.android.blockgame.R;

public class PauseDialog extends AlertDialog {

    private TextView goldScoreTextView, silverScoreTextView, bronzeScoreTextView;
    private Button resumeButton, restartButton, returnButton;

    PauseDialog( Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pause);

        goldScoreTextView = findViewById(R.id.gold_score);
        silverScoreTextView = findViewById(R.id.silver_score);
        bronzeScoreTextView = findViewById(R.id.bronze_score);

        resumeButton = findViewById(R.id.button_resume);
        restartButton = findViewById(R.id.button_restart);
        returnButton = findViewById(R.id.button_return_to_title);

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    void populateScores(Level level) {
        Brain.populateScores(level, goldScoreTextView, silverScoreTextView, bronzeScoreTextView, null);
    }

    void setRestartedClickListener(View.OnClickListener onClickListener) {
        restartButton.setOnClickListener(onClickListener);
    }

    void setReturnClickListener(View.OnClickListener onClickListener) {
        returnButton.setOnClickListener(onClickListener);
    }
}