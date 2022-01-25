package co.uniongr.scanerradicado;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uniongr.scanerradicado.adapters.FilesAdapter;
import co.uniongr.scanerradicado.dto.Radicados;
import co.uniongr.scanerradicado.utils.Constantes;

public class InfoActivity extends AppCompatActivity {

    List<Radicados> alist;
    FilesAdapter adapter;
    RequestQueue requestQueue;

    ListView musiclist;
    TextView qr;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);adapter = new FilesAdapter(this,R.layout.customcell,alist);
        setContentView(R.layout.activity_info);

        musiclist = findViewById(R.id.musiclistview);
        qr = findViewById(R.id.qr);

        alist = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        adapter = new FilesAdapter(this,R.layout.customcell,alist);

        musiclist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(InfoActivity.this, "Abriendo el recurso "+ alist.get(position).getArchivo(), Toast.LENGTH_LONG).show();

                //https://codea.app/blog/lanzar-un-link
                Uri uri = Uri.parse(alist.get(position).getRuta());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        String message = "Radicado";
        String data_important = "";

        try {
            Intent intent = getIntent();
            message = intent.getStringExtra(MainActivity.QR_MESSAGE);
            if(message==null){
                message = "Radicado";
            }
            data_important = message.substring(message.indexOf("Radicado"));
            data_important = data_important.substring(data_important.indexOf(" "));
            data_important = data_important.contains("\r") ? data_important.substring(0, data_important.indexOf("\r")) : data_important;
            data_important = data_important.contains("\n") ? data_important.substring(0, data_important.indexOf("\n")) : data_important;
            data_important = data_important.replace(" ","");
        }catch (Exception e){

        }

        qr.setText(message);

        postData(data_important);
    }

    private JSONObject get_data_send(String radicado){
        //https://www.flipandroid.com/crear-un-jsonarray.html#:~:text=Usted%20puede%20hacerlo%20como%3A,agregar%20cualquier%20cadena%20que%20desee.
        JSONObject j_obj = new JSONObject();
        try {
            j_obj.put("usr", Constantes.api_workManager.user);
            j_obj.put("pwd",Constantes.api_workManager.password);
            j_obj.put("codigobarras",radicado);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j_obj;
    }

    // Post Request For JSONObject
    public void postData(String radicado) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = get_data_send(radicado);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Constantes.api_workManager.url_api,
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(InfoActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("Respuesta");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String archivo = jsonObject.getString("archivo");
                                String ruta = jsonObject.getString("w_ruta_url");
                                String extension = jsonObject.getString("extension");
                                //Log.e("data", jsonArray.getJSONObject(i).toString());
                                alist.add(new Radicados(archivo, ruta, extension));
                            }
                            adapter.notifyDataSetChanged();
                            musiclist.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InfoActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void mostrar_info(String datos){
        AlertDialog.Builder builder = new
                AlertDialog.Builder(this);
        builder.setTitle("Contenido QR");
        builder.setMessage(datos);
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}