package studios.thinkup.com.apprunning.provider.restProviders;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import studios.thinkup.com.apprunning.model.entity.UsuarioApp;

/**
 * Created by dcamarro on 29/07/2015.
 */
public class CrearUsuarioTask extends AsyncTask<UsuarioApp, Void, Respuesta<List<UsuarioApp>>> {
    @Override
    protected Respuesta<List<UsuarioApp>> doInBackground(UsuarioApp... params) {
        try {
            final String url = "http://localhost:8080/RestServicesRunning/running/usuarios/saveUsuario";
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            UsuarioApp u = new UsuarioApp();
            u.setApellido("Prueba rest");
            u.setEmail("aaa@gmail.com");
            u.setNick("pirulito");
            u.setFechaNacimiento("25/07/1999");

            Respuesta<List<UsuarioApp>> greeting = restTemplate.postForObject(url, u, Respuesta.class, (Object[]) null);
            return greeting;
        } catch (Exception e) {
            Log.e("Explotó todo!!!", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Respuesta<List<UsuarioApp>> respuesta) {
        System.out.println(respuesta.getDto());
    }

}



