package com.example.pierrelaptop.diebeste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import programm.Lib;
import programm.Loader;
import programm.Orchestrator;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Orchestrator orchestrator = Orchestrator.getOrchestrator();
        orchestrator.setMainActivity(this);
    }

    //login und senden der Logindaten an den Server
    public void login(View view) {

        Intent intent = new Intent(this, DisaplyMessageActivity.class);
        EditText editTextUserInput = (EditText) findViewById(R.id.userInput);
        EditText editTextPassword = (EditText) findViewById(R.id.userPassword);
        String userName = editTextUserInput.getText().toString();
        String userPassword = editTextPassword.getText().toString();

        Loader loader = new Loader(this);
        loader.execute("login", Lib.baseURL+"userData?email="+userName+"&password="+userPassword);

    }
    //wenn login erfolgreich
    public void loginSucsess(){
        Intent intent = new Intent(this, DisaplyMessageActivity.class);
        startActivity(intent);
    }
    //wenn login fehlgeschlagen
    public void loginFail(){
        Toast.makeText(this, "Login Fehlgeschlagen",
                Toast.LENGTH_LONG).show();
    }
}