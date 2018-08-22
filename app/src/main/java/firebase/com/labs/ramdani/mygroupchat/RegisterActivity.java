package firebase.com.labs.ramdani.mygroupchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtPassword, edtEmail;
    private Button btnRegister;
    private TextView tvLogin;

    //initial
    private FirebaseAuth mFirebaseAuth;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        btnRegister = (Button) findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(this);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        //initial
        mFirebaseAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Register");
        mProgressDialog.setMessage("Please wait....");

        AppPreference mAppPreference = new AppPreference(this);
        if (!TextUtils.isEmpty(mAppPreference.getEmail())){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_register){
            boolean isEmptyField = false;

            String name  = edtName.getText().toString().trim();
            String email  = edtEmail.getText().toString().trim();
            String password  = edtPassword.getText().toString().trim();

            //validasi
            if (TextUtils.isEmpty(name)) {
                isEmptyField = true;
                edtName.setError("required");
            }

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

                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mProgressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Register user berhasil",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(RegisterActivity.this, "Register user gagal",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }







    }
}
