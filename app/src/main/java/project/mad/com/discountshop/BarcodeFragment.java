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

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import project.mad.com.discountshop.view.IBarcodeView;
import project.mad.com.discountshop.impl.FirebaseBarcodePresenter;

public class BarcodeFragment extends Fragment implements IBarcodeView{
    private static final String TAG = "BarcodeFragment";
    EditText mName, mCapacity, mBrand, mBarcode;
    FirebaseBarcodePresenter mPresenter;
    Button submit_btn, scan_btn;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.barcode_fragment, container, false);

        mBarcode = view.findViewById(R.id.product_barcode_et);
        mName = view.findViewById(R.id.product_name_et);
        mBrand = view.findViewById(R.id.product_brand_et);
        mCapacity = view.findViewById(R.id.product_capacity_et);
        submit_btn = view.findViewById(R.id.barcode_confirm_btn);
        scan_btn = view.findViewById(R.id.scan_barcode_btn);
        mPresenter = new FirebaseBarcodePresenter(this);
        mView = view;


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.input(
                        mBarcode.getText().toString().trim(),
                        mName.getText().toString().trim(),
                        mBrand.getText().toString().trim(),
                        mCapacity.getText().toString().trim());
            }
        });

        scan_btn.setOnClickListener(new View.OnClickListener() {
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
                    mBarcode.setText(barcode.displayValue);
                }else{
                    mBarcode.setText(Constant.KEY_NO_BARCODE);
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showValidationError() {
        Snackbar.make(mView, R.string.input_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void inputSuccess() {
        Snackbar.make(mView, R.string.input_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void inputError() {
        Snackbar.make(mView, R.string.input_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void barcodeInvalid() {
        Snackbar.make(mView, R.string.input_invalid, Snackbar.LENGTH_LONG).show();
    }
}
