package com.example.gponcet.myfirstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class ScannerActivity extends Activity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        // Bouton de scanner qui lance une nouvelle activit�
        final Button btnMainMenu = (Button)findViewById(R.id.btnMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScannerActivity.this, MyActivity.class);
                startActivity(intent);
            }

        });

        // Bouton image qui ferme l'activit�
        final ImageButton imgBtnClose = (ImageButton)findViewById(R.id.imgClose);
        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ScannerActivity.this.finish();
            }

        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId(); // get comment



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /*
        M�thodes reprise de mon projet Eclipse
     */

    //product barcode mode
    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(ScannerActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //product qr code mode
    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(ScannerActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                /*Toast toast = Toast.makeText(this, "Code :" + contents + " Format :" + format, Toast.LENGTH_LONG);
                toast.show();*/


                // On affiche dans le TextView du bas
                TextView resultTextview = (TextView)findViewById(R.id.textViewScanResult);
                resultTextview.setText("Code scanné : " + contents +  "\nFormat : " + format);

                try
                {
                    // Ouverture du service de connexion à la base de données
                    SQLiteService myService = new SQLiteService(getApplicationContext());
                    myService.open();

                    String article_id = contents;
                    Article retrievedArticle = myService.getArticleById(article_id);

                    // Fermeture de la connexion à la base de données
                    myService.close();

                    String message = "L'article retrouvé est : ";

                    if (retrievedArticle != null)
                    {
                        message += retrievedArticle.toString();
                        resultTextview.setTextColor(Color.GREEN);

                    }
                    else
                    {
                        message = "Aucun article trouvé avec le numéro : " + article_id;
                        resultTextview.setTextColor(Color.RED);
                    }

                    TextView txtResult = (TextView) findViewById(R.id.textViewScanResult);
                    txtResult.setText(message);

                    Toast toastArticle = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toastArticle.show();

                    // Recevoir le résultat par SMS ?
                    /*int reponse = JOptionPane.showConfirmDialog(null, "Confirmer par SMS ?", "Confirmation", JOptionPane.YES_OPTION);

                    SmsManager sms = SmsManager.getDefault();
                    Intent intent2= new Intent("SMS_ACTION_SENT");
                    PendingIntent spi= PendingIntent.getBroadcast(getApplicationContext(),0,intent,0);
                    sms.sendTextMessage("0630663789", null, "CONTENU_TEXTE_DU_SMS", spi, spi);*/

                }
                catch (Exception e) {
                    e.printStackTrace();
                    resultTextview.setTextColor(Color.RED);
                    resultTextview.setText(e.getMessage());
                    resultTextview.append(" | " + e.toString());
                    //txtConnection.append(e.getCause().getMessage());
                }
            }
        }
    }
}
