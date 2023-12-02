package sa.edu.yamamh.riyadhgo.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import sa.edu.yamamh.riyadhgo.DistanceUtils;
import sa.edu.yamamh.riyadhgo.PlaceSelectedListener;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.data.LocationModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;

public class LocationsRVAdapter extends RecyclerView.Adapter<LocationsRVAdapter.ViewHolder> {

    private PlaceSelectedListener listener;
    private List<LocationModel> locations;
    private LatLng distanceFrom;

    public LocationsRVAdapter(List<LocationModel> locations , PlaceSelectedListener listener, LatLng distanceFrom)
    {
        this.locations = locations != null ? locations : new ArrayList<>();
        this.listener = listener;
        this.distanceFrom = distanceFrom;
    }
    @NonNull
    @Override
    public LocationsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_place, parent, false);

        return new LocationsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsRVAdapter.ViewHolder holder, int position) {
        LocationModel model = this.locations.get(position);
        holder.getLocationNameTV().setText(model.getName());
        double dist = DistanceUtils.getDistanceInKM(model.getLatLng(),distanceFrom);
        Log.i("LOCRVAdapter",model.getLatLng().toString() + " {to} " + this.distanceFrom.toString() );
        holder.getLocationDistanceTV().setText(String.format("%.2f Km",dist));
        double time = DistanceUtils.getEstimatedTimeInMinutes(dist, TransportMethodTypes.CAR);
        holder.getLocationTimeTV().setText(String.format("%.2f Min", time));

        holder.getLocationTimeTV().setOnClickListener(view -> listener.placeSelected(model));
        holder.getLocationDistanceTV().setOnClickListener(view -> listener.placeSelected(model));
        holder.getLocationNameTV().setOnClickListener(view -> listener.placeSelected(model));
    }

    @Override
    public int getItemCount() {
        return this.locations.size();
    }

    public void clearItems() {
        if(locations != null){
            locations.clear();
        }

        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView locationNameTV;
        private TextView locationDistanceTV;
        private TextView locationTimeTV;

        public ViewHolder(@NonNull View parent) {
            super(parent);
            this.locationNameTV = parent.findViewById(R.id.tvPlaceName);
            this.locationDistanceTV = parent.findViewById(R.id.spi_tv_distance);
            this.locationTimeTV = parent.findViewById(R.id.spi_tv_duration);

        }

        public TextView getLocationDistanceTV() {
            return locationDistanceTV;
        }

        public TextView getLocationNameTV() {
            return locationNameTV;
        }

        public TextView getLocationTimeTV() {
            return locationTimeTV;
        }
    }
}
