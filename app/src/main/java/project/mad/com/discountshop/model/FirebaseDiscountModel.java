package project.mad.com.discountshop.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import project.mad.com.discountshop.Constant;
import project.mad.com.discountshop.Presenter.IDiscountPresenter;
import project.mad.com.discountshop.View.IDiscountView;

public class FirebaseDiscountModel implements IDiscountPresenter{

    private IDiscountView mIDiscountView;
    //private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    //private String mUid = mUser.getUid();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference(Constant.KEY_PRODUCT);
    //private DatabaseReference userRef = usersRef.child(mUid);


    public FirebaseDiscountModel(IDiscountView IDiscountView) {
        mIDiscountView = IDiscountView;
    }

    @Override
    public void input(String name, String brand, String capacity, Integer discount, Integer countdown) {
//        String newPostId = userRef.push().getKey();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(brand) || TextUtils.isEmpty(capacity) || TextUtils.isEmpty(discount.toString()) || TextUtils.isEmpty(countdown.toString())){
            mIDiscountView.showValidationError();
        }else{
            Map<String, Object> post = new HashMap<>();
            post.put(Constant.KEY_NAME, name);
            post.put(Constant.KEY_BRAND, brand);
            post.put(Constant.KEY_CAPACITY, capacity);
            post.put(Constant.KEY_DISCOUNT, discount);
            post.put(Constant.KEY_DATE, countdown);
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
        }
    }
}
