package gcit.edu.gcitnye_tsul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn, mRegisterAdmin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.InputEmail);
        mPassword = findViewById(R.id.InputPassword);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.login);
        mRegisterAdmin = findViewById(R.id.admin_bot);

        mLoginBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                mEmail.setError("Required!");
                return;
            }

            if (TextUtils.isEmpty(password)){
                mPassword.setError("Required!");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), home.class));
                    Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Error!! :: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
    }

    public void registerAdmin(View view) {
        Intent obj = new Intent(this, register_Admin.class);
        startActivity(obj);
    }

    public void registerUser(View view) {
        Intent obj = new Intent(this, register_User.class);
        startActivity(obj);
    }

    public void forgotPassword(View view) {
        Intent obj = new Intent(this, forgot_Password.class);
        startActivity(obj);
    }
}