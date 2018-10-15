package project.mad.com.discountshop.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.mad.com.discountshop.Constants;
import project.mad.com.discountshop.contract.IDiscountShopPresenter;
import project.mad.com.discountshop.contract.IShopView;
import project.mad.com.discountshop.data.Product;
import project.mad.com.discountshop.data.Shop;

/**
 * FirebaseShopPresenter
 * save shop fragment data to firebase
 */
public class FirebaseShopPresenter implements IDiscountShopPresenter{
    private static final String TAG = "FirebaseShopPresenter";
    private IShopView mIDiscountShopView;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
//    private String mUid = mUser.getUid();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference shopRef = database.getReference(Constants.KEY_SHOP);
    private ArrayList<Shop> mShops = new ArrayList<Shop>();
    Query shopsRef;

    /**
     * FirebaseShopPresenter constructor
     * initialize mIDiscountShopView
     * @param IDiscountShopView
     */
    public FirebaseShopPresenter(IShopView IDiscountShopView) {
        mIDiscountShopView = IDiscountShopView;
    }

    @Override
    public void input(String name, Integer discount, String date) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(discount.toString()) || TextUtils.isEmpty(date)){
            mIDiscountShopView.showValidationError();
        }else if (discount>Constants.KEY_MIN && discount<Constants.KEY_MAX){
            Map<String, Object> post = new HashMap<>();
            post.put(Constants.KEY_NAME, name);
            post.put(Constants.KEY_DISCOUNT, discount);
            post.put(Constants.KEY_DATE, date);
//            post.put("user", mUid);
            shopRef.push().setValue(post);
            shopRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mIDiscountShopView.inputSuccess();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mIDiscountShopView.inputError();
                    Log.d(TAG, databaseError.toString());
                }
            });
        }else{
            mIDiscountShopView.inputInvalid();
        }
    }

    @Override
    public ArrayList<Shop> getFirebaseData() {
        shopsRef = FirebaseDatabase.getInstance().getReference().child(Constants.KEY_SHOP).orderByChild("votes");
        shopsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Product shop = dataSnapshot1.getValue(Product.class);
                    mShops.add(shop);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mIDiscountShopView.showValidationError();
            }
        });

        return mShops;
    }
}
