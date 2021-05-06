package gcit.edu.gcitnye_tsul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class forgot_Password extends AppCompatActivity {
    EditText emailText;
    Button resetButton;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        emailText = findViewById(R.id.EnterEmail);
        resetButton = findViewById(R.id.send_bot);
        progressBar = findViewById(R.id.progressBar3);

        fAuth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(v -> {
            password();
        });
    }
    private void password(){
        String email = emailText.getText().toString().trim();

        if (email.isEmpty()){
            emailText.setError("Email Is Required!");
            emailText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Provide a valid Email");
            emailText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Email Sent To Your Email.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(forgot_Password.this,MainActivity.class));
            finish();
        });
    }

}
