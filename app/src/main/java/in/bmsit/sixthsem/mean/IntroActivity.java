package in.bmsit.sixthsem.mean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    int count = 2;
    Handler h = new Handler();
    TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        time = findViewById(R.id.textView3);
        getSupportActionBar().hide();
        h.postDelayed(func,1000);

        //openMain();
//        h.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent startmain = new Intent(IntroActivity.this, MainActivity.class);
//                startActivity(startmain);
//               finish();
//                    time.setText("Wait for"+count);
//                    count++;
//            }
//        },1000);

    }
    private final Runnable func = new Runnable() {
        @Override
        public void run() {
            if(count == 0){
                openMain();
            }
            time.setText("Wait for "+count);
            time.append(" sec");
            count--;
            h.postDelayed(this,1000);
        }
    };
    public void openMain() {
        Intent openmain = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(openmain);
        finish();
    }
}