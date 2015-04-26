package com.esgi.groupe1.eloworld;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.esgi.groupe1.eloworld.method.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Register extends Activity  {
    EditText inputemail,inputPwd,inputCPwd, inputPseudo;
    Spinner region;
    Button inscription;
    String valueOfSpinner;
    ProgressDialog dialog;
    public static final String Url_Register ="http://192.168.31.1/eloworldweb/Code/WebService/inscription/inscription.php";
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputPseudo = (EditText) findViewById(R.id.pseudo);
        inputemail = (EditText) findViewById(R.id.email);
        inputPwd =(EditText)findViewById(R.id.passwd);
        inputCPwd =(EditText)findViewById(R.id.c_passwd);



        region = (Spinner) findViewById(R.id.region);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.region,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region.setAdapter(adapter);


        region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueOfSpinner = region.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplication(),"onNothingSelected",Toast.LENGTH_SHORT).show();
            }
        });

        inscription=(Button) findViewById(R.id.inscription);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputemail.getText().toString();
                String pseudo = inputPseudo.getText().toString();
                String password = inputPwd.getText().toString();
                String confirmPwd = inputCPwd.getText().toString();

                if (email.trim().length()>0 && pseudo.trim().length()>0 && password.trim().length()>0 && confirmPwd.trim().length()>0){

                    if(password.equals(confirmPwd)){

                        new Newuser().execute();

                    }
                    else{
                        Toast.makeText(getApplication(),"Veuillez saisir des mots de passes indentiques ",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplication(),"Veuillez remplir tous les champs!",Toast.LENGTH_LONG).show();
                }



            }
        });
    }

    class Newuser extends AsyncTask {


       @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Register.this);
            dialog.setMessage("Veuillez patienter nous créons votre compte..");
            dialog.setIndeterminate(false);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {


            String email = inputemail.getText().toString();
            String pseudo = inputPseudo.getText().toString();
            String Password = inputCPwd.getText().toString();
            String Server = valueOfSpinner;

            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("pseudo",pseudo));
            parameters.add(new BasicNameValuePair("email",email));
            parameters.add(new BasicNameValuePair("Password",Password));
            parameters.add(new BasicNameValuePair("Server",Server));


                    try {
                        JSONObject json = JSONParser.makeHttpRequest(Url_Register,parameters);
                        Log.d("Json", String.valueOf(json));
                        int success = json.getInt(TAG_SUCCESS);
                        Log.d("success back", String.valueOf(success));
                        if (success == 1){
                            Intent intent = new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);

                            finish();

                        }else{
                           Log.d(":D","HAHAHAHAAHAHAHAHAHA");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(dialog.isShowing()){
                dialog.dismiss();
            }

            Toast.makeText(getApplication(),"Vous pouvez désormais vous connecter",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
