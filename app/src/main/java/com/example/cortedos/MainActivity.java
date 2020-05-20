package com.example.cortedos;

import android.content.Intent;
import android.os.Bundle;
import com.example.cortedos.Adaptador.Adaptador;
import com.example.cortedos.Clases.Artist;
import com.example.cortedos.Clases.Playlist;
import com.example.cortedos.Clases.Track;
import com.example.cortedos.Clases.Tracks;
import com.example.cortedos.api.CancionesInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
public static List<Artist> listA=new ArrayList<>();
public static List<Track> listT=new ArrayList<>();

private RecyclerView recyclerView;
private Adaptador adaptador;
Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,  AgregarCancion.class);
                startActivity(intent);
            }
        });
        consultaPlaylist();
    }

    private void consultaPlaylist() {
        retrofit = new Retrofit.Builder().baseUrl("https://ws.audioscrobbler.com/").addConverterFactory(GsonConverterFactory.create()).build();
        CancionesInterface cancionesInterface = retrofit.create(CancionesInterface.class);
        Call<Playlist> call = cancionesInterface.getItem();
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                Playlist playlist = response.body();
                Tracks tracks = playlist.getTracks();
                listT=tracks.getTrack();

                for (Track track: listT){
                    Artist artist = track.getArtist();
                    listA.add(artist);
                }

                recyclerView = (RecyclerView) findViewById(R.id.recyclerTrack);
                LinearLayoutManager linearManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearManager);
                adaptador = new Adaptador(listT,listA,MainActivity.this);
                recyclerView.setAdapter(adaptador);

            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR: "+ t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,  AgregarCancion.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}