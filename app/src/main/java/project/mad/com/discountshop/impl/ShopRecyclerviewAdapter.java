package project.mad.com.discountshop.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import project.mad.com.discountshop.presenter.ShopsAdapter;
import project.mad.com.discountshop.R;
import project.mad.com.discountshop.Shop;
import project.mad.com.discountshop.ShopsActivity;

public class ShopRecyclerviewAdapter extends RecyclerView.Adapter<ShopsViewHolder> {
    private final ShopsAdapter mShopsPresenter;
    private Context mContext;

    public ShopRecyclerviewAdapter(ShopsAdapter shopsPresenter, ArrayList<Shop> shops, ShopsActivity shopsActivity) {
        mShopsPresenter = shopsPresenter;
        mContext = shopsActivity;
    }

    @NonNull
    @Override
    public ShopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsViewHolder holder, int position) {
        mShopsPresenter.onBindShopData(position, holder);
    }

    @Override
    public int getItemCount() {
        return mShopsPresenter.getShopCount();
    }
}

//https://android.jlelse.eu/recyclerview-in-mvp-passive-views-approach-8dd74633158