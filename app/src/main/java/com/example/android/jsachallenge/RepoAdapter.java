package com.example.android.jsachallenge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder>{
    private List<Repo> reposList;
    private Context context;
    private LayoutInflater inflater;

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        RepoViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    RepoAdapter(Context context, List<Repo> reposList) {
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.reposList = reposList;
    }

    @NonNull
    @Override
    public RepoAdapter.RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_name, parent, false);

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
