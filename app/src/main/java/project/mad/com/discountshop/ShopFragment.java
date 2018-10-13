package project.mad.com.discountshop;

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

import project.mad.com.discountshop.view.IDiscountShopView;
import project.mad.com.discountshop.impl.FirebaseShopDiscountPresenter;

public class ShopFragment extends Fragment implements IDiscountShopView{
    private static final String TAG = "ProductFragment";
    EditText mName, mDate, mDiscount;
    int discount;
    FirebaseShopDiscountPresenter mPresenter;
    Button submit_btn;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.shop_fragment, container, false);

        mName = view.findViewById(R.id.shop_name_et);
        mDiscount = view.findViewById(R.id.shop_discount_et);
        mDate = view.findViewById(R.id.shop_date_et);
        submit_btn = view.findViewById(R.id.shop_discount_confirm_btn);
        mPresenter = new FirebaseShopDiscountPresenter(this);
        mView = view;

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
    public void discountInvalid() {
        Toast.makeText(getActivity(), R.string.discount_invalid, Toast.LENGTH_SHORT).show();
    }
}
