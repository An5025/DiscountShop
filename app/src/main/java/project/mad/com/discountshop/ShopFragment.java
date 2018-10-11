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

import project.mad.com.discountshop.View.IDiscountShopView;
import project.mad.com.discountshop.model.FirebaseShopDiscountModel;

public class ShopFragment extends Fragment implements IDiscountShopView{
    private static final String TAG = "ProductFragment";
    EditText mName, mCapacity, mBrand, mCountdown, mDiscount;
    int discount, countdown;
    FirebaseShopDiscountModel mPresenter;
    Button submit_btn;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.shop_fragment, container, false);

        mName = view.findViewById(R.id.shop_name_et);
        mDiscount = view.findViewById(R.id.shop_discount_et);
        mCountdown = view.findViewById(R.id.shop_countdown_et);
        submit_btn = view.findViewById(R.id.shop_discount_confirm_btn);
        mPresenter = new FirebaseShopDiscountModel(this);
        mView = view;

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discount = Integer.parseInt(mDiscount.getText().toString().trim());
                countdown = Integer.parseInt(mCountdown.getText().toString().trim());
                mPresenter.input(mName.getText().toString().trim(),
                                discount, countdown);
            }
        });
        return view;
    }

    @Override
    public void showValidationError() {
        Snackbar.make(mView, "input is invalid", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void inputSuccess() {
        Snackbar.make(mView, "input success", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void inputError() {
        Snackbar.make(mView, "input error", Snackbar.LENGTH_LONG).show();
    }
}
