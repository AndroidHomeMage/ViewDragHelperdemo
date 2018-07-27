package drag.example.com.demo3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import drag.example.com.R;

/**
 * Created by mayong on 2018/7/27.
 */

public class Demo3Activity extends AppCompatActivity {

    private View tv0;
    private Demo3View demo3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        demo3 = findViewById(R.id.demo3);
        tv0 = findViewById(R.id.tv_0);
        tv0.setOnClickListener(v -> {
            demo3.startScroll();
        });
        findViewById(R.id.tv_1).setOnClickListener(v->{
            demo3.startScroll();
        });
    }
}
