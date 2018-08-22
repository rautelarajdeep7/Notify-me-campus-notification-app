package firebase.com.labs.ramdani.mygroupchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;


    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Login");
        mProgressDialog.setMessage("Please wait..");

        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){

            boolean isEmptyField = false;

            final String email  = edtEmail.getText().toString().trim();
            String password  = edtPassword.getText().toString().trim();

            //validasi

            if (TextUtils.isEmpty(email)){
                isEmptyField = true;
                edtEmail.setError("required");
            }

            if (TextUtils.isEmpty(password)){
                isEmptyField = true;
                edtPassword.setError("required");
            }

            if (!isEmptyField){
                mProgressDialog.show();
                mFirebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mProgressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Login user berhasil",Toast.LENGTH_SHORT).show();

                                    AppPreference mAppPreference = new AppPreference(LoginActivity.this);
                                    mAppPreference.setEmail(email);

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Login user gagal",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
}
