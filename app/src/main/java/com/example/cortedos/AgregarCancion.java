package com.example.cortedos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cortedos.Clases.Artist;
import com.example.cortedos.Clases.Buscar;
import com.example.cortedos.Clases.Results;
import com.example.cortedos.Clases.Track;
import com.example.cortedos.Clases.TrackBuscar;
import com.example.cortedos.Clases.Trackmatches;
import com.example.cortedos.api.CancionesInterface;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgregarCancion extends AppCompatActivity{
    EditText name, buscar, artist;
    private Button btnBusqueda;
    List<TrackBuscar> lista=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        name =(EditText)findViewById(R.id.txtCancion);
        artist=(EditText)findViewById(R.id.txtArtista);
        buscar = (EditText)findViewById(R.id.txtBuscar);

        btnBusqueda=(Button) findViewById(R.id.btnBuscar);
        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Busqueda();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                RegistrarCancion();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Busqueda(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ws.audioscrobbler.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CancionesInterface cancionesInterface = retrofit.create(CancionesInterface.class);
        String aux = buscar.getText().toString();
        Call<Buscar> call = cancionesInterface.getBusqueda("track.search",aux,"b284db959637031077380e7e2c6f2775","json");
        try{
            call.enqueue(new Callback<Buscar>() {
                @Override
                public void onResponse(Call<Buscar> call, Response<Buscar> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Code: "+response.code(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Buscar buscar = response.body();
                    Results results = buscar.getResults();
                    Trackmatches trackmatches = results.getTrackmatches();
                    lista = trackmatches.getTrack();
                    name.setText(lista.get(0).getName());
                    artist.setText(lista.get(0).getArtist());
                }

                @Override
                public void onFailure(Call<Buscar> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"ERROR: "+ t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IllegalStateException | JsonSyntaxException exception){ }
    }

    private void RegistrarCancion(){
        Track track = new Track();
        Artist artista=new Artist();

        artista.name= artist.getText().toString();
        track.name = name.getText().toString();
        track.duration="0";
        track.artist=artista;
        MainActivity.listT.add(track);
        MainActivity.listA.add(artista);
    }
}

