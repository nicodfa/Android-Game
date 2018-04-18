package com.nico_informatica.scaperun.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.nico_informatica.scaperun.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = (ImageButton) findViewById(R.id.btPlay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(MainActivity.this,GameActivity.class);
                startActivity(game);
            }
        });
    }
}
