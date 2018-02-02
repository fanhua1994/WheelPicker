package com.hengyi.wheelpicker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hengyi.wheelpicker.R;
import com.hengyi.wheelpicker.listener.OnCityWheelComfirmListener;
import com.hengyi.wheelpicker.ppw.CityWheelPickerPopupWindow;

public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);

        final CityWheelPickerPopupWindow wheelPickerPopupWindow = new CityWheelPickerPopupWindow(MainActivity.this);
        wheelPickerPopupWindow.setListener(new OnCityWheelComfirmListener() {
            @Override
            public void onSelected(String Province, String City, String District, String PostCode) {
                Toast.makeText(MainActivity.this,Province + City + District,Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                wheelPickerPopupWindow.show();
            }
        });
    }
}
