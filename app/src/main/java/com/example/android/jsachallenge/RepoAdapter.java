package com.example.android.jsachallenge;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder>{
    private List<Repo> reposList;

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RepoViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.name_of_repo);
        }
    }

    public RepoAdapter(List<Repo> reposList) {
        this.reposList = reposList;
    }

    @NonNull
    @Override
    public RepoAdapter.RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_name, parent, false);

        return new RepoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.textView.setText(reposList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }
}
