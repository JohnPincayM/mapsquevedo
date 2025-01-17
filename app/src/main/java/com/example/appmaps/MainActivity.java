package com.example.appmaps;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            //conectamos el fragment con google maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        //configurar el mapa
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.getUiSettings().setZoomControlsEnabled(true);

        //mover a shibuya
        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(-1.0281677433138636, -79.46783068124908), 15);
        mapa.animateCamera(camUpd1);

        //mover en 3d
        /*LatLng madrid = new LatLng(35.66950480553888, 139.69433172590777);
        CameraPosition camPos = new CameraPosition.Builder()
                .target(madrid)
                .zoom(25)
                .bearing(45) //noreste arriba
                .tilt(90) //punto de vista de la cámara 70 grados
                .build();
        CameraUpdate camUpd3 =
                CameraUpdateFactory.newCameraPosition(camPos);
        mapa.animateCamera(camUpd3);*/
        //encerar quevedo añadir poligono
        PolylineOptions lineas = new PolylineOptions()
                .add(new LatLng(-0.971659904441967, -79.49651840313498))  // Esquina superior izquierda
                .add(new LatLng(-0.971659904441967, -79.44039394516551))  // Esquina inferior izquierda
                .add(new LatLng(-1.0608625756155359, -79.44039394516551)) // Esquina inferior derecha
                .add(new LatLng(-1.0608625756155359, -79.49651840313498)) // Esquina superior derecha
                .add(new LatLng(-0.971659904441967, -79.49651840313498)); // Volver a la esquina superior izquierda

        lineas.width(8);
        lineas.color(Color.RED);
        mapa.addPolyline(lineas);

        /*//AGREGAR MARCADORES AL MPAA


        mapa.addMarker(new MarkerOptions().position(new LatLng(-1.026147589026278, -79.46713361413268)).title("Centro Quevedo"));
        mapa.addMarker(new MarkerOptions().position(new LatLng(-0.971659904441967, -79.49651840313498)).title("1"));
        mapa.addMarker(new MarkerOptions().position(new LatLng(-0.971659904441967, -79.44039394516551)).title("2"));
        mapa.addMarker(new MarkerOptions().position(new LatLng(-1.0608625756155359, -79.44039394516551)).title("3"));
        mapa.addMarker(new MarkerOptions().position(new LatLng(-1.0608625756155359, -79.49651840313498)).title("4"));*/

        //generar un evento clic para poner automaticamente marcador
        mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mapa.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)));
            }
        });
        

        //circunferencia que encierr quevedo con un radio de 5km
        CircleOptions circleOptions= new CircleOptions()
                .center(new LatLng(-1.026147589026278, -79.46713361413268))
                .radius(5000)//En Metros
                .strokeColor(Color.RED)
                .fillColor(Color.argb(50, 150, 50, 50));
        Circle circulo = mapa.addCircle(circleOptions);
      













    }
}