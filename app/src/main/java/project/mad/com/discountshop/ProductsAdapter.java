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

import project.mad.com.discountshop.data.Product;

/**
 * ProductsAdapter
 * get product data from array and set data for name, brand.....
 * set the products from array to recyclerview
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    private List<Product> mProductsArray;
    private Context mContext;

    /**
     * ProductsAdapter constructor
     * initialize array and context
     * @param products
     * @param context
     */
    public ProductsAdapter(ArrayList<Product> products, Context context) {
        mProductsArray = products;
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
        Product product = mProductsArray.get(position);
        holder.product_name.setText(product.getName());
        holder.product_brand.setText(product.getBrand());
        holder.product_capacity.setText(product.getCapacity());
        holder.product_discount.setText(String.format(mContext.getString(R.string.integer), product.getDiscount()));
        holder.product_date.setText(product.getDate());
    }

    @Override
    public int getItemCount() {
        return mProductsArray.size();
    }

    /**
     * ProductsViewHolder
     * set all variable with id
     */
    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, product_brand, product_capacity, product_discount, product_date;
        public ProductsViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.prduct_name_tv);
            product_brand = itemView.findViewById(R.id.pr_brand_tv);
            product_capacity = itemView.findViewById(R.id.pr_capacity_tv);
            product_discount = itemView.findViewById(R.id.product_disc_tv);
            product_date = itemView.findViewById(R.id.pr_date_tv);
        }
    }
}
