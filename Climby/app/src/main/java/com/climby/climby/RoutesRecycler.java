package com.climby.climby;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.climby.climby.databinding.FragmentRoutesRecyclerBinding;
import com.climby.climby.databinding.RoutesViewholderBinding;
import com.climby.climby.model.Route;
import com.climby.climby.viewmodel.RouteViewmodel;

import java.util.ArrayList;
import java.util.List;
public class RoutesRecycler extends Fragment {
    FragmentRoutesRecyclerBinding binding;
    NavController navController;
    RouteViewmodel routeViewmodel;

    public RoutesRecycler() {
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
        return (binding = FragmentRoutesRecyclerBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Route> routes = new ArrayList<>();

        routeViewmodel = new ViewModelProvider(requireActivity()).get(RouteViewmodel.class);
        navController = Navigation.findNavController(view);
        RoutesAdapter routesAdapter = new RoutesAdapter();

        routeViewmodel.fetchRoutes();

        binding.routesRecycler.setAdapter(routesAdapter);
        binding.routesRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        routeViewmodel.getAll().observe(getViewLifecycleOwner(), new Observer<List<Route>>() {
            @Override
            public void onChanged(List<Route> routes) {
                routesAdapter.establishList(routes);
            }
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_routesRecycler_to_newRouteFragment);
            }
        });
    }

    class RoutesViewHolder extends RecyclerView.ViewHolder{
        private final RoutesViewholderBinding binding;

        public RoutesViewHolder(RoutesViewholderBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class RoutesAdapter extends RecyclerView.Adapter<RoutesViewHolder>{
        List<Route> routes;
        @NonNull
        @Override
        public RoutesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            return new RoutesViewHolder(RoutesViewholderBinding.inflate(getLayoutInflater(), parent, false));
        }

        public Route getRoute(int position){ return routes.get(position); }

        @Override
        public void onBindViewHolder(@NonNull RoutesViewHolder holder, int position) {
            Route route = routes.get(position);

            holder.binding.routeName.setText(route.getName());
            holder.binding.RouteType.setText(route.getType());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    routeViewmodel.select(route);
                    navController.navigate(R.id.action_routesRecycler_to_routeDetailFragment);
                }
            });
        }


        @Override
        public int getItemCount() {
            return routes != null ? routes.size() : 0;
        }

        public void establishList(List<Route> routes){
            this.routes = routes;
            notifyDataSetChanged();
        }
    }
}