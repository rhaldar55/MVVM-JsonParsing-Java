package com.rh.mvvm_jsonparsing_java.apiUtils;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rh.mvvm_jsonparsing_java.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSONParser {

    public static List<Movie> getMovieList(String jsonString) throws JSONException {

        JSONObject responseJson;
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        responseJson = new JSONObject(jsonString);

        Log.e("TAG", responseJson.toString());

        return gson.fromJson(responseJson.getJSONArray("results").toString(),
                new TypeToken<List<Movie>>(){}.getType());


    }

}
