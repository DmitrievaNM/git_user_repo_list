package com.example.android.jsachallenge;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button fetchButton;
    EditText userNameEditText;
    TextView textView;

    Retrofit mRetrofit;
    GitHubService mService;
    List<Repo> mRepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding up the button and the editText from the layout
        fetchButton = findViewById(R.id.fetchButton);
        userNameEditText = findViewById(R.id.userName);
        textView = findViewById(R.id.textView);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString();
                if (!userName.equals("")) {
                    fetchRepos(userName);
                }
            }
        });

        //instance of Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        //implementation of the GitHubService interface
        mService = mRetrofit.create(GitHubService.class);
    }

    protected void fetchRepos(String user) {
        mService.listRepos(user).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repo>> call, @NonNull Response<List<Repo>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.code());
                }

                mRepos = response.body();

                assert mRepos != null;
                for (Repo repo : mRepos) {
                    String content = "";
                    content += "Name: " + repo.getName() + "\n";
                    content += "Description: " + repo.getDescription() + "\n";
                    content += "HTML URL: : " + repo.getHtml_url() + "\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Repo>> call, @NonNull Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}
