package in.bmsit.sixthsem.mean;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

public class AnalyseActivity extends AppCompatActivity {
    String result;
    TextView res;
    String[] data;
    String s1,s2,s3,s4;
    String[] values = {"56","94","23","78","30"};
    String[] titles = {"Hello", "My", "Name","Is","Deep"};
    String[] color = {"#dc143c", "#2ec571", "#8c7ae6", "#e84118", "#009432", "#487eb0", "#22a6b3", "#130f40"};
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

//        for(int i=0; i<=7; i+=2)
//        {
//            res.append(data[i+1]);
//            res.append("\n");
//            res.append(data[i]);
//            res.append("\n");
//
//        }
        drawPie();
//        for (String str : data) {
//            res.append(str);
//            res.append("\n ");
//        }

    }


    public void drawPie()
    {
        AnimatedPieView mAnimatedPieView = findViewById(R.id.pie);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        for(int i=0; i<=7; i+=2) {
            config.startAngle(-90)// Starting angle offset
                    .addData(new SimplePieInfo(Integer.parseInt(data[i+1]), Color.parseColor(color[i%5]), data[i]))//Data (bean that implements the IPieInfo interface)

                    .drawText(true).duration(2000).textSize(30);
        }

// The following two sentences can be replace directly 'mAnimatedPieView.start (config); '
        mAnimatedPieView.applyConfig(config);
        mAnimatedPieView.start();
    }
}