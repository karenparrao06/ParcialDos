package com.example.cortedos.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cortedos.Clases.Artist;
import com.example.cortedos.Clases.Track;
import com.example.cortedos.R;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.AdaptadorViewHolder> {

    public final List<Track> listaTracks;
    public final List<Artist> listaArtist;
    private LayoutInflater layoutInflater;

    public Adaptador(List<Track> listaTracks, List<Artist> listaArtist, Context context) {
        this.listaTracks = listaTracks;
        this.listaArtist = listaArtist;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.activity_lista,null);
        return new AdaptadorViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorViewHolder holder, int posicion) {
        Track track =listaTracks.get(posicion);
        Artist artist = listaArtist.get(posicion);
        holder.nombre.setText(track.name);
        holder.artist.setText(artist.name);
        holder.duration.setText(track.duration);
    }

    @Override
    public int getItemCount() {
        return listaTracks.size();
    }

    public class AdaptadorViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private TextView artist;
        private TextView duration;
        private Adaptador trackAdapter;

        public AdaptadorViewHolder(@NonNull View itemView, Adaptador trackAdapter) {
            super(itemView);
            nombre = itemView.findViewById(R.id.idNombre);
            artist=itemView.findViewById(R.id.idArtist);
            duration=itemView.findViewById(R.id.idDuracion);
            this.trackAdapter=trackAdapter;
        }
    }
}
