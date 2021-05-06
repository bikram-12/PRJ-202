package gcit.edu.gcitnye_tsul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import static gcit.edu.gcitnye_tsul.R.id.home_drawer_layout;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //navigationView.bringToFront();
        drawer =(DrawerLayout) findViewById(home_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.opennavDrawer, R.string.closenavDrawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_view:
                startActivity(new Intent(this,view_user.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(this,About.class));
                break;
            case R.id.nav_logout:
                startActivity(new Intent(this,MainActivity.class));
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}


