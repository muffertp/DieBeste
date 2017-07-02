package com.example.pierrelaptop.diebeste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import programm.Orchestrator;

public class Uebungen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebungen);
        Orchestrator orchestrator = Orchestrator.getOrchestrator();
        orchestrator.setUebungen(this);
        orchestrator.displayNextQuestion();
        WebView webView = (WebView) findViewById(R.id.questionWebView);
        //Einstellen das der WebView bei downloadproblemen angemessen reagiert.
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                Orchestrator.getOrchestrator().goHome();
            }
        });
    }
    //kontrollieren der gegebenen Antwort.
    public void checkQuestion(View view){
        EditText userInput = (EditText) findViewById(R.id.answerInput);
        Orchestrator orchestrator = Orchestrator.getOrchestrator();
        orchestrator.checkUserAnswer(userInput.getText().toString());
    }
    //anszeigen der Frage im webView
    public void setQuestion(String question){

        try
        {
            WebView questionView = (WebView) findViewById(R.id.questionWebView);
            questionView.getSettings().setJavaScriptEnabled(true);
            questionView.loadDataWithBaseURL("", question, "text/html", "UTF-8", "");
            EditText answerInput = (EditText) findViewById(R.id.answerInput);
            answerInput.setText("");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }



}
