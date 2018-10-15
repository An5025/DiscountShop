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
import project.mad.com.discountshop.contract.IBarcodePresenter;
import project.mad.com.discountshop.contract.IBarcodeView;

/**
 * FirebaseBarcodePresenter
 * save barcode fragment data to firebase
 */
public class FirebaseBarcodePresenter implements IBarcodePresenter{
    public Boolean isExist;
    private static final String TAG = "FirebaseBarcodePresente";
    private IBarcodeView mISaveDataView;
    private Query mSearchBarcode;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference(Constants.KEY_BARCODE);

    /**
     * FirebaseBarcodePresenter constructore
     * initialize mISaveDataView
     * @param ISaveDataView an interface
     */
    public FirebaseBarcodePresenter(IBarcodeView ISaveDataView) {
        mISaveDataView = ISaveDataView;
    }


    @Override
    public void input(String barcode, String name, String brand, String capacity) {
        if(TextUtils.isEmpty(barcode) || TextUtils.isEmpty(brand) || TextUtils.isEmpty(capacity) || TextUtils.isEmpty(name)){
            mISaveDataView.showValidationError();
        }else{
            if (isExist) {
                mISaveDataView.exist();
            } else {
                if (TextUtils.getTrimmedLength(barcode) < 10) {
                    mISaveDataView.inputInvalid();
                } else {
                    Map<String, Object> post = new HashMap<>();
                    post.put(Constants.KEY_BARCODE, barcode);
                    post.put(Constants.KEY_NAME, name);
                    post.put(Constants.KEY_BRAND, brand);
                    post.put(Constants.KEY_CAPACITY, capacity);
                    productRef.push().setValue(post);
                    productRef.addValueEventListener(new ValueEventListener() {
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

    @Override
    public void searchBarcode(String barcode) {
        mSearchBarcode = database.getReference(Constants.KEY_BARCODE).orderByChild(Constants.KEY_BARCODE).equalTo(barcode);
        mSearchBarcode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    isExist = true;
                }else{
                    isExist = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

