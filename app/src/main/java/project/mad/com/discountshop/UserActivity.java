package project.mad.com.discountshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * UserActivity
 * user can login and logout in this activity
 */
public class UserActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton mSignInButton;
    private Button mSignOutBtn;
    private TextView mUserName, mUserEmail;
    private ImageView mUserPhoto;
    private RelativeLayout mUserLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView bottomNav = findViewById(R.id.navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Menu mMenu = bottomNav.getMenu();
        MenuItem menuItem = mMenu.getItem(3);
        menuItem.setChecked(true);

        mAuth = FirebaseAuth.getInstance();
        mSignInButton = findViewById(R.id.login_btn);
        mSignOutBtn = findViewById(R.id.sign_out);
        mUserName = findViewById(R.id.user_name);
        mUserEmail = findViewById(R.id.user_email);
        mUserPhoto = findViewById(R.id.photo);
        mUserLayout = findViewById(R.id.user_layout);
        mUserLayout.setVisibility(View.GONE);
        mSignOutBtn.setVisibility(View.GONE);

        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            updatUi(true);
        }
    }

    /**
     * click sign in button to invoke this method
     * sign in by google login
     */
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Constants.KEY_CODE);
    }

    /**
     * click sign out button, invoke this method
     * sign out by google sign out
     */
    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updatUi(false);
            }
        });
    }

    /**
     * get user name, email, photo from result
     * set all data to corresponding textviews or imageview
     * invoke updateUi method to update ui
     * @param result
     */
    private void handleResult(GoogleSignInResult result){
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String ima_url = account.getPhotoUrl().toString();
            mUserName.setText(name);
            mUserEmail.setText(email);
            Glide.with(this).load(ima_url).into(mUserPhoto);
            updatUi(true);
        }else{
            updatUi(false);
        }

    }

    /**
     * if user logged in the login button will be invisible
     * if user logged out the user information and logout buttion will be invisible
     * @param isLogin
     */
    private void updatUi(boolean isLogin) {
        if (isLogin){
            mSignInButton.setVisibility(View.GONE);
            mUserLayout.setVisibility(View.VISIBLE);
            mSignOutBtn.setVisibility(View.VISIBLE);
        }else{
            mSignInButton.setVisibility(View.VISIBLE);
            mUserLayout.setVisibility(View.GONE);
            mSignOutBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.KEY_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.KEY_UNAME, mUserName.getText().toString());
        outState.putString(Constants.KEY_UNAME, mUserEmail.getText().toString());
        super.onSaveInstanceState(outState);
    }

    /**
     * this method setup four activities at the bottom navigation
     * control the activityies' shifting
     */
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
                            Intent intent2 = new Intent(UserActivity.this, ShopsActivity.class);
                            startActivity(intent2);
                            break;
                        case R.id.bottom_add_info:
                            Intent intent3 = new Intent(UserActivity.this, AddActivity.class);
                            startActivity(intent3);
                            break;
                        case R.id.bottom_user:
                            break;

                    }
                    return true;
                }
            };

}
