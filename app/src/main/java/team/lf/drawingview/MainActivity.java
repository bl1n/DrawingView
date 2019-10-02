package team.lf.drawingview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final float SMALL_BRUSH = 10;
    public static final float MEDIUM_BRUSH = 20;
    public static final float LARGE_BRUSH = 30;

    private PaintView mPaintView;
    private BottomNavigationView mNavigaton;
    private BottomNavigationView.OnNavigationItemSelectedListener mListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.brush:
                    mPaintView.setErase(false);
                    break;
                case R.id.clear_sheet:
                    mPaintView.startNew();
                    break;
                case R.id.eraser:
                    mPaintView.setErase(true);
                    break;
                case R.id.settings:
                    showSettings();
                    break;
                case R.id.undo:

                    break;
            }
            Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    private void showSettings() {
        Dialog settingsDialog = new Dialog(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigaton = findViewById(R.id.navigation);
        mNavigaton.setOnNavigationItemSelectedListener(mListener);

        mPaintView = findViewById(R.id.paintView);
        mPaintView.setBrushSize(MEDIUM_BRUSH);


    }
}
