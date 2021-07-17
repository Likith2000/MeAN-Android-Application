package in.bmsit.sixthsem.mean;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText ptxt;
    private Button btnpst;
    private Button bClr;
    private Button bAnlz;
    private ClipboardManager clipboardManager;
    private ClipData clipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bAnlz = (Button)findViewById(R.id.bAnalyse);
        bClr = (Button)findViewById(R.id.bClear);
        ptxt = (EditText)findViewById(R.id.txtShow);
        btnpst = (Button)findViewById(R.id.btnShow);
        clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData pData = clipboardManager.getPrimaryClip();
        ClipData.Item item = pData.getItemAt(0);
        String txtpaste = item.getText().toString();
        ptxt.setText(txtpaste);
        Toast.makeText(getApplicationContext(),"Data Pasted from Clipboard",Toast.LENGTH_SHORT).show();

        btnpst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData pData = clipboardManager.getPrimaryClip();
                ClipData.Item item = pData.getItemAt(0);
                String txtpaste = item.getText().toString();
                ptxt.setText(txtpaste);
                Toast.makeText(getApplicationContext(),"Data Pasted from Clipboard",Toast.LENGTH_SHORT).show();
            }
        });

        bAnlz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAnalyseActivity();
            }
        });


        bClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptxt.setText("");
            }
        });
    }
    public void openAnalyseActivity(){
        Intent intent = new Intent(this, AnalyseActivity.class);
        startActivity(intent);
    }
}