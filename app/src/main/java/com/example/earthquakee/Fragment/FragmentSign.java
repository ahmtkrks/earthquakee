package com.example.earthquakee.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.earthquakee.Model.Users;
import com.example.earthquakee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class FragmentSign extends Fragment {

    private Users mUsers;
    private Button buttonSign;
    private TextView txtLogin;
    private EditText editEmail,editPassaword,editName;
    private String txtEmail,txtPassaword,txtName;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private HashMap<String,Object> mData;
    private FirebaseFirestore mFirestore;




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign, container, false);

        editEmail=rootView.findViewById(R.id.editEmail);
        editPassaword=rootView.findViewById(R.id.editPassaword);
        editName=rootView.findViewById(R.id.editName);
        buttonSign = rootView.findViewById(R.id.buttonSign);

        txtLogin =rootView.findViewById(R.id.txtLogin);


        mAuth =FirebaseAuth.getInstance();
        mReference= FirebaseDatabase.getInstance().getReference();
        mFirestore=FirebaseFirestore.getInstance();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentLogin fragmentSign = new FragmentLogin();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentHolder, fragmentSign);
                ft.commit();
            }
        });




       buttonSign.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               kayitOl();
           }
       });

        return rootView;
    }


    private void kayitOl() {
        txtEmail = editEmail.getText().toString();
        txtPassaword=editPassaword.getText().toString();
        txtName = editName.getText().toString();


        if(!TextUtils.isEmpty((txtName))&&!TextUtils.isEmpty(txtEmail)&& !TextUtils.isEmpty(txtPassaword)){
            mAuth.createUserWithEmailAndPassword(txtEmail,txtPassaword)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                mUser =mAuth.getCurrentUser();

                                if(mUser != null){
                                    mUsers= new Users(txtName,txtEmail,mUser.getUid(),"default");

                                    mFirestore.collection("Users").document(mUser.getUid())
                                            .set(mUsers)
                                            .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){
                                                        Toast.makeText(requireContext(), "Kayit İşlemi Başarili!", Toast.LENGTH_SHORT).show();
                                                        FragmentLogin fragmentSign = new FragmentLogin();
                                                        FragmentManager fm = getFragmentManager();
                                                        FragmentTransaction ft = fm.beginTransaction();
                                                        ft.replace(R.id.fragmentHolder, fragmentSign);
                                                        ft.commit();

                                                    }
                                                }
                                            });

                                }


                                /*   mData = new HashMap<>();

                                mData.put("kullaniciAdi", txtName);
                                mData.put("kullaniciEmail",txtEmail);
                                mData.put("kulliniciSifre",txtPassaword);
                                mData.put("kullaniciId",mUser.getUid());*/




                                /*    mReference.child("Users")
                                      .child(mUser.getUid())
                                        .setValue(mData)
                                                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {

                                                            Toast.makeText(requireContext(), "Kayit İşlemi Başarili!", Toast.LENGTH_SHORT).show();
                                                            FragmentSign fragmentSign = new FragmentSign();
                                                            FragmentManager fm = getFragmentManager();
                                                            FragmentTransaction ft = fm.beginTransaction();
                                                            ft.replace(R.id.fragmentHolder, fragmentSign);
                                                            ft.commit();
                                                        }

                                                        else{
                                                            Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                        }

                                                    }
                                                }); */


                            } else{
                                Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }else
            Toast.makeText(requireContext(), "Email ve Şifre Boş Olamaz!", Toast.LENGTH_SHORT).show();
    }


}