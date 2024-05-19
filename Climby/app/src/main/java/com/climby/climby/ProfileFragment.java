package com.climby.climby;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.climby.climby.databinding.FragmentProfileBinding;
import com.climby.climby.databinding.FragmentRegisterBinding;
import com.climby.climby.model.UserProfile;
import com.climby.climby.viewmodel.UserProfileViewmodel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    NavController navController;
    Executor executor;
    UserProfile userProfile;
    UserProfileViewmodel userProfileViewmodel;



    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentProfileBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userProfileViewmodel = new ViewModelProvider(requireActivity()).get(UserProfileViewmodel.class);
        navController = Navigation.findNavController(view);

        userProfileViewmodel.fetchUserData(SharedData.userId);

        LiveData<UserProfile> userProfileLiveData = userProfileViewmodel.getById(SharedData.userId);
        userProfileLiveData.observe(getViewLifecycleOwner(), new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                if (userProfile != null) {
                    Log.d("USER DATA: ", userProfile.toString());
                    binding.bioText.setText(userProfile.getBio());
                    binding.editEmailProfile.setText(userProfile.getEmail());
                    binding.editUsernameProfile.setText(userProfile.getUsername());
                } else {
                    // Handle case where user data is null (e.g., show error message)
                }
            }


        });
    }
}