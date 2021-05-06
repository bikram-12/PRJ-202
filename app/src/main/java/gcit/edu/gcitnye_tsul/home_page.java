package gcit.edu.gcitnye_tsul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void ann(View view) {
        Intent obj = new Intent(this, announcement.class);
        startActivity(obj);
    }
    public void media(View view) {
        Intent obj = new Intent(this, media.class);
        startActivity(obj);
    }
}