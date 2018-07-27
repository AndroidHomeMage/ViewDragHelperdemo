package drag.example.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import drag.example.com.demo1.SimpleDragActivity;
import drag.example.com.demo2.Demo2Activity;
import drag.example.com.demo3.Demo3Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SimpleDragActivity.class));
        });
        findViewById(R.id.btn2).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Demo2Activity.class));
        });
        findViewById(R.id.btn3).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Demo3Activity.class));
        });

    }
}
