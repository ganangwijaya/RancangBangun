package com.tugasakhir.rancangbangun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.support.v7.widget.CardView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentPertama extends Fragment{
    TextView buatsuhu, buatpintu, buatlampu, buatkipas;
    public static String ruangan, idx, suhu, pintu, relay1, relay2, relay1x, relay2x;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.ruangan_pertama, container, false);
        RelativeLayout lampuin = myView.findViewById(R.id.nyala_lampu);
        RelativeLayout kipasin = myView.findViewById(R.id.nyala_kipas);

        buatsuhu = myView.findViewById(R.id.kondisi_suhu);
        buatpintu = myView.findViewById(R.id.kondisi_pintu);
        buatlampu = myView.findViewById(R.id.kondisi_lampu);
        buatkipas = myView.findViewById(R.id.kondisi_kipas);

        lampuin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lampu();
            }
        });

        kipasin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kipas();
            }
        });

        parsedata();
        getActivity().setTitle("Ruangan Pertama");
        return myView;
    }

    public void parsedata(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.5/ta/ambildata.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            for(int i = 0; i < data.length(); i++){
                                JSONObject dataobject = data.getJSONObject(i);

                                ruangan = dataobject.getString("ruangan");
                                suhu = dataobject.getString("suhu");
                                pintu = dataobject.getString("pintu");
                                relay1 = dataobject.getString("relay1");
                                relay1x = relay1;
                                if (relay1.equals("1")){
                                    relay1 = "ON";
                                }
                                else {
                                    relay1 = "OFF";
                                }
                                relay2 = dataobject.getString("relay2");
                                relay2x = relay2;
                                if (relay2.equals("1")){
                                    relay2 = "ON";
                                }
                                else {
                                    relay2 = "OFF";
                                }
                                buatsuhu.setText(suhu + "°C");
                                buatkipas.setText(relay2);
                                buatlampu.setText(relay1);
                                String pintuin = pintu.substring(0,1).toUpperCase() + pintu.substring(1);
                                buatpintu.setText(pintuin);
                                refresh(1000);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ruangan", "pertama");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);
    }

    public void lampu(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.5/ta/pencetrelaylain.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.isEmpty()){
                            Toast.makeText(getActivity(),
                                    "Gagal", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (relay1x.equals("1")) {
                                Toast.makeText(getActivity(),
                                        "Berhasil mematikan lampu.", Toast.LENGTH_SHORT).show();
                            }
                            else if (relay1x.equals("0")){
                                Toast.makeText(getActivity(),
                                        "Berhasil menyalakan lampu.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ruangan", ruangan);
                params.put("relay", "relay1");
                if (relay1x.equals("1")) {
                    params.put("kondisi", "0");
                } else {
                    params.put("kondisi", "1");
                }
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    public void kipas(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.5/ta/pencetrelaylain.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.isEmpty()){
                            Toast.makeText(getActivity(),
                                    "Gagal", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            if (relay2x.equals("1")) {
                                Toast.makeText(getActivity(),
                                        "Berhasil mematikan kipas.", Toast.LENGTH_SHORT).show();
                            }
                            else if (relay2x.equals("0")){
                                Toast.makeText(getActivity(),
                                        "Berhasil menyalakan kipas.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ruangan", ruangan);
                params.put("relay", "relay2");
                if (relay2x.equals("1")) {
                    params.put("kondisi", "0");
                } else {
                    params.put("kondisi", "1");
                }
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void refresh(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                parsedata();
            }
        };
        handler.postDelayed(runnable, milliseconds);
    }
}
