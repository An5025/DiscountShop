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
    private static final String TAG = "FirebaseProductPresente";
    private ISaveDataView mIDiscountView;
    private Query mSearchBarcode;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference(Constants.KEY_PRODUCT);

    /**
     * FirebaseProductPresenter
     * initialize mIDiscountView
     * @param IDiscountView an interface
     */
    public FirebaseProductPresenter(ISaveDataView IDiscountView) {
        mIDiscountView = IDiscountView;
    }

    @Override
    public void input(String name, String brand, String capacity, Integer discount, String date) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(brand) || TextUtils.isEmpty(capacity)
                || String.valueOf(discount).isEmpty() || TextUtils.isEmpty(date)){
            mIDiscountView.showValidationError();
        }else if (discount>Constants.KEY_MIN && discount<Constants.KEY_MAX){

            Map<String, Object> post = new HashMap<>();
            post.put(Constants.KEY_NAME, name);
            post.put(Constants.KEY_BRAND, brand);
            post.put(Constants.KEY_CAPACITY, capacity);
            post.put(Constants.KEY_DISCOUNT, discount);
            post.put(Constants.KEY_DATE, date);
            productRef.push().setValue(post);
            productRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mIDiscountView.inputSuccess();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mIDiscountView.inputError();
                    Log.e(TAG, databaseError.toString());
                }
            });
        }else{
            mIDiscountView.inputInvalid();
        }
    }

    @Override
    public void searchBarcode(final String barcode) {
        mSearchBarcode = database.getReference(Constants.KEY_BARCODE).orderByChild(Constants.KEY_BARCODE).equalTo(barcode);
        mSearchBarcode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot barcodeModel : dataSnapshot.getChildren()) {
                        String bName = barcodeModel.child(Constants.KEY_NAME).getValue().toString();
                        String bCapacity = barcodeModel.child(Constants.KEY_CAPACITY).getValue().toString();
                        String bBrand = barcodeModel.child(Constants.KEY_BRAND).getValue().toString();
                        mIDiscountView.showModelData(bName, bBrand, bCapacity);
                    }
                }else{
                    mIDiscountView.notExist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
