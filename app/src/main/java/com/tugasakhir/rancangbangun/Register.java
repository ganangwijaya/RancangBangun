package com.tugasakhir.rancangbangun;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText masuk_username, masuk_password, cek_password, nama, nip, status;
    Button registerin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
        hideNav();
        registerin = findViewById(R.id.button_registerin);
        masuk_username = findViewById(R.id.masuk_username);
        masuk_password = findViewById(R.id.masuk_password);
        cek_password = findViewById(R.id.cek_password);
        nama = findViewById(R.id.nama);
        nip = findViewById(R.id.nip);
        status = findViewById(R.id.status);

        registerin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cek();
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNav();
    }

    public void cek(){
        if(masuk_username.getText().toString().isEmpty() || masuk_password.getText().toString().isEmpty() || cek_password.getText().toString().isEmpty() || nip.getText().toString().isEmpty() || status.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Silahkan isi semua form yang ada", Toast.LENGTH_SHORT).show();
        }
        else{
            String samainpass = masuk_password.getText().toString();
            String samainpassword = cek_password.getText().toString();
            if (samainpass.equals(samainpassword)){
                if(samainpass.trim().length() >= 6 && samainpass.trim().length() <= 20){
                    daftar();
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Jumlah password kurang dari 6 atau lebih dari 20", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Password tidak cocok", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void daftar(){
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.100.5/ta/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("1")){
                            Toast.makeText(getApplicationContext(),
                                    "Data anda berhasil di daftarkan", Toast.LENGTH_SHORT).show();
                            // masuk_username.setText(null);
                            // masuk_password.setText(null);
                            // cek_password.setText(null);
                            // nama.setText(null);
                            // nip.setText(null);
                            // status.setText(null);
                        }
                        else if (response.contains("2")){
                            Toast.makeText(getApplicationContext(),
                                    "Username sudah digunakan", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("3")){
                            Toast.makeText(getApplicationContext(),
                                    "NIP/NIM sudah pernah didaftarkan", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Gagal.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Gabisa.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", masuk_username.getText().toString());
                params.put("password", masuk_password.getText().toString());
                params.put("nama", nama.getText().toString());
                params.put("nomor_induk", nip.getText().toString());
                params.put("status", status.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void hideNav(){
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                );
    }


}
