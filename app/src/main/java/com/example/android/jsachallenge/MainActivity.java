package com.example.android.jsachallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button fetchButton;
    EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Git User Repo List");

        //binding up the button and the editText from the layout
        fetchButton = (Button) findViewById(R.id.fetchButton);
        userNameEditText = (EditText) findViewById(R.id.userName);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString();
                if (!userName.equals("")) {
                    fetchRepos(userName);
                }
            }
        });
    }

    protected void fetchRepos(String user) {
    }
}
