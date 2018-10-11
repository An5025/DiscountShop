package project.mad.com.discountshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopsActivity extends AppCompatActivity {
    private static final String TAG = "ShopsActivity";
    RecyclerView mShopRecyclerView;
    private ArrayList<Shop> mShops = new ArrayList<Shop>();
    private ShopsAdapter mShopAdapter;
    Query shopsRef;
    boolean disc_order = false, count_oder = false, vote_order = false;
    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Initialize the recyclerView
        mShopRecyclerView = findViewById(R.id.shopsRecyclerView);
        mShopAdapter = new ShopsAdapter(mShops, this);
        mShopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShopRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mShopRecyclerView.setAdapter(mShopAdapter);
        mShopRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.recyclerview_divider));
        getDataFirebase();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.bottom_shop:
                            break;
                        case R.id.bottom_product:
                            Intent intent2 = new Intent(ShopsActivity.this, ProductsActivity.class);
                            startActivity(intent2);
                            break;
                        case R.id.bottom_user:
                            Intent intent3 = new Intent(ShopsActivity.this, UserActivity.class);
                            startActivity(intent3);
                            break;
                    }
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.orderByDiscount) {
            disc_order = true;
            Toast.makeText(this, "click 1", Toast.LENGTH_SHORT).show();
            return disc_order;
        }else if (id == R.id.orderByDate) {
            count_oder = true;
            Toast.makeText(this, "click 2", Toast.LENGTH_SHORT).show();
            return count_oder;
        }else if (id == R.id.orderByCredit) {
            vote_order = true;
            Toast.makeText(this, "click 3", Toast.LENGTH_SHORT).show();
            return vote_order;
        }

        return super.onOptionsItemSelected(item);
    }

    void getDataFirebase(){

        shopsRef = FirebaseDatabase.getInstance().getReference().child(Constant.KEY_SHOP);

//        shopsRef = FirebaseDatabase.getInstance().getReference().child(Constant.KEY_SHOP).orderByChild("discount");
//
//        shopsRef = FirebaseDatabase.getInstance().getReference().child(Constant.KEY_SHOP).orderByChild("countdown");
//
//        shopsRef = FirebaseDatabase.getInstance().getReference().child(Constant.KEY_SHOP).orderByChild("votes");


        shopsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Product shop = dataSnapshot1.getValue(Product.class);
                    mShops.add(shop);
                }
                mShopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(mView, getString(R.string.onCancelled), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
