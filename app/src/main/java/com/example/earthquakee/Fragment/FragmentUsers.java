package com.example.earthquakee.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.earthquakee.Adapter.EqAdapter;
import com.example.earthquakee.API.ApiClient;
import com.example.earthquakee.Model.Users;
import com.example.earthquakee.R;
import com.example.earthquakee.databinding.FragmentUsersBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentUsers extends Fragment {


    private RecyclerView rv;

    private Users mModelUSer;
    private ArrayList<Users> mUsersList;
    private EqAdapter eqAdapter;
    private FirebaseUser mUser;

    private FirebaseFirestore mFirestore;

    private Query mQuery;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mFirestore = FirebaseFirestore.getInstance();

        mUsersList = new ArrayList<>();


        rv = rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mQuery = mFirestore.collection("Users");


        mQuery.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (value != null) {
                mUsersList.clear();

                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    mModelUSer = snapshot.toObject(Users.class);

                    assert mModelUSer != null;
                    if (!mModelUSer.getUid().equals(mUser.getUid())) {
                        mUsersList.add(mModelUSer);
                    }

                    eqAdapter = new EqAdapter(getActivity(), mUsersList);
                    rv.setAdapter(eqAdapter);
                }

            }

        });

        return rootView;

    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("token:", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        String token = task.getResult();

                        try {
                            ApiClient.saveToken(token);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        Log.e("tokennnn", token);
                    }
                });
    }
}