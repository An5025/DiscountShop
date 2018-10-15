package project.mad.com.discountshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import project.mad.com.discountshop.contract.IBarcodeView;
import project.mad.com.discountshop.impl.FirebaseBarcodePresenter;

/**
 * Barcode fragment
 * use barcode scanner to scan barcode
 * input barcode name, brand, capacity and save this barcode data to firebase
 */
public class BarcodeFragment extends Fragment implements IBarcodeView {
    private static final String TAG = "BarcodeFragment";
    private EditText mName, mCapacity, mBrand, mBarcode;
    private FirebaseBarcodePresenter mPresenter;
    private Button mSubmitBtn, mScanBtn;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.barcode_fragment, container, false);

        mBarcode = view.findViewById(R.id.product_barcode_et);
        mName = view.findViewById(R.id.product_name_et);
        mBrand = view.findViewById(R.id.product_brand_et);
        mCapacity = view.findViewById(R.id.product_capacity_et);
        mSubmitBtn = view.findViewById(R.id.barcode_confirm_btn);
        mScanBtn = view.findViewById(R.id.scan_barcode_btn);
        mPresenter = new FirebaseBarcodePresenter(this);
        mView = view;


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.searchBarcode(mBarcode.getText().toString().trim());
                if (mPresenter.isExist != null) {
                    mPresenter.input(
                            mBarcode.getText().toString().trim(),
                            mName.getText().toString().trim(),
                            mBrand.getText().toString().trim(),
                            mCapacity.getText().toString().trim());
                }
            }
        });

        //scan barcode
        mScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanBarcodeActivity.class);
                startActivityForResult(intent, Constants.KEY_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.KEY_CODE){
            if (resultCode == CommonStatusCodes.SUCCESS){
                if (data != null){
                    Barcode barcode = data.getParcelableExtra(Constants.KEY_BARCODE);
                    mBarcode.setText(barcode.displayValue);
                }else{
                    mBarcode.setText(getString(R.string.no_barcode));
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showValidationError() {
        Toast.makeText(getActivity(), R.string.input_invalid, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void inputSuccess() {
        Toast.makeText(getActivity(), R.string.input_success, Toast.LENGTH_SHORT ).show();
        mBarcode.setText(getString(R.string.empty));
        mName.setText(getString(R.string.empty));
        mBrand.setText(getString(R.string.empty));
        mCapacity.setText(getString(R.string.empty));
    }

    @Override
    public void inputError() {
        Toast.makeText(getActivity(), R.string.input_success, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void inputInvalid() {
        Toast.makeText(getActivity(), R.string.input_invalid, Toast.LENGTH_SHORT ).show();
    }

    /**
     * if barcode exist, user cannot add it again
     */
    @Override
    public void exist() {
        Toast.makeText(getActivity(), R.string.barcode_exist, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void databaseError() {
        Toast.makeText(getActivity(), R.string.databaseError, Toast.LENGTH_SHORT ).show();
    }
}
