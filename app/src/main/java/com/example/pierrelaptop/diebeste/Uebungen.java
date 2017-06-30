package com.example.pierrelaptop.diebeste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import programm.Orchestrator;

public class Uebungen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebungen);
        Orchestrator orchestrator = Orchestrator.getOrchestrator();
        orchestrator.setUebungen(this);
        orchestrator.displayNextQuestion();


    }

    public void checkQuestion(View view){
        Orchestrator orchestrator = Orchestrator.getOrchestrator();
        orchestrator.setUebungen(this);
        orchestrator.displayNextQuestion();
    }

    public void setQuestion(String question){
        WebView questionView = (WebView) findViewById(R.id.questionWebView);
        questionView.getSettings().setJavaScriptEnabled(true);
        questionView.loadDataWithBaseURL("", question, "text/html", "UTF-8", "");
        EditText answerInput = (EditText) findViewById(R.id.answerInput);
        answerInput.setText("");
    }



}
