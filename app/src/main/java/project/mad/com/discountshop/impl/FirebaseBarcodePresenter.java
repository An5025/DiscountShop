package project.mad.com.discountshop.impl;

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

import project.mad.com.discountshop.Constants;
import project.mad.com.discountshop.contract.IBarcodePresenter;
import project.mad.com.discountshop.contract.ISaveDataView;

/**
 * FirebaseBarcodePresenter
 * save barcode fragment data to firebase
 */
public class FirebaseBarcodePresenter implements IBarcodePresenter{
    private static final String TAG = "FirebaseBarcodePresente";

    private ISaveDataView mISaveDataView;
//    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
//    private String mUid = mUser.getUid();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference(Constants.KEY_BARCODE);

    /**
     * FirebaseBarcodePresenter constructore
     * initialize mISaveDataView
     * @param ISaveDataView
     */
    public FirebaseBarcodePresenter(ISaveDataView ISaveDataView) {
        mISaveDataView = ISaveDataView;
    }

    @Override
    public void input(String barcode, String name, String brand, String capacity) {
        if(TextUtils.isEmpty(barcode) || TextUtils.isEmpty(brand) || TextUtils.isEmpty(capacity) || TextUtils.isEmpty(name)){
            mISaveDataView.showValidationError();
        }else{
            if(TextUtils.getTrimmedLength(barcode) < 10){
                mISaveDataView.inputInvalid();
            }else{
                Map<String, Object> post = new HashMap<>();
                post.put(Constants.KEY_BARCODE, barcode);
                post.put(Constants.KEY_NAME, name);
                post.put(Constants.KEY_BRAND, brand);
                post.put(Constants.KEY_CAPACITY, capacity);
               // post.put(Constants.KEY_UID, mUid);
                productRef.push().setValue(post); //should be userRef
                productRef.addValueEventListener(new ValueEventListener() {//should be userRef
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mISaveDataView.inputSuccess();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mISaveDataView.inputError();
                        Log.d(TAG, databaseError.toString());
                    }
                });
            }
        }
    }
}

