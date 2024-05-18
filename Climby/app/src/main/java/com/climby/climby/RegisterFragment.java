package com.climby.climby;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.climby.climby.dao.DBAccess;
import com.climby.climby.databinding.FragmentRegisterBinding;
import com.climby.climby.viewmodel.UserProfileViewmodel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    NavController navController;
    UserProfileViewmodel userProfileViewmodel;
    Executor executor;

    public RegisterFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentRegisterBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        userProfileViewmodel = new ViewModelProvider(requireActivity()).get(UserProfileViewmodel.class);
        executor = Executors.newSingleThreadExecutor();

        binding.backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.usernameRegisterText.getText().toString();
                String email = binding.emailRegisterText.getText().toString();
                String password = binding.passwordRegisterText.getText().toString();
                String confirm = binding.confirmRegisterText.getText().toString();

                if (!password.equals(confirm)){
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean ok = DataAccess.registerUser(email, username, password);
                            if (ok){
                                if(DataAccess.getTokenFromLogin(email, password, getContext())){
                                   Intent intent = new Intent(getContext(), MainActivity.class);
                                   startActivity(intent);
                                }
                            }
                        }
                    });
                }
            }
        });




















    }




































}