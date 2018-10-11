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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    List<Product> ProductsArray;
    private Context mContext;

    public ProductsAdapter(ArrayList<Product> products, Context context) {
        ProductsArray = products;
        mContext = context;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View shopView = LayoutInflater.from(mContext)
                .inflate(R.layout.product_view, parent, false);
        return new ProductsViewHolder(shopView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        Product product = ProductsArray.get(position);
        holder.product_name.setText(product.getName());
        holder.product_brand.setText(product.getBrand());
        holder.product_capacity.setText(product.getCapacity());
        holder.product_discount.setText(String.format(mContext.getString(R.string.integer), product.getDiscount()));
        holder.product_countdown.setText(String.format(mContext.getString(R.string.integer), product.getCountdown()));
        holder.product_votes.setText(String.format(mContext.getString(R.string.integer), product.getVotes()));
    }

    @Override
    public int getItemCount() {
        return ProductsArray.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_brand, product_capacity, product_discount, product_countdown, product_votes;
        public ProductsViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.prduct_name_tv);
            product_brand = itemView.findViewById(R.id.pr_brand_tv);
            product_capacity = itemView.findViewById(R.id.pr_capacity_tv);
            product_discount = itemView.findViewById(R.id.product_disc_tv);
            product_countdown = itemView.findViewById(R.id.pr_count_tv);
            product_votes = itemView.findViewById(R.id.pr_vote_tv);
        }
    }
}
