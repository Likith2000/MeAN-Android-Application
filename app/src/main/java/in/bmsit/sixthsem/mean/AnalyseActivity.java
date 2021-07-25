package in.bmsit.sixthsem.mean;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

public class AnalyseActivity extends AppCompatActivity {
    String result;
    TextView res;
    String[] data;
    int length;
    String[] colors = {"#ffa502", "#2ed573", "#ff6348", "#5352ed", "#2f3542","#3dc1d3"};
    String s1,s2,s3,s4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        result = getIntent().getStringExtra("result");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);
        res = findViewById(R.id.res);
        s1 = result.replace('"', ' ');
        s2 = s1.replace("{", "");
        s3 = s2.replace("}", "");
        s4 = s3.replace(":", ",");
        data = s4.split(",");
        drawPie();
        length = Integer.parseInt(String.valueOf(data.length));
//        for(int i=0; i<data.length; i+=2)
//        {
//            res.append(data[i]);
//            res.append("\t");
//            res.append(data[i+1]);
//            res.append("\n");
//        }
    }

    public void drawPie()
    {
        int j=0;
        AnimatedPieView mAnimatedPieView = findViewById(R.id.pie);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        for(int i = 0; i<12; i+=2) {

            config.startAngle(-90)
                    .addData(new SimplePieInfo(Integer.parseInt(data[i+1]), Color.parseColor(colors[j]), data[i]))
                    .drawText(true).duration(2000).textSize(30);
            j++;
        }
        mAnimatedPieView.applyConfig(config);
        mAnimatedPieView.start();
    }

    public int randomColor() {
        int r = (int) (0xff * Math.random());
        int g = (int) (0xff * Math.random());
        int b = (int) (0xff * Math.random());
        return Color.rgb(r, g, b);
    }
}