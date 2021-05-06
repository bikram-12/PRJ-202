package gcit.edu.gcitnye_tsul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register_User extends AppCompatActivity {
    EditText mName, mEmail, mPassword, mConfirmPassword;
    RadioButton mUserTypeS, mUserTypeT;
    Button mRegister;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user);

        mName = findViewById(R.id.InputName2);
        mEmail = findViewById(R.id.InputEmail3);
        mPassword = findViewById(R.id.InputPassword3);
        mConfirmPassword = findViewById(R.id.InputConfirmPassword2);

        mUserTypeS = findViewById(R.id.radioButton);
        mUserTypeT = findViewById(R.id.radioButton3);

        mRegister = findViewById(R.id.registerUser);

        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (mUser.isEmailVerified()){
                        startActivity(new Intent(register_User.this, MainActivity.class));
                    }else {
                        mUser.sendEmailVerification();
                        Toast.makeText(register_User.this, "Check Email for verification.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        userID = fAuth.getCurrentUser().getUid();

                        DocumentReference documentReference = fStore.collection("Admin").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("Name", name);
                        user.put("Email", email);
                        user.put("Password", password);

                        documentReference.set(user).addOnSuccessListener(aVoid ->
                                Log.d("TAG","onSuccess: User profile is created for "+ userID));
                        startActivity(new Intent(register_User.this,MainActivity.class));
                        finish();
                    }
                }else {
                    Toast.makeText(register_User.this, "Error!! :: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(register_User.this,MainActivity.class));
                    finish();
                }
            });
        });
    }
}