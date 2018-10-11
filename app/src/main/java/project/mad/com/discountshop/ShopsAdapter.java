package project.mad.com.discountshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ShopsViewHolder> {
    List<Shop> ShopsArray;
    private Context mContext;

    public ShopsAdapter(ArrayList<Shop> shops, Context context) {
        ShopsArray = shops;
        mContext = context;
    }

    @NonNull
    @Override
    public ShopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View shopView = LayoutInflater.from(mContext)
                .inflate(R.layout.shop_view, parent, false);
        return new ShopsViewHolder(shopView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsViewHolder holder, int position) {
        Shop shop = ShopsArray.get(position);
        holder.shop_name.setText(shop.getName());
        holder.shop_discount.setText(String.format(mContext.getString(R.string.integer), shop.getDiscount()));
        holder.shop_countdown.setText(String.format(mContext.getString(R.string.integer), shop.getCountdown()));
        holder.shop_votes.setText(String.format(mContext.getString(R.string.integer), shop.getVotes()));
    }

    @Override
    public int getItemCount() {
        return ShopsArray.size();
    }

    public class ShopsViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name, shop_discount, shop_countdown, shop_votes;
        public ShopsViewHolder(View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_discount = itemView.findViewById(R.id.shop_disc);
            shop_countdown = itemView.findViewById(R.id.shop_countdown);
            shop_votes = itemView.findViewById(R.id.shop_votes);
        }
    }
}