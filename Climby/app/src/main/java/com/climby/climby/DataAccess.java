package com.climby.climby;

import android.content.Context;
import android.util.Log;

import com.climby.climby.model.Climb;
import com.climby.climby.model.PersistentCookieJar;
import com.climby.climby.model.Route;
import com.climby.climby.model.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataAccess {

    static String webServiceURL = "http://192.168.0.186:3030/api/v1/";
    static OkHttpClient client;
    static UserProfile userProfile;
    static List<Route> routes;
    static List<Climb> climbs;
    static String authToken;

    Locale locale = new Locale("es", "ES");
    static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 9, true) // minimum 1 digit, maximum 9 digits, optional
            .toFormatter();


    public static void setHttpClient(Context context){
        CookieJar cookieJar = new PersistentCookieJar(context);
        DataAccess.client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }


    public static int getTokenFromLogin(String username, String password, Context context) {
        int userId = 0;
        int responseCode = 0;
        try{

            // Convert username and password to a JSON string
            String json = new JSONObject()
                    .put("email", username)
                    .put("password", password)
                    .toString();

            // Create the request body with JSON content type
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

            Request request = new Request.Builder()
                    .url(webServiceURL + "user/login")
                    .header("Content-Type", "application/json") // Important for JSON data
                    .post(body)
                    .build();

            Response response = DataAccess.client.newCall(request).execute();

            JSONObject jResponse = new JSONObject(response.body().string());
            DataAccess.authToken = jResponse.getString("token");
            responseCode = response.code();
            userId = jResponse.getInt("id");
        } catch (Exception e) {
            return 0;
        }
        if(responseCode == 200){
            return userId;
        } else {
            return 0;
        }
    }


    public static boolean registerUser(String email, String username, String password){
        try{
            String json = new JSONObject()
                    .put("username", username)
                    .put("email", email)
                    .put("password", password)
                    .toString();

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

            Request request = new Request.Builder()
                    .url(webServiceURL + "user/signup")
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();

            Response response = DataAccess.client.newCall(request).execute();
            return response.code() == 200;
        } catch (Exception e){
            return false;
        }
    }

    public static boolean updateProfile(String newUsername, String newEmail, String newBio, Context context, String userId){
        try{
            String json = new JSONObject()
                    .put("username", newUsername)
                    .put("email", newEmail)
                    .put("bio", newBio)
                    .toString();

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

            Request request = new Request.Builder()
                    .url(webServiceURL + "user/" + userId)
                    .header("Content-type", "application/json")
                    .post(body)
                    .build();
            Response response = DataAccess.client.newCall(request).execute();
            return response.code() == 200;
        } catch (Exception e){
            return false;
        }
    }

    public static UserProfile getMyUserData(int id){
        userProfile = new UserProfile();
        try{
            Log.d("USER DATA", SharedData.userId + " ");
            Request request = new Request.Builder()
                    .url(webServiceURL + "user/" + SharedData.userId)
                    .get()
                    .addHeader("Cookie", "Authorization=" + DataAccess.authToken)
                    .build();

            Response response = DataAccess.client.newCall(request).execute();
            JSONObject jResponse = new JSONObject(response.body().string());
            JSONObject jData = new JSONObject(jResponse.getJSONObject("data").toString());
            userProfile.setId(jData.getInt("ID"));
            userProfile.setBio(jData.getString("Bio"));
            userProfile.setEmail(jData.getString("Email"));
            userProfile.setUsername(jData.getString("Username"));

        } catch (Exception e){

            e.printStackTrace();
        }
        return userProfile;
    }

    public static List<Route> getAllRoutes(){
        routes = new ArrayList<Route>();
        try{
            Request request = new Request.Builder()
                    .url(webServiceURL + "routes")
                    .get()
                    .addHeader("Cookie", "Authorization=" + DataAccess.authToken)
                    .build();

            Response response = DataAccess.client.newCall(request).execute();
            JSONObject jResponse = new JSONObject(response.body().string());
            JSONArray jArray = new JSONArray(jResponse.getJSONArray("data").toString());
            if(jArray != null && jArray.length() > 0){
                for(int i = 0; i < jArray.length(); i++){
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    int id = jsonObject.getInt("ID");
                    String type = jsonObject.getString("Type");
                    String name = jsonObject.getString("Name");
                    String difficulty = jsonObject.getString("Difficulty");
                    String description = jsonObject.getString("Description");
                    String location = jsonObject.getString("Location");
                    int userId = jsonObject.getInt("UserProfileID");

                    Route route = new Route(id, type, name, difficulty, description, location, userId);
                    routes.add(route);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return routes;
    }

    public static int addNewRoute(Route route) {
        int routeId = 0;
        try {
            String json = new JSONObject()
                    .put("Id", route.getId())
                    .put("Name", route.getName())
                    .put("Difficulty", route.getDifficulty())
                    .put("Description", route.getDescription())
                    .put("Location", route.getLocation())
                    .put("UserProfileID", SharedData.userId)
                    .put("Type", route.getType())
                    .toString();

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

            Request request = new Request.Builder()
                    .url(webServiceURL + "route/new")
                    .post(body)
                    .addHeader("Cookie", "Authorization=" + DataAccess.authToken)
                    .build();

            Response response = DataAccess.client.newCall(request).execute();

            JSONObject jResponse = new JSONObject(response.body().string());

            JSONObject data = jResponse.getJSONObject("data");

            routeId =  data.getInt("ID");
            return routeId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routeId;
    }
    /*
    * JSONObject jResponse = new JSONObject(response.body().string());
            JSONArray jArray = new JSONArray(jResponse.getJSONArray("data").toString());
            if(jArray != null && jArray.length() > 0){
                for(int i = 0; i < jArray.length(); i++){
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    int id = jsonObject.getInt("ID");
                    String type = jsonObject.getString("Type");
                    String name = jsonObject.getString("Name");
                    String difficulty = jsonObject.getString("Difficulty");
                    String description = jsonObject.getString("Description");
                    String location = jsonObject.getString("Location");
                    int userId = jsonObject.getInt("UserProfileID");

                    Route route = new Route(id, type, name, difficulty, description, location, userId);
                    routes.add(route);
                }
    * */
    public static List<Climb> fetchClimbs(){
        climbs = new ArrayList<>();
        try {
            Request request = new Request.Builder()
                    .url(webServiceURL + "climbs")
                    .get()
                    .addHeader("Cookie", "Authorization=" + DataAccess.authToken)
                    .build();

            Response response = DataAccess.client.newCall(request).execute();
            JSONObject jResponse = new JSONObject(response.body().string());
            JSONArray jArray = new JSONArray(jResponse.getJSONArray("data").toString());
            if (jArray != null && jArray.length() > 0){
                for(int i = 0; i < jArray.length(); i++){
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    int routeId = jsonObject.getInt("RouteID");
                    int id = jsonObject.getInt("ID");
                    int userId = jsonObject.getInt("UserProfileID");
                    String comment = jsonObject.getString("Comment");
                    LocalDateTime date = LocalDateTime.parse(jsonObject.getString("Date"), formatter);

                    Climb climb = new Climb(id, date, comment, routeId, userId);
                    climbs.add(climb);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return climbs;
    }


    public static int addNewClimb(Climb climb, int routeId, int userId){
        int climbId = 0 ;
        try{
            Log.d("USER DATA ADD NEW CLIMB", climb.getUserProfileId() + "  " + climb.getRouteId());
            String json = new JSONObject()
                    .put("Id", climb.getId())
                    .put("Comment", climb.getComment())
                    .put("Date", climb.getDate().toString())
                    .put("RouteId", routeId)
                    .put("user_id", userId)
                    .toString();
            Log.d("JSON DATA", json);

            Log.d("USER DATA ADD NEW CLIMB", SharedData.routeId + "  " + SharedData.userId);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
            Request request = new Request.Builder()
                    .url(webServiceURL + "climb/new")
                    .post(body)
                    .addHeader("Cookie", "Authorization=" + DataAccess.authToken)
                    .build();

            Log.d("REQUEST BODY", request.toString());
            Response response = DataAccess.client.newCall(request).execute();

            JSONObject jResponse = new JSONObject(response.body().string());

            JSONObject data = jResponse.getJSONObject("data");
            climbId = data.getInt("ID");
        } catch (Exception e){
            e.printStackTrace();
        }
        return climbId;
    }
}
