package co.uniongr.scanerradicado;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String QR_MESSAGE = "QR";
    private int conteo_retroceso = 0;
    private IntentIntegrator intentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //starCam();

        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.scanQR, new ScanQr_Fragment());
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //https://es.acervolima.com/como-leer-el-codigo-qr-usando-la-biblioteca-zxing-en-android/
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                conteo_retroceso = conteo_retroceso + 1;
                if(conteo_retroceso<3){
                    Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                }else{
                    mensaje_desea_salir_app("La app ha presentado varios errores consecutivos en el escaneo, desea cerrarla?");
                }
            } else {
                abrir_otra_actividad(intentResult.getContents());
                conteo_retroceso = 0;
                //Toast.makeText(getBaseContext(), intentResult.getContents() + "-" + intentResult.getFormatName(), Toast.LENGTH_SHORT).show();
                // if the intentResult is not null we'll set
                // the content and format of scan message
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        mensaje_desea_salir_app("Desea cerrar la app?");
    }

    private void mensaje_desea_salir_app(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje).setCancelable(false).setTitle("Confirmación")
                .setPositiveButton("Cerrar app", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cerrar_app();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        starCam();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        starCam();
    }

    private void cerrar_app(){
        finish();
        System.exit(0);
    }

    private void starCam(){
        //https://cursokotlin.com/zxing-leer-qr-codigos-de-barras-en-kotlin/
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.setPrompt("Por favor ponga el QR dentro del recuadro de la camara");
        intentIntegrator.setTorchEnabled(false); //control flash
        intentIntegrator.setBeepEnabled(true);

        List<String> opciones_usar = new ArrayList<String>();
        opciones_usar.add(IntentIntegrator.QR_CODE);
        intentIntegrator.setDesiredBarcodeFormats(opciones_usar);
        intentIntegrator.initiateScan();
    }

    private void abrir_otra_actividad(String datos){
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra(QR_MESSAGE, datos);
        startActivity(intent);
    }

    private void loadFragment(Fragment fragment) {
        try {
            // create a FragmentManager
            FragmentManager fm = getFragmentManager();
            // create a FragmentTransaction to begin the transaction and replace the Fragment
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            // replace the FrameLayout with new Fragment
            fragmentTransaction.replace(R.id.scanQR,
                    fragment);
            fragmentTransaction.commit(); // save the changes
        }catch (Exception e){

        }
    }
}