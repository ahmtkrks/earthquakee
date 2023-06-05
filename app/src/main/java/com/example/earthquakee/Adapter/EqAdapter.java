package com.example.earthquakee.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earthquakee.R;
import com.example.earthquakee.Model.Users;

import java.util.ArrayList;
import java.util.List;

public class EqAdapter extends RecyclerView.Adapter<EqAdapter.CardViewItemHolder> {
    private Context mContext;
    private Users mUser;
    private List<Users> mUsersList;

    public EqAdapter(Context mContext, ArrayList<Users> mUsersList) {
        this.mContext = mContext;
        this.mUsersList = mUsersList;
    }

    public class CardViewItemHolder extends RecyclerView.ViewHolder {

        public ImageView imageUser;

        public TextView textUserName;
        public Button buttonInformation;


        public CardViewItemHolder(@NonNull View itemView) {
            super(itemView);
            textUserName = itemView.findViewById(R.id.textUserName);
            imageUser = itemView.findViewById(R.id.imageUser);
            buttonInformation = itemView.findViewById(R.id.buttonInformation);
        }
    }

    @NonNull
    @Override
    public CardViewItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_users, parent, false);
        return new CardViewItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewItemHolder holder, int position) {

        mUser = mUsersList.get(position);

        holder.textUserName.setText(mUser.getName());

        holder.buttonInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mUsersList.get(position).getUid(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }


}
