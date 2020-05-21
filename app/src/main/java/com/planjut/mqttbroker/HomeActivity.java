package com.planjut.mqttbroker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Spinner spnTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        spnTopic = findViewById(R.id.spnTopic);

        List<String> topics = new ArrayList<>();
        topics.add(0, "Choose Topic");
        topics.add(1, "Topic 1");
        topics.add(2, "Topic 2");
        topics.add(3, "Topic 3");

        // Style the spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter =  new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, topics);

        // Dropdown layout style
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // Attaching data to spinner
        spnTopic.setAdapter(dataAdapter);
        spnTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Topic")){
                    // Do nothing
                }else{
                    // On selecting a spinner item
                    String item = parent.getItemAtPosition(position).toString();

                    // Show selected spinner item
                    Toast.makeText(parent.getContext(), "Selected: "+item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}
