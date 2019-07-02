/*
 * @author Natalia Dmitrieva
*/

package com.example.android.jsachallenge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/* Class Adapter for creating ViewHolders and filling recycler view */
public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder>{
    private List<Repo> reposList;
    private Context context;

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        RepoViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.name_of_repo);
        }
    }

    RepoAdapter(Context context, List<Repo> reposList) {
        this.reposList = reposList;
        this.context = context;
    }

    // Construct ViewHolders and set the view it uses to display its contents
    @NonNull
    @Override
    public RepoAdapter.RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_name, parent, false);

        return new RepoViewHolder(v);
    }

    //The layout manager binds the view holder to its data
    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.textView.setText(reposList.get(position).getName());
        final String html_url = reposList.get(position).getHtml_url();

        //Take the user to the webpage of the repo
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(html_url));
                context.startActivity(browser);
            }
        });
    }

    // return the size of repo list
    @Override
    public int getItemCount() {
        try {
            return reposList.size();
        }
        catch (NullPointerException e) {
            Toast.makeText(context, "There is no repos. Try another name", Toast.LENGTH_LONG).show();
        }
        return 0;
    }
}
