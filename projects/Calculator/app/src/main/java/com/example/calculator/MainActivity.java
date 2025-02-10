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

    // variables
    int decimal = 0;
    //track decimal places
    boolean negative = false;
    Button active_operation = null;

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
                if(negative == false){
                    negative_button.setBackgroundColor(getColor(R.color.gold));
                    negative = true;
                }else if(negative == true){
                    negative_button.setBackgroundColor(getColor(R.color.default_purple));
                    negative = false;
                }
            }
        });

        // decimal button
        Button decimal_button = findViewById(R.id.decimal);
        decimal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(decimal == 0){
                    decimal_button.setBackgroundColor(getColor(R.color.gold));
                    decimal = 1;
                }
            }
        });

        // addition button
        Button add_button = findViewById(R.id.add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolve_operation(add_button);
            }
        });

        // subtract button
        Button sub_button = findViewById(R.id.subtract);
        sub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolve_operation(sub_button);
            }
        });

        // multiply button
        Button mult_button = findViewById(R.id.multiply);
        mult_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolve_operation(mult_button);
            }
        });

        // divide button
        Button div_button = findViewById(R.id.divide);
        div_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolve_operation(div_button);
            }
        });

        // equal button
        Button equal_button = findViewById(R.id.execute);
        equal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execute();
            }
        });
        equal_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView screen_upper = findViewById(R.id.screen_upper);
                TextView screen_lower = findViewById(R.id.screen_lower);
                screen_upper.setText("0.0");
                screen_lower.setText("0.0");
                if(active_operation != null) {
                    active_operation.setBackgroundColor(getColor(R.color.default_purple));
                }
                active_operation = null;
                decimal_button.setBackgroundColor(getColor(R.color.default_purple));
                decimal = 0;
                negative_button.setBackgroundColor(getColor(R.color.default_purple));
                negative = false;
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
                TextView screen;
                if(active_operation == null) {
                    screen = findViewById(R.id.screen_upper);
                }else{
                    screen = findViewById(R.id.screen_lower);
                }

                float val = Float.parseFloat(screen.getText().toString());
                boolean neg = false;
                if(val < 0){
                    val = val * -1;
                    neg = true;
                }

                if(decimal == 0){
                    val = val * 10 + Float.parseFloat(num);
                }else{
                    val = val + Float.parseFloat(num)/(float)(Math.pow(10, decimal));
                    decimal++;
                }

                if(negative){
                    val = val * -1;
                }else if(neg){
                    val = val * -1;
                }

                screen.setText(String.valueOf(val));
            }
        });
        layout.addView(newButton);
    }

    private void resolve_operation(Button operation){
        execute();
        change_active_operation(operation);
    }

    private void change_active_operation(Button new_operation){
        if(active_operation != null){
            active_operation.setBackgroundColor(getColor(R.color.default_purple));
        }
        active_operation = new_operation;
        if(active_operation != null) {
            active_operation.setBackgroundColor(getColor(R.color.gold));
        }
    }

    private void execute(){
        TextView screen_upper = findViewById(R.id.screen_upper);
        TextView screen_lower = findViewById(R.id.screen_lower);
        float x = Float.parseFloat(screen_upper.getText().toString());
        float y = Float.parseFloat(screen_lower.getText().toString());

        if (active_operation == findViewById(R.id.add)) {
            screen_upper.setText(String.valueOf(x+y));
        } else if (active_operation == findViewById(R.id.subtract)) {
            screen_upper.setText(String.valueOf(x-y));
        } else if (active_operation == findViewById(R.id.multiply)) {
            screen_upper.setText(String.valueOf(x*y));
        } else if (active_operation == findViewById(R.id.divide)) {
            if(y != 0) {
                screen_upper.setText(String.valueOf(x/y));
            } else{
                Toast.makeText(getApplicationContext(), "ERROR: CANNOT DIVIDE BY 0", Toast.LENGTH_SHORT).show();
            }
        }
        screen_lower.setText("0");

        Button decimal_button = findViewById(R.id.decimal);
        decimal_button.setBackgroundColor(getColor(R.color.default_purple));
        decimal = 0;
        change_active_operation(null);
    }
}