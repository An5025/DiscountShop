package project.mad.com.discountshop;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import project.mad.com.discountshop.contract.IShopView;
import project.mad.com.discountshop.impl.FirebaseShopPresenter;

/**
 * ShopFragment
 * user can input shop discount information and save them into firebase
 */
public class ShopFragment extends Fragment implements IShopView{
    private static final String TAG = "ProductFragment";
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private FirebaseShopPresenter mPresenter;
    private EditText mName, mDiscount;
    private TextView  mDate;
    private int discount;
    private Button submit_btn;
    private View mView;

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

        //click to select expiry date
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
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
                try {
                    discount = Integer.parseInt(mDiscount.getText().toString().trim());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                mPresenter.checkDate( mDate.getText().toString().trim());
                if (mPresenter.isValidDate) {
                    mPresenter.input(mName.getText().toString().trim(), discount,
                            mDate.getText().toString().trim());
                }
            }
        });
        return view;
    }

    @Override
    public void showValidationError() {
        Toast.makeText(getActivity(), R.string.input_invalid, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void inputSuccess() {
        Toast.makeText(getActivity(), R.string.input_success, Toast.LENGTH_SHORT ).show();
        mName.setText(getString(R.string.empty));
        mDiscount.setText(getString(R.string.empty));
        mDate.setText(getString(R.string.empty));
    }

    @Override
    public void inputError() {
        Toast.makeText(getActivity(), R.string.input_error, Toast.LENGTH_SHORT ).show();
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
