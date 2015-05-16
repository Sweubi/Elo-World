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

import com.esgi.groupe1.eloworld.RiotGameAPI.APIMethod;
import com.esgi.groupe1.eloworld.method.JSONParser;
import com.esgi.groupe1.eloworld.method.SessionManager;
import com.esgi.groupe1.eloworld.sqlLite.SQLiteHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class Login extends FragmentActivity {

    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    APIMethod apiMethod = new APIMethod();
    TextView newaccount;
    EditText inputemail,inputpassword;
    ProgressDialog dialog;
    RelativeLayout reset;
    String email;
    String password ;
    Button btLogin;
    SQLiteHandler db;

    public static final String URL_LOGIN ="http://192.168.31.1/eloworldweb/code/WebService/connexion/connexion.php";
    private static final String TAG_SUCCESS = "success";
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
            finish();
        }
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
                if (session.isOnline()) {
                    //If not empty
                    if (email.trim().length() > 0 && password.trim().length() > 0) {

                        //db.addUser(email);

                        new Logintask().execute();
                    } else {
                        Toast.makeText(getApplication(), "Veuillez remplir tous les champs!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplication(), "pas de connexion", Toast.LENGTH_LONG).show();
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

                DialogEmail dialogEmail = new DialogEmail();
                if (dialogEmail.isVisible()) dialogEmail.dismiss();

                dialogEmail.show(fragmentTransaction, "Dialog");

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



    class Logintask extends AsyncTask<Object, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            email = inputemail.getText().toString();
            password = inputpassword.getText().toString();
            dialog = new ProgressDialog(Login.this);
            dialog.setMessage("Connexion..");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Integer doInBackground(Object... params) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("email",email));
            parameters.add(new BasicNameValuePair("Password", password));
            JSONObject object = new JSONParser().makeHttpRequest(URL_LOGIN, parameters);
            int retour = 0;

            try {

                int success = object.getInt(TAG_SUCCESS);
                int idUser =object.getInt("idUser");
                String pseudo = object.getString("pseudo");
                String server = object.getString("Server");
                int idSummoner =object.getInt("summonerIds");
                String rank;
                int profileIconId;
                JSONObject Info = apiMethod.getInfoSummonerByPseudo(pseudo, server);
                int level = Info.getJSONObject(pseudo.toLowerCase()).getInt("summonerLevel");
                profileIconId= Info.getJSONObject(pseudo.toLowerCase()).getInt("profileIconId");
                rank = new APIMethod().getRankUser(idSummoner, server);
                Log.d("success back", String.valueOf(level));
                if (success == 1){
                    retour =1;
                    db = new SQLiteHandler(getApplicationContext());
                    db.addUser(idUser,pseudo,email,level,server,rank,idSummoner,profileIconId);
                    session.setLogin(true);
                    Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                    intent.putExtra("Pseudo",object.getString("pseudo"));
                    startActivity(intent);
                    finish();
                }else{
                    retour=0;
                    Log.d("erreur","impossible");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                return retour;
            }

        }

        @Override
        protected void onPostExecute(Integer object) {
            // Dismiss the progress dialog
            dialog.dismiss();
            if (object==1){
                Toast.makeText(getApplication(), "Welcome", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplication(), "NON", Toast.LENGTH_LONG).show();
            }
        }
    }



}
