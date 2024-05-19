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

import com.climby.climby.databinding.ClimbsViewholderBinding;
import com.climby.climby.databinding.FragmentClimbsRecyclerBinding;
import com.climby.climby.model.Climb;
import com.climby.climby.viewmodel.ClimbViewmodel;
import com.climby.climby.viewmodel.RouteViewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ClimbsRecycler extends Fragment {
    NavController navController;
    FragmentClimbsRecyclerBinding binding;
    ClimbViewmodel climbViewmodel;
    RouteViewmodel routeViewmodel;
    Executor executor = Executors.newSingleThreadExecutor();
    public ClimbsRecycler() {
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
        return (binding = FragmentClimbsRecyclerBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Climb> climbs = new ArrayList<>();

        climbViewmodel = new ViewModelProvider(requireActivity()).get(ClimbViewmodel.class);
        routeViewmodel = new ViewModelProvider(requireActivity()).get(RouteViewmodel.class);
        navController = Navigation.findNavController(view);
        ClimbsAdapter climbsAdapter = new ClimbsAdapter();
        climbViewmodel.fetchUserClimbs();



        binding.climbsRecycler.setAdapter(climbsAdapter);
        binding.climbsRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        climbViewmodel.getUserClimbs(SharedData.userId).observe(getViewLifecycleOwner(), new Observer<List<Climb>>() {
            @Override
            public void onChanged(List<Climb> climbs) {
                climbsAdapter.establishList(climbs);
            }
        });
    }


    class ClimbsViewHolder extends RecyclerView.ViewHolder{
        private final ClimbsViewholderBinding binding;

        public ClimbsViewHolder(ClimbsViewholderBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class ClimbsAdapter extends RecyclerView.Adapter<ClimbsViewHolder>{
        List<Climb> climbs;

        @NonNull
        @Override
        public ClimbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ClimbsViewHolder(ClimbsViewholderBinding.inflate(getLayoutInflater(), parent, false));
        }

        public Climb getClimb(int position){ return climbs.get(position); }

        @Override
        public void onBindViewHolder(@NonNull ClimbsViewHolder holder, int position) {
            Climb climb = climbs.get(position);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String routeName = routeViewmodel.getRouteName(climb.getRouteId());
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.binding.routeNameHolder.setText(routeName);
                            holder.binding.climbDateHolder.setText(climb.getDate().toString());
                        }
                    });

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    climbViewmodel.select(climb);
                    navController.navigate(R.id.action_climbsRecycler_to_climbDetailFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return climbs != null ? climbs.size() : 0;
        }

        public void establishList(List<Climb> climbs){
            this.climbs = climbs;
            notifyDataSetChanged();
        }
    }
}