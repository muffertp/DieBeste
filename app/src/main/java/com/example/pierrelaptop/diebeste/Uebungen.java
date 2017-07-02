package com.example.pierrelaptop.diebeste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                System.out.println("FEHLER WEBVIEW request:: "+request+" ERROR:: "+error);
                Orchestrator.getOrchestrator().goHome();
            }
        });


    }

    public void checkQuestion(View view){
        EditText userInput = (EditText) findViewById(R.id.answerInput);
        Orchestrator orchestrator = Orchestrator.getOrchestrator();
        orchestrator.checkUserAnswer(userInput.getText().toString());
    }



    public void setQuestion(String question){

        try
        {
            WebView questionView = (WebView) findViewById(R.id.questionWebView);
            questionView.getSettings().setJavaScriptEnabled(true);
            questionView.loadDataWithBaseURL("", question, "text/html", "UTF-8", "");
            EditText answerInput = (EditText) findViewById(R.id.answerInput);
            answerInput.setText("");
            //Intent intentUrl = new Intent(Intent.ACTION_VIEW);
            //intentUrl.setDataAndType(url, "application/pdf");
            //intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //myActivity.startActivity(intentUrl);
        }
        catch (Exception e)
        {
            System.out.println("FEHLER ANZEIGE WEBVIEW");
            System.out.println(e);
            Toast.makeText(this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();
        }


    }



}
