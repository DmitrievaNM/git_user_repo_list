/*
 * @author Natalia Dmitrieva
 */
package com.example.android.jsachallenge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText userNameEditText;

    private GitHubService mService;
    private List<Repo> mRepos;
    private Toast toastFailure;
    private Context context = this;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisation of list of repos
        mRepos = new ArrayList<>();

        //toast for error message
        toastFailure = Toast.makeText(getApplicationContext(), "There is an error accessing the service", Toast.LENGTH_LONG);

        //binding up the button and the editText from the layout
        Button fetchButton = findViewById(R.id.fetch_button);
        userNameEditText = findViewById(R.id.user_name);
        recyclerView = findViewById(R.id.recycler_view_repos);

        //listener for Fetch Repos button
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString();
                //checking if there is no name
                if (!userName.equals("")) {
                    fetchRepos(userName);
                }
            }
        });

        //instance of Retrofit
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        //implementation of the GitHubService interface
        mService = mRetrofit.create(GitHubService.class);

        //connecting with layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RepoAdapter(context, mRepos);
        recyclerView.setAdapter(mAdapter);

    }

    //getting response from GitHub with list of repos
    protected void fetchRepos(String user) {
        mService.listRepos(user).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repo>> call, @NonNull Response<List<Repo>> response) {
                if (!response.isSuccessful()) {
                    toastFailure.show();
                }
                //getting list of repos
                mRepos = response.body();

                //updating adapter
                mAdapter = new RepoAdapter(context, mRepos);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Repo>> call, @NonNull Throwable t) {
                //error message in a toast shows when there is a problem accessing the service
                toastFailure.show();
            }
        });
    }
}
