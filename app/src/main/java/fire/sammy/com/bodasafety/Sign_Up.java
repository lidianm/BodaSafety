package fire.sammy.com.bodasafety;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up extends AppCompatActivity {
    private EditText inputEmail, inputPassword, NaMe;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        auth = FirebaseAuth.getInstance();
        btnSignUp = (Button) findViewById(R.id.btn_signin);
        inputEmail = (EditText) findViewById(R.id.id_email);
        inputPassword = (EditText) findViewById(R.id.id_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        NaMe=(EditText)findViewById(R.id.id_email);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = NaMe.getText().toString().trim();

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                if ((TextUtils.isEmpty(name))) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Sign_Up.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Sign_Up.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Sign_Up.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    auth.createUserWithEmailAndPassword(email, password);
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                                    DatabaseReference currentUserDB = mDatabase.child(auth.getCurrentUser().getUid());
                                    currentUserDB.child("name").setValue(name);


                                    startActivity(new Intent(Sign_Up.this, MainActivity.class));
                                    finish();

                                }
                            }
                        });

            }
        });
    }
}
