package com.example.earthquakee.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.earthquakee.Adapter.EqAdapter;
import com.example.earthquakee.Model.Users;
import com.example.earthquakee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.List;

public class FragmentInformation extends Fragment {
    public TextView latitude;
    public TextView longtiude;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);


        latitude =rootView.findViewById(R.id.latitude);
        longtiude=rootView.findViewById(R.id.longtiude);

        latitude.setText(getArguments().getString("latitude"));
        longtiude.setText(getArguments().getString("longtiude"));



        final FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();




        return rootView;






    }



}
