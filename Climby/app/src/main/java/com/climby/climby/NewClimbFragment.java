package com.climby.climby;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.climby.climby.databinding.FragmentNewClimbBinding;
import com.climby.climby.model.Climb;
import com.climby.climby.model.UserProfile;
import com.climby.climby.viewmodel.ClimbViewmodel;
import com.climby.climby.viewmodel.RouteViewmodel;
import com.climby.climby.viewmodel.UserProfileViewmodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewClimbFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewClimbFragment extends Fragment {

    FragmentNewClimbBinding binding;
    NavController navController;
    Executor executor = Executors.newSingleThreadExecutor();
    int routeId;
    Climb climb;
    ClimbViewmodel climbViewmodel;
    RouteViewmodel routeViewmodel;
    UserProfileViewmodel userProfileViewmodel;


    public NewClimbFragment() {
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
        return (binding = FragmentNewClimbBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Log.d("SHARED DATA", SharedData.userId + "   " +  SharedData.routeId);

        super.onViewCreated(view, savedInstanceState);
        climbViewmodel = new ViewModelProvider(requireActivity()).get(ClimbViewmodel.class);
        routeViewmodel = new ViewModelProvider(requireActivity()).get(RouteViewmodel.class);
        userProfileViewmodel = new ViewModelProvider(requireActivity()).get(UserProfileViewmodel.class);
        navController = Navigation.findNavController(view);

        routeId = SharedData.routeId;

        binding.buttonNewClimb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                climb = new Climb();
                climb.setComment(binding.newRouteComment.getText().toString());
                climb.setDate(LocalDateTime.now());
                climb.setRouteId(SharedData.routeId);
                climb.setUserProfileId(SharedData.userId);

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        int newClimbId = DataAccess.addNewClimb(climb, SharedData.routeId, SharedData.userId);
                        climb.setId(newClimbId);
                        climbViewmodel.insert(climb);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                navController.navigate(R.id.action_newClimbFragment_to_climbsRecycler);
                            }
                        });
                    }
                });
            }
        });
    }
}