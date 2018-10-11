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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    RecyclerView mProductRecyclerView;
    private ArrayList<Product> mProducts = new ArrayList<Product>();
    private ProductsAdapter mProductsAdapter;
    Query productsRef;
    View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        BottomNavigationView bottomNav = findViewById(R.id.navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        mProductRecyclerView = findViewById(R.id.productsRecyclerView);
        mProductRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mProductsAdapter = new ProductsAdapter(mProducts, this);
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProductRecyclerView.setAdapter(mProductsAdapter);

        getDataFirebase();
    }

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
                        case R.id.bottom_user:
                            Intent intent3 = new Intent(ProductsActivity.this, UserActivity.class);
                            startActivity(intent3);
                            break;

                    }
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    void getDataFirebase(){
        productsRef = FirebaseDatabase.getInstance().getReference().child(Constant.KEY_PRODUCT);
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Product product = dataSnapshot1.getValue(Product.class);
                    mProducts.add(product);
                }
                mProductsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(mView, getString(R.string.onCancelled), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
