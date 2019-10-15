package com.example.musixmatch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrackAdapter   extends RecyclerView.Adapter<TrackAdapter.ViewHolder>{

    ArrayList<Track> mData ;
    Context context ;

    public TrackAdapter(ArrayList<Track> mData , Context context) {
        this.mData = mData;
        this.context  = context ;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item, parent, false);

        ViewHolder  viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = mData.get(position);

        holder.tv_track_name.setText("Track: "+track.track_name);
        holder.tv_album_name.setText("Album: "+track.album_name);
        holder.tv_artist_name.setText("Artist: "+track.artist_name);
        String date_tv = track.updated_time.split("T")[0].split("-")[1]+"-"+
                track.updated_time.split("T")[0].split("-")[2]+"-"+
                track.updated_time.split("T")[0].split("-")[0];
        holder.tv_updated_time.setText("Time: "+date_tv);
        holder.track = track;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  tv_track_name , tv_album_name , tv_artist_name,tv_updated_time;
        Track track ;

        public ViewHolder(@NonNull View itemView  ) {
            super(itemView);

            tv_track_name =  itemView.findViewById(R.id.textDisplayTrack);
            tv_album_name =  itemView.findViewById(R.id.textDisplayAlbum);
            tv_artist_name =  itemView.findViewById(R.id.textDisplayArtist);
            tv_updated_time =  itemView.findViewById(R.id.textDisplayDate);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //
                    //
                    // Log.d("Demo" , track+""  );
//

                  //  Intent intent = new Intent(context, WebViewActivity.class);

//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(MainActivity.URL_TAG , track.track_share_url);
//                    context.startActivity(intent);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(track.track_share_url));
                   context. startActivity(intent);

                }
            });
        }
    }

}
