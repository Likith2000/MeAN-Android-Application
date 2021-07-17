package in.bmsit.sixthsem.mean;

import android.app.VoiceInteractor;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText ptxt;
    private Button btnpst;
    private Button bClr;
    private Button bAnlz;
    private ClipboardManager clipboardManager;
    private ClipData clipData;
    String resultData;

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
//                res.setText("response");
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
        String url = "https://mean-senti.herokuapp.com/predict";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JSONObject postData = new JSONObject();
        try {
            String content = new String();
            content = ptxt.getText().toString();
            postData.put("text", content);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                res.setText(response.toString());
                resultData = response.toString();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultData = error.toString();
            }
        });
        requestQueue.add(jsonObjectRequest);
        Intent intent = new Intent(this, AnalyseActivity.class);
        intent.putExtra("result",resultData);
        startActivity(intent);
    }
}