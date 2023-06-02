package com.example.earthquakee.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.earthquakee.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentLogin extends Fragment {
    private Button buttonSign;
    private EditText editEmail,editPassaword,editName;
    private String txtEmail,txtPassaword;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore mFirestore;

    private DocumentReference docRef;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign, container, false);
        



        editEmail=rootView.findViewById(R.id.signEmail);
        editPassaword=rootView.findViewById(R.id.signPassaword);
        buttonSign = rootView.findViewById(R.id.buttonLogin);
        mAuth =FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();


        buttonSign = rootView.findViewById(R.id.buttonLogin);
        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                girisYap();

            }
        });

return rootView;
    }

    private void girisYap() {
        txtEmail=editEmail.getText().toString();
        txtPassaword=editPassaword.getText().toString();

        if(!TextUtils.isEmpty(txtEmail)&& !TextUtils.isEmpty(txtPassaword)){
            mAuth.signInWithEmailAndPassword(txtEmail,txtPassaword)
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser =mAuth.getCurrentUser();



                            System.out.println("Kullanıcı adi : " + mUser.getDisplayName());
                            System.out.println("Email  : " + mUser.getEmail());
                            System.out.println("Kullanıcı adi : " + mUser.getUid());

                            assert mUser != null;
                            verileriGetir(mUser.getUid());

                            FragmentUsers fragmentUsers = new FragmentUsers();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.fragmentHolder,fragmentUsers);
                            ft.commit();


                        }
                    }).addOnFailureListener(getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }else
            Toast.makeText(requireContext(), "Email ve Şifre Boş Olamaz ! ", Toast.LENGTH_SHORT).show();

    }

    private void verileriGetir(String uid){
       docRef= mFirestore.collection("Users").document(uid);
       docRef.get()
               .addOnSuccessListener(getActivity(), new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                       if(documentSnapshot.exists()){
                           System.out.println(documentSnapshot.getData());
                       }

                   }
               }).addOnFailureListener(getActivity(), new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
    }
}
