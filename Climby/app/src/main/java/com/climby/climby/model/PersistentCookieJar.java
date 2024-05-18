package com.climby.climby.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class PersistentCookieJar implements CookieJar {

    private final Context context;

    public PersistentCookieJar(Context context) {
        this.context = context;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        SharedPreferences prefs = context.getSharedPreferences("cookies", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (Cookie cookie : cookies) {
            String key = cookie.name();
            String value = cookie.value();
            editor.putString(key, value);
        }

        editor.apply();
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        SharedPreferences prefs = context.getSharedPreferences("cookies", Context.MODE_PRIVATE);
        List<Cookie> cookies = new ArrayList<>();

        Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            Cookie cookie = new Cookie.Builder()
                    .domain(url.host()) // Set domain based on the request URL (might cause issues)
                    .path("/") // Set path to "/" (can be adjusted based on your needs)
                    .name(key)
                    .value(value)
                    .build();
            cookies.add(cookie);
        }

        return cookies;
    }
}

