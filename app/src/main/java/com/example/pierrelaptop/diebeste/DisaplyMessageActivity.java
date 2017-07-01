package com.example.pierrelaptop.diebeste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import programm.Orchestrator;

public class DisaplyMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaply_message);
        Orchestrator orchestrator = Orchestrator.getOrchestrator();
        orchestrator.setDisaplyMessageActivity(this);
        setButton();
        /*
        EditText editTextUserInput = (EditText) findViewById(R.id.userInput);
        */
    }

    public void setButton(){
        ArrayList<Button> buttonArrayList = new ArrayList<Button>();
        buttonArrayList.add((Button) findViewById(R.id.MathButton1));
        buttonArrayList.add((Button) findViewById(R.id.MathButton2));
        buttonArrayList.add((Button) findViewById(R.id.MathButton3));
        buttonArrayList.add((Button) findViewById(R.id.MathButton4));
        Orchestrator orchestrator = Orchestrator.getOrchestrator();
        for (int i = 0;i<orchestrator.getBlockList().size();i++){
            buttonArrayList.get(i).setVisibility(View.VISIBLE);
            buttonArrayList.get(i).setText(orchestrator.getBlockList().get(i).getBlockName());

        }
        for (int j = orchestrator.getBlockList().size();j<buttonArrayList.size();j++){
            buttonArrayList.get(j).setVisibility(View.INVISIBLE);
            buttonArrayList.get(j).setText("");
        }
    }

    public void sendMessage2(View view) {
        System.out.println("TAG :: "+view.getTag().toString());
        Orchestrator orchestrator = Orchestrator.getOrchestrator();
        orchestrator.setCurrentBlock(Integer.parseInt(view.getTag().toString()));
        Intent intent2 = new Intent(this, Uebungen.class);
        startActivity(intent2);
    }
}
