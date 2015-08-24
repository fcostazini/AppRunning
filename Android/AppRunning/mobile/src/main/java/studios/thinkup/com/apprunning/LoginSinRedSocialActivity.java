package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import studios.thinkup.com.apprunning.model.PasswordEncoder;
import studios.thinkup.com.apprunning.model.entity.CheckUsuarioPassDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.LoginUsuarioService;
import studios.thinkup.com.apprunning.provider.RecuperarPassService;


public class LoginSinRedSocialActivity extends Activity implements LoginUsuarioService.IServicioActualizacionAmigoHandler, RecuperarPassService.IServicioActualizacionAmigoHandler {
    private LoginUsuarioService us;
    private RecuperarPassService rps;
    private static ProgressDialog pd;
    protected static void showProgress(Context context, String message) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();




    }

    protected static void hideProgress() {
        if (pd != null) {
            pd.dismiss();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sin_red_social);

        Button loggin = (Button)findViewById(R.id.btn_loggin);
        loggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usuario = (TextView) findViewById(R.id.user_name);
                TextView password = (TextView) findViewById(R.id.pass);
                if (usuario.getText().length() <= 0 || password.getText().length() <= 0) {
                    Toast.makeText(LoginSinRedSocialActivity.this, "Complete los datos", Toast.LENGTH_LONG).show();
                } else {
                    CheckUsuarioPassDTO c = new CheckUsuarioPassDTO(usuario.getText().toString(), PasswordEncoder.encodePass(password.getText().toString()));
                    showProgress(LoginSinRedSocialActivity.this,getString(R.string.entrando));
                    us = new LoginUsuarioService(LoginSinRedSocialActivity.this,LoginSinRedSocialActivity.this);
                    us.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, c);
                }


            }
        });

        TextView recuperar = (TextView)findViewById(R.id.lbl_recuperar);
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView usuario = (TextView) findViewById(R.id.user_name);
                if(usuario.getText().length()<=0){
                    Toast.makeText(LoginSinRedSocialActivity.this,"Complete el email",Toast.LENGTH_LONG).show();
                }else{
                    showProgress(LoginSinRedSocialActivity.this,"Espere por favor...");
                    rps = new RecuperarPassService(LoginSinRedSocialActivity.this,LoginSinRedSocialActivity.this);
                    rps.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, usuario.getText().toString());
                }

            }
        });


    }


    @Override
    public void onOk(UsuarioApp u) {
        hideProgress();
        Intent i = new Intent(this,StartUpActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(UsuarioApp.FIELD_ID,u);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtras(b);
        startActivity(i);
        finish();

    }

    @Override
    public void onOk() {
        hideProgress();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se enviará un email para recuperar el password")
                .setTitle(getString(R.string.usuario_invalido))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.ic_ayuda).show();

    }

    @Override
    public void onError(String error) {
        hideProgress();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error)
                .setTitle(getString(R.string.usuario_invalido))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.ic_warning).show();


    }
}
