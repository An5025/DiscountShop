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

import project.mad.com.discountshop.Constant;
import project.mad.com.discountshop.presenter.IBarcodePresenter;
import project.mad.com.discountshop.view.IBarcodeView;

public class FirebaseBarcodePresenter implements IBarcodePresenter{

    private IBarcodeView mIBarcodeView;
    //private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    //private String mUid = mUser.getUid();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference productRef = database.getReference(Constant.KEY_BARCODE);
    //private DatabaseReference userRef = usersRef.child(mUid);


    public FirebaseBarcodePresenter(IBarcodeView IBarcodeView) {
        mIBarcodeView = IBarcodeView;
    }

    @Override
    public void input(String barcode, String name, String brand, String capacity) {
        if(TextUtils.isEmpty(barcode) || TextUtils.isEmpty(brand) || TextUtils.isEmpty(capacity) || TextUtils.isEmpty(name)){
            mIBarcodeView.showValidationError();
        }else{
            if(TextUtils.getTrimmedLength(barcode) < 10){
                mIBarcodeView.barcodeInvalid();
            }else{
                Map<String, Object> post = new HashMap<>();
                post.put(Constant.KEY_BARCODE, barcode);
                post.put(Constant.KEY_NAME, name);
                post.put(Constant.KEY_BRAND, brand);
                post.put(Constant.KEY_CAPACITY, capacity);
                productRef.push().setValue(post); //should be userRef
                productRef.addValueEventListener(new ValueEventListener() {//should be userRef
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mIBarcodeView.inputSuccess();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mIBarcodeView.inputError();
                        Log.e("onCancelled: ", databaseError.toString());
                    }
                });
            }
        }
    }
}

