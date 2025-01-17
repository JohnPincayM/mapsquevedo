package com.example.appmaps;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.slider.Slider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class lugares_Turistico_de_Quevedo extends AppCompatActivity implements OnMapReadyCallback, Asynchtask {
    GoogleMap mapa;
    Double lat, lng;
    float radio;
    Circle circulo = null;

    Slider sliderRadio;
    EditText txtLatitud, txtLongitud;
    ArrayList<Marker> markers = new ArrayList<Marker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lugares_turistico_de_quevedo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



        });
        lat = -1.02313;
        lng = -79.459561;
        radio = 1;
        txtLatitud = findViewById(R.id.editTextNumber);
        txtLongitud = findViewById(R.id.editTextNumber2);
        sliderRadio = findViewById(R.id.sliderRadio);

        //conectamos el fragment con google maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapok);
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
        /*PolylineOptions lineas = new PolylineOptions()
                .add(new LatLng(-0.971659904441967, -79.49651840313498))  // Esquina superior izquierda
                .add(new LatLng(-0.971659904441967, -79.44039394516551))  // Esquina inferior izquierda
                .add(new LatLng(-1.0608625756155359, -79.44039394516551)) // Esquina inferior derecha
                .add(new LatLng(-1.0608625756155359, -79.49651840313498)) // Esquina superior derecha
                .add(new LatLng(-0.971659904441967, -79.49651840313498)); // Volver a la esquina superior izquierda

        lineas.width(8);
        lineas.color(Color.RED);
        mapa.addPolyline(lineas);*/

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


//        //circunferencia que encierr quevedo con un radio de 5km
//        CircleOptions circleOptions= new CircleOptions()
//                .center(new LatLng(-1.026147589026278, -79.46713361413268))
//                .radius(5000)//En Metros
//                .strokeColor(Color.RED)
//                .fillColor(Color.argb(50, 150, 50, 50));
//        Circle circulo = mapa.addCircle(circleOptions);

        sliderRadio.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                radio = slider.getValue();
                updateInterfaz();
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {


            }
        });
        mapa.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng center = mapa.getCameraPosition().target;
                lat = center.latitude;
                lng =center.longitude;
                updateInterfaz();

            }
        });
        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });


    }
    private void updateInterfaz(){
        txtLatitud.setText(String.format("%.4f", lat));
        txtLongitud.setText(String.format("%.4f", lng));

        if(circulo!=null){  circulo.remove();  circulo = null; }

        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(lat,lng))
                .radius(radio * 100)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(50, 150, 50, 50));

        circulo = mapa.addCircle(circleOptions);
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService(
                "https://turismoquevedo.com/lugar_turistico/json_getlistadoMapa?lat=-1.02313&lng=-79.459561&radio=2"
                        + lat +   "&lng=" + lng +"&radio=" + (radio/10.0)  ,datos,

                lugares_Turistico_de_Quevedo.this, lugares_Turistico_de_Quevedo.this);
        ws.execute("GET");

    }


    @Override
    public void processFinish(String result) throws JSONException {


        for (Marker marker : markers) marker.remove();
        markers.clear();

        JSONObject JSONobj= new JSONObject(result);
        JSONArray jsonLista = JSONobj.getJSONArray("data");
        for(int i=0; i< jsonLista.length(); i++){
            JSONObject lugar= jsonLista.getJSONObject(i);
            markers.add(mapa.addMarker(
                    new MarkerOptions().position(
                            new LatLng(lugar.getDouble("lat"), lugar.getDouble("lng"))
                    ).title(lugar.get("nombre").toString())));

        }


    }
}