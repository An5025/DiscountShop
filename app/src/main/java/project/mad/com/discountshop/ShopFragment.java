package project.mad.com.discountshop;

import android.app.DatePickerDialog;
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

import java.util.Calendar;

import project.mad.com.discountshop.contract.ISaveDataView;
import project.mad.com.discountshop.impl.FirebaseShopPresenter;

/**
 * ShopFragment
 * user can input shop discount information and save them into firebase
 */
public class ShopFragment extends Fragment implements ISaveDataView{
    private static final String TAG = "ProductFragment";
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    FirebaseShopPresenter mPresenter;
    EditText mName, mDiscount;
    TextView  mDate;
    int discount;
    Button submit_btn;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.shop_fragment, container, false);

        mName = view.findViewById(R.id.shop_name_et);
        mDiscount = view.findViewById(R.id.shop_discount_et);
        mDate = view.findViewById(R.id.shop_date_tv);
        submit_btn = view.findViewById(R.id.shop_discount_confirm_btn);
        mPresenter = new FirebaseShopPresenter(this);
        mView = view;

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
                String date = getString(R.string.space)+dayOfMonth +getString(R.string.slash)+ month +getString(R.string.slash)+ year;
                mDate.setText(date);
            }
        };

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discount = Integer.parseInt(mDiscount.getText().toString().trim());
                mPresenter.input(mName.getText().toString().trim(), discount,
                                mDate.getText().toString().trim());
            }
        });
        return view;
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
