package com.climby.climby;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.climby.climby.model.PersistentCookieJar;

import okhttp3.CookieJar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataAccess.setHttpClient(getApplicationContext());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
    }
}