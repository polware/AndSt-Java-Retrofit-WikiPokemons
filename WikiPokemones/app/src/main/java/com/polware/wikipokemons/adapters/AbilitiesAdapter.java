package com.polware.wikipokemons.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polware.wikipokemons.R;
import com.polware.wikipokemons.models.Abilities;

import java.util.List;

public class AbilitiesAdapter extends RecyclerView.Adapter<AbilitiesAdapter.AbilityViewHolder> {
    private List<Abilities> abilitiesList;
    private Context context;

    public AbilitiesAdapter(List<Abilities> abilitiesList, Context context) {
        this.abilitiesList = abilitiesList;
        this.context = context;
    }

    @NonNull
    @Override
    public AbilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.abilities_list, parent, false);
        return new AbilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbilityViewHolder holder, int position) {
        holder.abilitieName.setText(abilitiesList.get(position).getAbility().getName());
    }

    @Override
    public int getItemCount() {
        return abilitiesList.size();
    }

    class AbilityViewHolder extends RecyclerView.ViewHolder{
        TextView abilitieName;
        public AbilityViewHolder(@NonNull View itemView) {
            super(itemView);
            abilitieName = itemView.findViewById(R.id.textViewAbilitieName);
        }
    }

}
