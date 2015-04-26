package com.esgi.groupe1.eloworld;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.esgi.groupe1.eloworld.method.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class Login extends FragmentActivity {
    FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
    EditText email,password;
    TextView newaccount;
    EditText inputemail,inputpassword;
    ProgressDialog dialog;
    RelativeLayout reset;

    Button btLogin;
    public static final String URL_LOGIN ="http://192.168.31.1/eloworldweb/code/WebService/connexion/connexion.php";
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        
        email = (EditText) findViewById(R.id.email);
        password =(EditText) findViewById(R.id.password);

        newaccount = (TextView) findViewById(R.id.nouveau);
        inputemail = (EditText) findViewById(R.id.email);
        inputpassword =(EditText)findViewById(R.id.password);
        btLogin = (Button)findViewById(R.id.btnlog);
        reset = (RelativeLayout)findViewById(R.id.reset);

        btLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 String email = inputemail.getText().toString();
                 String password = inputpassword.getText().toString();
                //If not empty
                if (email.trim().length() > 0 && password.trim().length() > 0){

                    new Logintask().execute();

                }else{
                    Toast.makeText(getApplication(),"Veuillez remplir tous les champs!",Toast.LENGTH_LONG).show();
                }

            }
        });

        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(getApplicationContext(),Register.class);
                startActivity(account);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplication(),DialogEmail.class);
                startActivity(intent);*/
                DialogEmail dialogEmail =new DialogEmail();
                dialogEmail.show(fragmentTransaction,"Dialog");
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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



    class Logintask extends AsyncTask{
        String email = inputemail.getText().toString();
        String password = inputpassword.getText().toString();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Login.this);
            dialog.setMessage("Connexion..");
            dialog.setIndeterminate(false);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("email",email));
            parameters.add(new BasicNameValuePair("Password",password));

            //JSONObject object = new JSONParser().makeHttpRequest(URL_LOGIN,parameters);
             try {
                 JSONObject object = new JSONParser().makeHttpRequest(URL_LOGIN,parameters);
                 int success = object.getInt(TAG_SUCCESS);
                 Log.d("success back", String.valueOf(success));
                 if (success == 1){
                     Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                     startActivity(intent);
                     finish();

                 }else{
                     Log.d("erreur","impossible");
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dialog.dismiss();
        }
    }

}
