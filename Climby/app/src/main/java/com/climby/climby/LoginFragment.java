package com.climby.climby;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.climby.climby.databinding.FragmentLoginBinding;
import com.climby.climby.viewmodel.UserProfileViewmodel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    NavController navController;
    UserProfileViewmodel userProfileViewmodel;
    Executor executor;

    public LoginFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentLoginBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        userProfileViewmodel = new ViewModelProvider(requireActivity()).get(UserProfileViewmodel.class);
        executor = Executors.newSingleThreadExecutor();
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        binding.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = binding.passwordText.getText().toString();
                String username = binding.loginText.getText().toString();
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int userId = DataAccess.getTokenFromLogin(username, password, getContext());
                                SharedData.userId = userId;
                                if (userId != 0){
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    requireActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "There was an error. Try again", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
            }
        });
    }
}