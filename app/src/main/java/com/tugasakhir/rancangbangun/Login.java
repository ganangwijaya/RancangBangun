package com.tugasakhir.rancangbangun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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


public class Login extends AppCompatActivity {

    Button button_login, button_register;
    EditText et_username, et_password;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hideNav();

        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login();
            }
        });

        button_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        if(pref.contains("username") && pref.contains("password")){
            startActivity(new Intent(getApplicationContext(), Nav.class));
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        hideNav();
    }

    public void onBackPressed(){
        if(pref.contains("username") && pref.contains("password")){
            startActivity(new Intent(getApplicationContext(), Nav.class));
        }
        else{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    public void login(){
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.100.5/ta/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("1")){
                            startActivity(new Intent(getApplicationContext(), Nav.class));
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("username", et_username.getText().toString());
                            editor.putString("password", et_password.getText().toString());
                            editor.commit();

                        }
                        else {

                            Toast.makeText(getApplicationContext(),
                                     "Username atau Password yang anda masukkan salah.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Tidak terkoneksi dengan jaringan WMN.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", et_username.getText().toString());
                params.put("password", et_password.getText().toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);

    }

    private void hideNav() {
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_IMMERSIVE |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE  |
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                );

    }
}
