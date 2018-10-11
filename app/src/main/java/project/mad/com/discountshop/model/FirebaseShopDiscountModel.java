package project.mad.com.discountshop.model;

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
import project.mad.com.discountshop.Presenter.IDiscountShopPresenter;
import project.mad.com.discountshop.Product;
import project.mad.com.discountshop.Shop;
import project.mad.com.discountshop.View.IDiscountShopView;

public class FirebaseShopDiscountModel implements IDiscountShopPresenter{

    private IDiscountShopView mIDiscountShopView;
    //private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    //private String mUid = mUser.getUid();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference shopRef = database.getReference(Constant.KEY_SHOP);
    //private DatabaseReference userRef = usersRef.child(mUid);
    private ArrayList<Shop> mShops = new ArrayList<Shop>();
    Query shopsRef;

    public FirebaseShopDiscountModel(IDiscountShopView IDiscountShopView) {
        mIDiscountShopView = IDiscountShopView;
    }

    @Override
    public void input(String name, Integer discount, Integer countdown) {
//        String newPostId = userRef.push().getKey();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(discount.toString()) || TextUtils.isEmpty(countdown.toString())){
            mIDiscountShopView.showValidationError();
        }else{
            Map<String, Object> post = new HashMap<>();
            post.put(Constant.KEY_NAME, name);
            post.put(Constant.KEY_DISCOUNT, discount);
            post.put(Constant.KEY_DATE, countdown);
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
