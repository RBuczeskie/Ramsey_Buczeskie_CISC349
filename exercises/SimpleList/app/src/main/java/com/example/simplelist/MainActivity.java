package com.example.simplelist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    ListView listView;
    TextView textView;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<User> arrayOfUsers = new ArrayList<>();
        arrayOfUsers.add(new User("Eve", "777-777-7771"));
        arrayOfUsers.add(new User("John", "777-777-7772"));
        arrayOfUsers.add(new User("Mark", "777-777-7773"));
        arrayOfUsers.add(new User("Michael", "777-777-7774"));
        arrayOfUsers.add(new User("Adam", "777-777-7775"));
        arrayOfUsers.add(new User("Mary", "777-777-7776"));
        arrayOfUsers.add(new User("Olivia", "777-777-7777"));


        // Create the adapter to convert the array to views
        UserAdapter adapter = new UserAdapter(this, arrayOfUsers);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.my_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView phone = (TextView) view.findViewById(R.id.phone);
                String value = (String) phone.getText();
                Log.d(TAG, "Selected " + value);
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
            }
        });
    }
}