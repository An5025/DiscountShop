package project.mad.com.discountshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import project.mad.com.discountshop.data.Product;

/**
 * ProductsActivity
 * retrieve all products' mDiscount2 information
 * dispaly them by recyclerview
 */
public class ProductsActivity extends AppCompatActivity {
    private RecyclerView mProductRecyclerView;
    private ArrayList<Product> mProducts = new ArrayList<Product>();
    private ProductsAdapter mProductsAdapter;
    private ProgressBar mProgressBar;
    private Query mProductsRef;
    private View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        BottomNavigationView bottomNav = findViewById(R.id.navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Menu mMenu = bottomNav.getMenu();
        MenuItem menuItem = mMenu.getItem(1);
        menuItem.setChecked(true);

        mProductRecyclerView = findViewById(R.id.productsRecyclerView);
        mProductRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mProductsAdapter = new ProductsAdapter(mProducts, this);
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProductRecyclerView.setAdapter(mProductsAdapter);
        mProgressBar = findViewById(R.id.product_progress);
        mProgressBar.setVisibility(View.VISIBLE);

        //retrieve product data by reference
        mProductsRef = FirebaseDatabase.getInstance().getReference().child(Constants.KEY_PRODUCT);
        getDataFirebase(mProductsRef);
    }

    /**
     * this method setup four activities at the bottom navigation
     * control the activityies' shifting
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.bottom_shop:
                            Intent intent1 = new Intent(ProductsActivity.this, ShopsActivity.class);
                            startActivity(intent1);
                            break;
                        case R.id.bottom_product:
                            break;
                        case R.id.bottom_add_info:
                            Intent intent3 = new Intent(ProductsActivity.this, AddActivity.class);
                            startActivity(intent3);
                            break;
                        case R.id.bottom_user:
                            Intent intent4 = new Intent(ProductsActivity.this, UserActivity.class);
                            startActivity(intent4);
                            break;

                    }
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.orderByProductDiscount) {
            destroyArray();
            getDataFirebase(mProductsRef.orderByChild(Constants.KEY_DISCOUNT));
        }else if (id == R.id.orderByProductName) {
            destroyArray();
            getDataFirebase(mProductsRef.orderByChild(Constants.KEY_NAME));
        }else if (id == R.id.orderByProductBrand) {
            destroyArray();
            getDataFirebase(mProductsRef.orderByChild(Constants.KEY_BRAND));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * use products reference to get product information and save then to a list
     */
    private void getDataFirebase(Query productRef){
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Product product = dataSnapshot1.getValue(Product.class);
                    mProducts.add(product);
                }
                mProductsAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(mView, getString(R.string.error), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * destroy current recyclerview array
     */
    private void destroyArray(){
        mProductsAdapter.productsArray.clear();
        mProductsAdapter.notifyDataSetChanged();
    }

}
