package com.mallock.bicyclelending;
        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import java.util.ArrayList;
        import java.util.List;
/**
 * Created by vrs on 3/9/15.
 */
public class DescriptionCardAdapter extends RecyclerView.Adapter<DescriptionCardAdapter.ViewHolder> {
    List<Stand> list = new ArrayList<>();
    public DescriptionCardAdapter(Stand stand) {
        this.list.clear();
        this.list.add(stand);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public Stand getItem(int i) {
        return list.get(i);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.description_card_view, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.stand=getItem(position);
        holder.cardTitle.setText(list.get(position).standName);
        if(list.get(position).available())
            holder.cardImage.setImageResource(R.drawable.available);
        else
            holder.cardImage.setImageResource(R.drawable.unavailable);
        holder.cardDescription.setText(list.get(position).description);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView cardTitle;
        TextView cardDescription;
        Stand stand;
        public ViewHolder(View itemView) {
            super(itemView);
            cardImage = (ImageView) itemView.findViewById(R.id.cardimage);
            cardTitle = (TextView) itemView.findViewById(R.id.cardtitle);
            cardDescription=(TextView) itemView.findViewById(R.id.cardtext);
        }
    }
}