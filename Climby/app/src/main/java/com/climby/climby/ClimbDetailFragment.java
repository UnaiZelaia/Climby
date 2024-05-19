package com.climby.climby;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.climby.climby.databinding.FragmentClimbDetailBinding;
import com.climby.climby.model.Climb;
import com.climby.climby.viewmodel.ClimbViewmodel;
import com.climby.climby.viewmodel.RouteViewmodel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ClimbDetailFragment extends Fragment {

    FragmentClimbDetailBinding binding;
    NavController navController;
    ClimbViewmodel climbViewmodel;
    RouteViewmodel routeViewmodel;
    Executor executor = Executors.newSingleThreadExecutor();
    String routeName = "";

    public ClimbDetailFragment() {
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
        return (binding = FragmentClimbDetailBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        climbViewmodel = new ViewModelProvider(requireActivity()).get(ClimbViewmodel.class);
        routeViewmodel = new ViewModelProvider(requireActivity()).get(RouteViewmodel.class);
        navController = Navigation.findNavController(view);

        climbViewmodel.selected().observe(getViewLifecycleOwner(), new Observer<Climb>() {
            @Override
            public void onChanged(Climb climb) {
                binding.climbDetailComment.setText(climb.getComment());
                binding.climbDetailDate.setText(climb.getDate().toString());
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        routeName = routeViewmodel.getRouteName(climb.getRouteId());
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.climbDetailRoute.setText(routeName);
                            }
                        });
                    }
                });
            }
        });
    }
}