package sa.edu.yamamh.riyadhgo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.TransportMethodTypeSelectedListener;
import sa.edu.yamamh.riyadhgo.data.SelectTransportMethodModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;

public class SelectTransportMethodsRVAdapter extends RecyclerView.Adapter<SelectTransportMethodsRVAdapter.ViewHolder> {

    private TransportMethodTypeSelectedListener listener;
    private List<SelectTransportMethodModel> methodsList;


    public SelectTransportMethodsRVAdapter(List<SelectTransportMethodModel> methods , TransportMethodTypeSelectedListener listener)
    {
        this.methodsList = methods != null ? methods : new ArrayList<>();
        this.listener = listener;
    }
    @NonNull
    @Override
    public SelectTransportMethodsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transport_method, parent, false);

        return new SelectTransportMethodsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTransportMethodsRVAdapter.ViewHolder holder, int position) {
        SelectTransportMethodModel model = this.methodsList.get(position);
        holder.getMethodTypeTV().setText(model.getMethodType() != null ?  model.getMethodType().toString() : "MT");
        holder.getMethodNameTV().setText(model.getNames());
        holder.getMethodPriceTV().setText(String.format("%.2f-%.2f",model.getMaxPrice(),model.getMinPrice()));

        holder.getMethodTypeTV().setOnClickListener(view -> listener.methodTypeSelected(model));
        holder.getMethodNameTV().setOnClickListener(view -> listener.methodTypeSelected(model));
        holder.getMethodPriceTV().setOnClickListener(view -> listener.methodTypeSelected(model));

        switch (TransportMethodTypes.valueOf(model.getMethodType()))
        {
            case BUS:
                holder.getIconView().setImageResource(R.drawable.bus);
                break;
            case CAR:
                holder.getIconView().setImageResource(R.drawable.car);
                break;
            case SCOOTER:
                holder.getIconView().setImageResource(R.drawable.scooter);
                break;
            case WALK:
                holder.getIconView().setImageResource(R.drawable.walk);
        }
    }

    @Override
    public int getItemCount() {
        return this.methodsList.size();
    }

    public void clearItems() {
        if(methodsList != null){
            methodsList.clear();
        }

        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView methodTypeTV;
        private TextView methodNameTV;
        private TextView methodPriceTV;
        private ImageView iconView;

        public ViewHolder(@NonNull View parent) {
            super(parent);
            this.methodTypeTV = parent.findViewById(R.id.tvMethodType);
            this.methodNameTV = parent.findViewById(R.id.tvMethodName);
            this.methodPriceTV = parent.findViewById(R.id.tvMethodPrice);
            this.iconView = parent.findViewById(R.id.ivMethodImage);
        }

        public TextView getMethodTypeTV() {
            return methodTypeTV;
        }

        public TextView getMethodNameTV() {
            return methodNameTV;
        }

        public TextView getMethodPriceTV() {
            return methodPriceTV;
        }

        public ImageView getIconView() {
            return this.iconView;
        }
    }
}
