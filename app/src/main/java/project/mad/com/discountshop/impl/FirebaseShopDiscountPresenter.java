package project.mad.com.discountshop.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.mad.com.discountshop.Constant;
import project.mad.com.discountshop.presenter.IDiscountShopPresenter;
import project.mad.com.discountshop.Product;
import project.mad.com.discountshop.Shop;
import project.mad.com.discountshop.view.IDiscountShopView;

public class FirebaseShopDiscountPresenter implements IDiscountShopPresenter{

    private IDiscountShopView mIDiscountShopView;
    //private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    //private String mUid = mUser.getUid();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference shopRef = database.getReference(Constant.KEY_SHOP);
    //private DatabaseReference userRef = usersRef.child(mUid);
    private ArrayList<Shop> mShops = new ArrayList<Shop>();
    Query shopsRef;

    public FirebaseShopDiscountPresenter(IDiscountShopView IDiscountShopView) {
        mIDiscountShopView = IDiscountShopView;
    }

    @Override
    public void input(String name, Integer discount, String date) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(discount.toString()) || TextUtils.isEmpty(date)){
            mIDiscountShopView.showValidationError();
        }else if (discount>0 && discount<100){
            Map<String, Object> post = new HashMap<>();
            post.put(Constant.KEY_NAME, name);
            post.put(Constant.KEY_DISCOUNT, discount);
            post.put(Constant.KEY_DATE, date);
            shopRef.push().setValue(post); //should be userRef
            shopRef.addValueEventListener(new ValueEventListener() {//should be userRef
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mIDiscountShopView.inputSuccess();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mIDiscountShopView.inputError();
                    Log.e("onCancelled: ", databaseError.toString());
                }
            });
        }else{
            mIDiscountShopView.discountInvalid();
        }
    }

    @Override
    public ArrayList<Shop> getFirebaseData() {
        shopsRef = FirebaseDatabase.getInstance().getReference().child(Constant.KEY_SHOP).orderByChild("votes");
        shopsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Product shop = dataSnapshot1.getValue(Product.class);
                    mShops.add(shop);
                }
  //              mShopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
 //               Snackbar.make(mView, getString(R.string.onCancelled), Snackbar.LENGTH_SHORT).show();
            }
        });

        return mShops;
    }
}
