package in.bmsit.sixthsem.mean;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AnalyseActivity extends AppCompatActivity {
    String result;
    TextView res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        result = getIntent().getStringExtra("result");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);
        res = findViewById(R.id.res);
        res.setText(result);
    }
}