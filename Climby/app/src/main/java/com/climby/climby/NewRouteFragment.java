package com.climby.climby;

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

import com.climby.climby.databinding.FragmentNewRouteBinding;
import com.climby.climby.model.Route;
import com.climby.climby.viewmodel.RouteViewmodel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NewRouteFragment extends Fragment {

    FragmentNewRouteBinding binding;
    RouteViewmodel routeViewmodel;
    NavController navController;
    int newId;

    Executor executor;

    public NewRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentNewRouteBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        routeViewmodel = new ViewModelProvider(requireActivity()).get(RouteViewmodel.class);
        navController = Navigation.findNavController(view);
        executor = Executors.newSingleThreadExecutor();

        Route route = new Route();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route.setDescription(binding.descNewRoute.getText().toString());
                route.setLocation(binding.LocNewRoute.getText().toString());
                route.setName(binding.nameNewRoute.getText().toString());
                route.setDifficulty(binding.diffNewRotue.getText().toString());
                route.setType(binding.typeNewRoute.getText().toString());


                // First insert into database and then use the id returned
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        newId = DataAccess.addNewRoute(route);
                        if(newId == 0){
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(requireActivity(), "There was an error", Toast.LENGTH_SHORT).show();
                                    navController.popBackStack();
                                }
                            });

                        } else {
                            route.setId(newId);
                            routeViewmodel.insert(route);
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    navController.popBackStack();
                                }
                            });
                        }
                    }
                });

            }
        });
    }
}
