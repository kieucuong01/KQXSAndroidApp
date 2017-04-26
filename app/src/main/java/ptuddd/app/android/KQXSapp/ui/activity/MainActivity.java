package ptuddd.app.android.KQXSapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ptuddd.app.android.KQXSapp.R;

public class MainActivity extends AppCompatActivity {
    // UI references.
    Button btnExit;
    Button btnXemKQXS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getControl();
        bindEvents();
    }

    /*
    *           Function get control items in view
     */
    private void getControl() {
        btnXemKQXS = (Button)findViewById(R.id.btnXemKQXS);
        btnExit = (Button)findViewById(R.id.btnExit);
    }

    private void bindEvents() {

        btnXemKQXS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, KQXSActivity.class);
                MainActivity.this.startActivity(myIntent);

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    /*
    *    Function show @activity
    */
    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    /*
    *    Function show @activity
    */
    private void showActivity(Class activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
    }
}
