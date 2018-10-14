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
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import project.mad.com.discountshop.Constants;
import project.mad.com.discountshop.contract.IDiscountPresenter;
import project.mad.com.discountshop.contract.ISaveDataView;
/**
 * FirebaseProductPresenter
 * save product fragment data to firebase
 */
public class FirebaseProductPresenter implements IDiscountPresenter{

    private ISaveDataView mIDiscountView;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
//    private String mUid = mUser.getUid();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference(Constants.KEY_PRODUCT);

    /**
     * FirebaseProductPresenter
     * initialize mIDiscountView
     * @param IDiscountView
     */
    public FirebaseProductPresenter(ISaveDataView IDiscountView) {
        mIDiscountView = IDiscountView;
    }

    @Override
    public void input(String name, String brand, String capacity, Integer discount, String date) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(brand) || TextUtils.isEmpty(capacity)
                || TextUtils.isEmpty(String.valueOf(discount)) || TextUtils.isEmpty(date)){

            mIDiscountView.showValidationError();

        }else if (discount>Constants.KEY_MIN && discount<Constants.KEY_MAX){

            Map<String, Object> post = new HashMap<>();
            post.put(Constants.KEY_NAME, name);
            post.put(Constants.KEY_BRAND, brand);
            post.put(Constants.KEY_CAPACITY, capacity);
            post.put(Constants.KEY_DISCOUNT, discount);
            post.put(Constants.KEY_DATE, date);
//            post.put(Constants.KEY_UID, mUid);
            productRef.push().setValue(post); //should be userRef
            productRef.addValueEventListener(new ValueEventListener() {//should be userRef
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mIDiscountView.inputSuccess();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mIDiscountView.inputError();
                    Log.e("onCancelled: ", databaseError.toString());
                }
            });
        }else{
            mIDiscountView.inputInvalid();
        }
    }
}
