package project.mad.com.discountshop.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import project.mad.com.discountshop.R;
import project.mad.com.discountshop.View.IShopsRecyclerView;

public class ShopsViewHolder extends RecyclerView.ViewHolder implements IShopsRecyclerView{
    TextView shop_name, shop_discount, shop_countdown, shop_votes;
    Context mContext;

    public ShopsViewHolder(View itemView) {
        super(itemView);

        shop_name = itemView.findViewById(R.id.shop_name);
        shop_discount = itemView.findViewById(R.id.shop_disc);
        shop_countdown = itemView.findViewById(R.id.shop_countdown);
        shop_votes = itemView.findViewById(R.id.shop_votes);
    }

    @Override
    public void setShopName(String name) {
        shop_name.setText(name);
    }

    @Override
    public void setShopDiscount(int discount) {
        shop_discount.setText(String.format(mContext.getString(R.string.integer),discount));
    }

    @Override
    public void setShopCountdown(int countdown) {
        shop_countdown.setText(String.format(mContext.getString(R.string.integer),countdown));
    }

    @Override
    public void setShopVotes(int votes) {
        shop_votes.setText(String.format(mContext.getString(R.string.integer),votes));
    }
}
