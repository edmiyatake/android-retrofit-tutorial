package edu.uci.swe264p.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private List<Movie> movieList;
    private Context context;

    MovieListAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, releaseDate, voteAverage, overview;
        ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            releaseDate = itemView.findViewById(R.id.tvReleaseDate);
            voteAverage = itemView.findViewById(R.id.txtVote);
            overview = itemView.findViewById(R.id.tvOverview);
            poster = itemView.findViewById(R.id.ivMovie);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.releaseDate.setText(movie.getReleaseDate());
        holder.voteAverage.setText(String.valueOf(movie.getVoteAverage()));
        holder.overview.setText(movie.getOverview());

        // load poster using Picasso
        String posterUrl = "https://image.tmdb.org/t/p/w500/POSTER_PATH";
        Picasso.get().load(posterUrl).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

}
