package gcit.edu.gcitnye_tsul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register_Admin extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mName, mEmail, mPassword, mConfirmPassword;
    Button mRegister;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String adminID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__admin);

        mName = findViewById(R.id.Name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.InputConfirmPassword);

        mRegister = findViewById(R.id.registerAdmin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        mRegister.setOnClickListener(v -> {
            String name = mName.getText().toString();
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String confirmPassword = mConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name)){
                mName.setError("Required!");
                return;
            }

            if (TextUtils.isEmpty(email)){
                mEmail.setError("Required!");
                return;
            }

            if (TextUtils.isEmpty(password)){
                mPassword.setError("Required!");
                return;
            }

            if (password.length() < 6){
                mPassword.setError("Password too short");
                return;
            }

            if (TextUtils.isEmpty(confirmPassword)){
                mConfirmPassword.setError("Required!");
                return;
            }

            if (!password.equals(confirmPassword)){
                mConfirmPassword.setError("Does not match the password.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    FirebaseUser mAdmin = FirebaseAuth.getInstance().getCurrentUser();
                    if (mAdmin.isEmailVerified()){
                        startActivity(new Intent(register_Admin.this,MainActivity.class));
                    } else {
                        mAdmin.sendEmailVerification();
                        Toast.makeText(register_Admin.this, "Check Email for verification.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        adminID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("Admin").document(adminID);
                        Map<String, Object> admin = new HashMap<>();
                        admin.put("Name", name);
                        admin.put("Email", email);
                        admin.put("Password", password);

                        documentReference.set(admin).addOnSuccessListener(aVoid ->
                                Log.d(TAG,"onSuccess: Admin profile is created for "+ adminID));
                        startActivity(new Intent(register_Admin.this,MainActivity.class));
                        finish();
                    }
                }else {
                    Toast.makeText(register_Admin.this, "Error!! :: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(register_Admin.this,MainActivity.class));
                    finish();
                }
            });
        });
    }
}