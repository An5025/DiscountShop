package project.mad.com.discountshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * this activity includes three fragments, add barcode, add product, add shop
 */
public class AddActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "AddActivity";
    private static final String TAB1 = "barcode";
    private static final String TAB2 = "product";
    private static final String TAB3 = "shop";

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Log.d(TAG, "onCreate: starting");

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        BottomNavigationView bottomNav = findViewById(R.id.navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Menu mMenu = bottomNav.getMenu();
        MenuItem menuItem = mMenu.getItem(2);
        menuItem.setChecked(true);
    }

    /**
     * this method set three fragments at activity top navigation
     * @param viewPager
     */
    public void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new BarcodeFragment(), TAB1);
        adapter.addFragment(new ProductFragment(), TAB2);
        adapter.addFragment(new ShopFragment(), TAB3);
        viewPager.setAdapter(adapter);
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
                            Intent intent1 = new Intent(AddActivity.this, ShopsActivity.class);
                            startActivity(intent1);
                            break;
                        case R.id.bottom_product:
                            Intent intent2 = new Intent(AddActivity.this, ProductsActivity.class);
                            startActivity(intent2);
                            break;
                        case R.id.bottom_add_info:
                            break;
                        case R.id.bottom_user:
                            Intent intent4 = new Intent(AddActivity.this, UserActivity.class);
                            startActivity(intent4);
                            break;
                    }
                    return true;
                }
            };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //do nothing
    }
}

