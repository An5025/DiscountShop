package project.mad.com.discountshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import project.mad.com.discountshop.view.IDiscountView;
import project.mad.com.discountshop.impl.FirebaseDiscountPresenter;

public class ProductFragment extends Fragment implements IDiscountView{
    private static final String TAG = "ProductFragment";
    EditText mName, mCapacity, mBrand, mDate, mDiscount;
    int discount;
    FirebaseDiscountPresenter mPresenter;
    Button mSubmit_btn, mScan_btn;
    String mBarcodeValue;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.product_fragment, container, false);

        mName = view.findViewById(R.id.name_et);
        mBrand = view.findViewById(R.id.brand_et);
        mCapacity = view.findViewById(R.id.capacity_et);
        mDiscount = view.findViewById(R.id.discount_et);
        mDate = view.findViewById(R.id.date_et);
        mSubmit_btn = view.findViewById(R.id.discount_confirm_btn);
        mScan_btn = view.findViewById(R.id.scan_btn);
        mPresenter = new FirebaseDiscountPresenter(this);
        mView = view;


        mSubmit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discount = Integer.parseInt(mDiscount.getText().toString().trim());
                mPresenter.input(mName.getText().toString().trim(),
                                mBrand.getText().toString().trim(),
                                mCapacity.getText().toString().trim(),discount,
                                mDate.getText().toString().trim()
                                );
            }
        });

        mScan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanBarcodeActivity.class);
                startActivityForResult(intent, Constant.KEY_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.KEY_CODE){
            if (resultCode == CommonStatusCodes.SUCCESS){
                if (data != null){
                    Barcode barcode = data.getParcelableExtra(Constant.KEY_BARCODE);
                    mBarcodeValue = barcode.displayValue;
                }else{
                    Snackbar.make(mView, Constant.KEY_NO_BARCODE, Snackbar.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showValidationError() {
        Snackbar.make(mView, R.string.input_invalid, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void inputSuccess() {
        Snackbar.make(mView, R.string.input_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void inputError() {
        Snackbar.make(mView, R.string.input_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void discountInvalid() {
        Toast.makeText(getActivity(), R.string.discount_invalid, Toast.LENGTH_SHORT).show();
    }
}
