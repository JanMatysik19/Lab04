package com.example.lab04;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;


public class MainActivity extends Activity {
    private Context context;
    private int duration = Toast.LENGTH_SHORT;

    private Button btnExit;
    private EditText txtColorSelected;
    private TextView txtSpyBox;
    private ConstraintLayout myScreen;
    private final String PREFNAME = "myPrefFile1";
    private String savedColor = "white";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtColorSelected = findViewById(R.id.editText1);
        btnExit = findViewById(R.id.button1);
        txtSpyBox = findViewById(R.id.textView1);
        myScreen = findViewById(R.id.myScreen1);

        btnExit.setOnClickListener(this::exitBtn_click);
        txtColorSelected.addTextChangedListener(new TextChangedHandler());

        context = getApplicationContext();
        Toast.makeText(context, "onCreate", duration).show();
    }

    private void exitBtn_click(View v) {
        finish();
    }

    private class TextChangedHandler implements TextWatcher {
        @Override public void afterTextChanged(Editable s) { }
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String chosenColor = s.toString().toLowerCase(Locale.US);
            txtSpyBox.setText(chosenColor);

            try {
                var color = Color.parseColor(chosenColor);
                myScreen.setBackgroundColor(color);
                savedColor = chosenColor;
            } catch (Exception ignored) { }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(context, "onDestroy", duration).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(context, "onPause", duration).show();

        saveStateData(savedColor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(context, "onStart", duration).show();

        updateMeUsingSavedStateData();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(context, "onRestart", duration).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(context, "onResume", duration).show();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(context, "onStop", duration).show();
    }





    // ///////////////////////////////////////////////////////////////////

    private void saveStateData(String value) {
        SharedPreferences myPrefContainer = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor myPrefEditor = myPrefContainer.edit();

        String key = "chosenBackgroundColor";
        myPrefEditor.putString(key, value);

        myPrefEditor.apply();
    }


    private void updateMeUsingSavedStateData() {
        // (in case it exists) use saved data telling backg color
        SharedPreferences myPrefContainer = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        String key = "chosenBackgroundColor";
        String defaultValue = "white";

        if (( myPrefContainer == null ) ||  !myPrefContainer.contains(key)) return;

        savedColor = myPrefContainer.getString(key, defaultValue);

        try {
            var color = Color.parseColor(savedColor);
            myScreen.setBackgroundColor(color);
            txtSpyBox.setText(savedColor);
        } catch (Exception ignore) { }

    }
}