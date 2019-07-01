package com.example.android.jsachallenge;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button fetchButton;
    private EditText userNameEditText;

    private Retrofit mRetrofit;
    private GitHubService mService;
    private List<Repo> mRepos;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepos = new ArrayList<>();

        //binding up the button and the editText from the layout
        fetchButton = findViewById(R.id.fetch_button);
        userNameEditText = findViewById(R.id.user_name);
        recyclerView = findViewById(R.id.recycler_view_repos);

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

        //connecting with layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RepoAdapter(MainActivity.this, mRepos);
        recyclerView.setAdapter(mAdapter);


    }

    protected void fetchRepos(String user) {
        mService.listRepos(user).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repo>> call, @NonNull Response<List<Repo>> response) {

                mRepos = response.body();
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(@NonNull Call<List<Repo>> call, @NonNull Throwable t) {
            }
        });
    }
}
