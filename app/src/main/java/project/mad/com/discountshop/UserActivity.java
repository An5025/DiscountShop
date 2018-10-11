package project.mad.com.discountshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserActivity";
    private static final String TAB1 = "barcode";
    private static final String TAB2 = "product";
    private static final String TAB3 = "shop";

    private ViewPager mViewPager;
    private FirebaseAuth mAuth;
    String mCodeSent;
    EditText phoneNumber;
    EditText verificationCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Log.d(TAG, "onCreate: starting");

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        BottomNavigationView bottomNav = findViewById(R.id.navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        mAuth = FirebaseAuth.getInstance();
    }

    public void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new BarcodeFragment(), TAB1);
        adapter.addFragment(new ProductFragment(), TAB2);
        adapter.addFragment(new ShopFragment(), TAB3);
        viewPager.setAdapter(adapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.bottom_shop:
                            Intent intent1 = new Intent(UserActivity.this, ShopsActivity.class);
                            startActivity(intent1);
                            break;
                        case R.id.bottom_product:
                            Intent intent2 = new Intent(UserActivity.this, ProductsActivity.class);
                            startActivity(intent2);
                            break;
                        case R.id.bottom_user:
                            break;

                    }
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sign) {
            AlertDialog.Builder loginDialogue = new AlertDialog.Builder(UserActivity.this);
            loginDialogue.setTitle("Login");

            View mView = getLayoutInflater().inflate(R.layout.login_dialogue, null);

            phoneNumber = mView.findViewById(R.id.phoneNumberET);
            Button getCodeBtn = mView.findViewById(R.id.get_code_btn);
            verificationCode = mView.findViewById(R.id.verificationCodeET);
            Button loginBtn = mView.findViewById(R.id.login_btn);

            getCodeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendVerificationCode();

                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifySignInCode();
                }
            });
            loginDialogue.setView(mView).create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendVerificationCode() {
        String mPhoneNumber = phoneNumber.getText().toString();

        if (mPhoneNumber.isEmpty()){
            phoneNumber.setError("Phone number is required");
            phoneNumber.requestFocus();
            return;
        }

        if (mPhoneNumber.length() < 10){
            phoneNumber.setError("Phone number is invalid");
            phoneNumber.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new
            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    mCodeSent = s;
                }
            };

    public void verifySignInCode() {
        String input_Code = verificationCode.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mCodeSent, input_Code);
        signInWithPhoneAuthCredential(credential);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //open new activity
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(), "Incorrect code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
