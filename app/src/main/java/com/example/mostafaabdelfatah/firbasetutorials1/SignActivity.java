package com.example.mostafaabdelfatah.firbasetutorials1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.example.mostafaabdelfatah.firbasetutorials1.MainActivity.sendRegistrationToServer;

public class SignActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth.AuthStateListener authStateListener;



    private ProgressDialog dialog;
    private EditText emailView,passView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        emailView = (EditText) findViewById(R.id.emailText);
        passView  = (EditText) findViewById(R.id.passwordText);
        dialog = new ProgressDialog(SignActivity.this);
        /**
         * */
        mAuth = FirebaseAuth.getInstance();

        //FirebaseAuth.getInstance().signOut();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){

                    //intent the user account
                    Intent page = new Intent(SignActivity.this,MainActivity.class);
                    startActivity(page);

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }

            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
        /**
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);*/
    }

    /**
     * Sign Up with Email and Password
     * */

    public void singUpbtn_Clicked(View view) {

        dialog = new ProgressDialog(SignActivity.this);
        dialog.setMessage("Do Sign Up Authentication, please wait.");
        dialog.show();

        // [START create_user_with_email]
        String email = emailView.getText().toString().trim();
        String pass  = passView.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignActivity.this, "Authentication Successful.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    /**
     * Sign in with Email and Password account
     * */

    public void singInbtn_Clicked(View view) {

        dialog = new ProgressDialog(SignActivity.this);
        dialog.setMessage("Do Sign In Authentication, please wait.");
        dialog.show();

        String email = emailView.getText().toString().trim();
        String pass  = passView.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignActivity.this, "Authentication Successful.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }

                        // ...
                    }
                });

    }

    /**
     * Sign in with google account
     * */
    public void singInWithGoogle(View view) {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignActivity.this, "Authentication Successful.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                       // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]


    /**
     * Sign in with Facebook account
     * */
    public void singInWithFacebook(View view) {

    }
    /**
     * Sign in with Twitter account
     * */
    public void singInWithTwitter(View view) {

    }
    /**
     * Sign in with GitHub account
     * */
    public void singInWithGitHub(View view) {

    }
    /**
     * Sign in with Phone Number account
     * */
    public void singInWithPhoneNumber(View view) {

    }

    /**
     * Sign in with Anonymous
     * */
    public void singInWithAnonymous(View view) {

    }
}
