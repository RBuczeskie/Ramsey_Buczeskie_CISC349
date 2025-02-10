package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // loop to create button for each digit
        for (int i = 0; i < 10; i++){
            addNumButton(String.valueOf(i));
        }

        // negative button
        Button negative_button = findViewById(R.id.negative);
        negative_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView screen = findViewById(R.id.screen_lower);
                screen.setText(screen.getText()+"n");
            }
        });

        // decimal button
        Button decimal_button = findViewById(R.id.decimal);
        decimal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView screen = findViewById(R.id.screen_lower);
                screen.setText(screen.getText()+".");
            }
        });

        // addition button
        Button add_button = findViewById(R.id.add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView screen = findViewById(R.id.screen_lower);
                screen.setText(screen.getText()+"a");
            }
        });

        // subtract button
        Button sub_button = findViewById(R.id.subtract);
        sub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView screen = findViewById(R.id.screen_lower);
                screen.setText(screen.getText()+"s");
            }
        });

        // multiply button
        Button mult_button = findViewById(R.id.multiply);
        mult_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView screen = findViewById(R.id.screen_lower);
                screen.setText(screen.getText()+"m");
            }
        });

        // divide button
        Button div_button = findViewById(R.id.divide);
        div_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView screen = findViewById(R.id.screen_lower);
                screen.setText(screen.getText()+"d");
            }
        });

        // equal button
        Button equal_button = findViewById(R.id.execute);
        equal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView screen = findViewById(R.id.screen_lower);
                screen.setText(screen.getText()+"e");
            }
        });
        equal_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView screen_upper = findViewById(R.id.screen_upper);
                TextView screen_lower = findViewById(R.id.screen_lower);
                screen_upper.setText("");
                screen_lower.setText("");
                Toast.makeText(getApplicationContext(), "Screen Cleared", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void addNumButton(String num){
        // function to create buttons for each digit
        GridLayout layout = findViewById(R.id.num_layout);
        Button newButton = new Button(this);
        newButton.setText(num);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView screen = findViewById(R.id.screen_lower);
                screen.setText(screen.getText()+num);
                // buttons write new digits to screen via string concatenation
            }
        });
        layout.addView(newButton);
    }
}