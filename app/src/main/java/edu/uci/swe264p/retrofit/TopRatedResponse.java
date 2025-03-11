package edu.uci.swe264p.retrofit;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TopRatedResponse {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
