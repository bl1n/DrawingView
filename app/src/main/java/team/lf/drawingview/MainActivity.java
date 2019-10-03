package team.lf.drawingview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

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
                    mPaintView.setErase(false);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigaton = findViewById(R.id.navigation);
        mNavigaton.setOnNavigationItemSelectedListener(mListener);

        mPaintView = findViewById(R.id.paintView);
        mPaintView.setBrushSize(MEDIUM_BRUSH);


    }

    private void showSettings() {
        Dialog settingsDialog = new Dialog(this);
        settingsDialog.setContentView(R.layout.dialog_settings);
        settingsDialog.setTitle("Settings");
        settingsDialog.findViewById(R.id.colorBlackIB).setOnClickListener(v -> {
            mPaintView.setColor(Color.BLACK);
            settingsDialog.dismiss();
        });
        settingsDialog.findViewById(R.id.colorRedIB).setOnClickListener(v -> {
            mPaintView.setColor(Color.RED);
            settingsDialog.dismiss();
        });
        settingsDialog.findViewById(R.id.colorGreenIB).setOnClickListener(v -> {
            mPaintView.setColor(Color.GREEN);
            settingsDialog.dismiss();
        });
        settingsDialog.findViewById(R.id.colorBlueIB).setOnClickListener(v -> {
            mPaintView.setColor(Color.BLUE);
            settingsDialog.dismiss();
        });
        settingsDialog.findViewById(R.id.smallBrushIB).setOnClickListener(v -> {
            mPaintView.setBrushSize(SMALL_BRUSH);
            settingsDialog.dismiss();
        });
        settingsDialog.findViewById(R.id.mediumBrushIB).setOnClickListener(v -> {
            mPaintView.setBrushSize(MEDIUM_BRUSH);
            settingsDialog.dismiss();
        });
        settingsDialog.findViewById(R.id.largeBrushIB).setOnClickListener(v -> {
            mPaintView.setBrushSize(LARGE_BRUSH);
            settingsDialog.dismiss();
        });

        settingsDialog.show();
    }
}
