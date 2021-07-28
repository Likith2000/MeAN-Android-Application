package in.bmsit.sixthsem.mean;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText ptxt;
    Button btnpst, bClr, bAnlz;
    ClipboardManager clipboardManager;
    ProgressDialog p,p1;
    String resultData = null;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bAnlz = findViewById(R.id.bAnalyse);
        bClr = findViewById(R.id.bClear);
        ptxt = findViewById(R.id.txtShow);
        btnpst = findViewById(R.id.btnShow);
        clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

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
                closeKeyboard();
                AsyncTaskAnalyze asyncTask=new AsyncTaskAnalyze();
                asyncTask.execute("https://mean-senti.herokuapp.com/predict");
            }
        });

        bClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptxt.setText("");
            }
        });
    }


    private class AsyncTaskAnalyze extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Please Wait...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String url = new String(strings[0]);
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
                        resultData = response.toString();
                        count--;
                    }
                }, error -> resultData = error.toString());
                requestQueue.add(jsonObjectRequest);
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
            while(count !=0 ){
                Log.d("Waiting","waiting");
            }
            return resultData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(resultData != null){
                p.hide();
                Intent intent = new Intent(MainActivity.this, AnalyseActivity.class);
                intent.putExtra("result",resultData);
                resultData = null;
                startActivity(intent);
            } else{
                p.show();
            }
        }
    }
    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}