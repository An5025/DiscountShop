package project.mad.com.discountshop;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.Query;

import java.util.Calendar;

import project.mad.com.discountshop.contract.ISaveDataView;
import project.mad.com.discountshop.impl.FirebaseProductPresenter;

/**
 * Product fragment
 * user can user barcode scanner,
 * if the product barcode has been stored in database, proeduct name, brand and capacity will be auto fill,
 * user only need to fill discount number and expiry date
 * Or user can input product discount information, product name, brand, capacity, discount and exxpiry date
 */
public class ProductFragment extends Fragment implements ISaveDataView{
    private static final String TAG = "ProductFragment";
    EditText mName, mCapacity, mBrand, mDiscount;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    FirebaseProductPresenter mPresenter;
    Button mSubmit_btn, mScan_btn;
    String mBarcodeValue;
    Query mSearchBarcode;
    TextView mDate;
    int mDiscount2;
    View mView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.product_fragment, container, false);


        mName = view.findViewById(R.id.name_et);
        mBrand = view.findViewById(R.id.brand_et);
        mCapacity = view.findViewById(R.id.capacity_et);
        mDiscount = view.findViewById(R.id.discount_et);
        mDate = view.findViewById(R.id.date_tv);
        mSubmit_btn = view.findViewById(R.id.discount_confirm_btn);
        mScan_btn = view.findViewById(R.id.scan_btn);
        mPresenter = new FirebaseProductPresenter(this);
        mView = view;



        mSubmit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiscount2 = Integer.parseInt(mDiscount.getText().toString().trim());
                mPresenter.input(mName.getText().toString().trim(),
                        mBrand.getText().toString().trim(),
                        mCapacity.getText().toString().trim(), mDiscount2,
                        mDate.getText().toString().trim()
                );
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                        mOnDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                 Log.d(TAG,  year + getString(R.string.slash) + month + getString(R.string.slash)+ dayOfMonth);
                String date = getString(R.string.space)+dayOfMonth + getString(R.string.slash)+ month + getString(R.string.slash)+ year;
                mDate.setText(date);
            }
        };

        mScan_btn.setOnClickListener(new View.OnClickListener() {
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
                    mBarcodeValue = barcode.displayValue;
                }else{
                    Snackbar.make(mView, Constants.KEY_NO_BARCODE, Snackbar.LENGTH_SHORT).show();
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
    public void inputInvalid() {
        Toast.makeText(getActivity(), R.string.discount_invalid, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void databaseError() {
        Toast.makeText(getActivity(), R.string.databaseError, Toast.LENGTH_SHORT).show();
    }

}
