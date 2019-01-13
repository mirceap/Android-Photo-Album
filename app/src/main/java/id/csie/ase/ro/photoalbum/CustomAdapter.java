package id.csie.ase.ro.photoalbum;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private int[] _list;

    public CustomAdapter(Context mContext, int[] _list) {
        this.mContext = mContext;
        this._list = _list;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_custom_layout,
                viewGroup, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof PlaceViewHolder){
            ((PlaceViewHolder) viewHolder).mPlace.setImageResource(_list[position]);
            ((PlaceViewHolder) viewHolder).mPlace.setTag(_list[position]);
            ((PlaceViewHolder) viewHolder).textOnTop.setText(_list[position]);
            ((PlaceViewHolder) viewHolder).mPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailed = new Intent(mContext, DetailActivity.class);
                    detailed.putExtra("Image", _list[viewHolder.getAdapterPosition()]);
                    mContext.startActivity(detailed);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return _list.length;
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView mPlace;
        TextView textOnTop;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            mPlace = itemView.findViewById(R.id.imageView);
            textOnTop = itemView.findViewById(R.id.imageViewText);
        }
    }
}
