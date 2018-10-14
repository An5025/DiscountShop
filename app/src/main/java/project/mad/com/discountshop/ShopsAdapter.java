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

import project.mad.com.discountshop.data.Shop;
/**
 * ShopsAdapter
 * get shop data from array and set data for name, discount.....
 * set the shops from array to recyclerview
 */
public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ShopsViewHolder> {
    List<Shop> ShopsArray;
    private Context mContext;

    /**
     * ShopsAdapter Constructor
     * initialize array and context
     * @param shops
     * @param context
     */
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
        holder.shop_date.setText(shop.getDate());
        holder.shop_discount.setText(String.format(mContext.getString(R.string.integer), shop.getDiscount()));
    }

    @Override
    public int getItemCount() {
        return ShopsArray.size();
    }

    public class ShopsViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name, shop_discount, shop_date;
        public ShopsViewHolder(View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_discount = itemView.findViewById(R.id.shop_disc);
            shop_date = itemView.findViewById(R.id.shop_date);
        }
    }
}
