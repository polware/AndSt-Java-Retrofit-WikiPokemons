package com.polware.wikipokemons.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.polware.wikipokemons.R;
import com.polware.wikipokemons.models.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private ArrayList<Pokemon> pokemons;
    private Context context;
    private Pokemon pokemon;

    private OnPokemonCardEvents events;

    public PokemonAdapter(Context context) {
        this.context = context;
        pokemons = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        pokemon = pokemons.get(position);
        holder.nameTextView.setText(pokemon.getName());

        //Cargamos la imagen del Pokemon
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/"+ pokemon.getNumber()+".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        // Obtiene la posicion y el nombre del Pokemon seleccionado
        holder.nameTextView.setText(pokemons.get(position).getName());

        //Evento que escucha el cic sobre la tarjeta
        holder.itemView.setOnClickListener(v->{
            if(events != null)
                events.OnPokemonSelected(position, pokemons.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void addListPokemon(ArrayList<Pokemon> listPokemon){
        pokemons.addAll(listPokemon);
        //Actualizamos el RecyclerView
        notifyDataSetChanged();
    }

    //Interfaz de la tarjeta del pokemon
    public interface OnPokemonCardEvents{
        //Metodo de cuando se selecciona o dé clic en la tarjeta
        void OnPokemonSelected(int position, Pokemon pokemon);
    }

    public void setOnPokemonSelected(OnPokemonCardEvents events){
        this.events = events;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Variables enlazadas al Layout item_pokemon
        private ImageView imageView;
        private TextView nameTextView;
        private CardView cards;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            cards = itemView.findViewById(R.id.cards);
        }
    }

}
