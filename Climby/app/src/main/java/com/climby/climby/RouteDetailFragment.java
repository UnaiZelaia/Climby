package com.climby.climby;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.climby.climby.databinding.FragmentRouteDetailBinding;
import com.climby.climby.model.Route;
import com.climby.climby.viewmodel.RouteViewmodel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RouteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteDetailFragment extends Fragment {

    FragmentRouteDetailBinding binding;
    RouteViewmodel routeViewmodel;
    public RouteDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentRouteDetailBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        routeViewmodel = new ViewModelProvider(requireActivity()).get(RouteViewmodel.class);
        routeViewmodel.selected().observe(getViewLifecycleOwner(), new Observer<Route>() {
            @Override
            public void onChanged(Route route) {
                binding.routeNameDetail.setText(route.getName());
                binding.routeDIffDetail.setText(route.getDifficulty());
                binding.routeDescDetail.setText(route.getDescription());
                binding.routeLocDetail.setText(route.getLocation());
                binding.routeTypeDetail.setText(route.getType());
            }
        });
    }

}