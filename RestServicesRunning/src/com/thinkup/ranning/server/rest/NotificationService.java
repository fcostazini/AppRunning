package com.thinkup.ranning.server.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpUtils;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.thinkup.ranning.dao.CarreraDAO;
import com.thinkup.ranning.dao.UsuarioCarreraDAO;
import com.thinkup.ranning.dtos.Content;
import com.thinkup.ranning.dtos.MessageContent;
import com.thinkup.ranning.dtos.TopicContent;
import com.thinkup.ranning.entities.Carrera;
import com.thinkup.ranning.entities.UsuarioCarrera;
import com.thinkup.ranning.exceptions.PersistenciaException;

@Stateless
@Path("/notificaciones")
public class NotificationService {
	@Inject
	private CarreraDAO carreraDao;
	@Inject
	private UsuarioCarreraDAO usuarioCarreraDao;
	private static final String API_KEY = "AIzaSyBmSxpiySLAri_GS6UPYZrDGJdRgylOPAI"; 

	
	public void notificarSuscriptos(Integer idCarrera) {
		List<UsuarioCarrera> usuarios = usuarioCarreraDao
				.getUsuariosByIdCarrera(idCarrera);
		
		MessageContent content = new MessageContent();
		try {
			Carrera c = carreraDao.getById(idCarrera);
			content.setTitle(c.getNombre());
			content.setBody("Se actualizó la carrera!");
			content.addData("idCarrera",idCarrera.toString());
			content.addData("urlImagen",c.getUrlImagen());
			for (UsuarioCarrera uc : usuarios) {
				if (uc.getUsuario().getGsmToken() != null) {
					content.addDestinatario(uc.getUsuario().getGsmToken());
				}
			}
			this.sendPost(content);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
	
	}
	@Path("/{title}/{message}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public void notificarNoticias(@PathParam("title") String titulo, @PathParam("message") String mensaje) {
				
		TopicContent content = new TopicContent();
		
			content.setTitle(titulo);
			content.setMessage(mensaje);
			content.setTopic("/topics/noticias");
			this.sendPost(content);
		
	
	}

	public void sendPost(Content content){
		try {
			String url = "https://gcm-http.googleapis.com/gcm/send";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");

						// Send post request
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
	        con.setRequestProperty("Authorization", "key="+API_KEY);
	        
	        // 5. Add JSON data into POST request body 
	        
            //`5.1 Use Jackson object mapper to convert Contnet object into JSON
            Gson mapper = new Gson();
 
            // 5.2 Get connection output stream
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
 
            // 5.3 Copy Content "JSON" into 
            wr.write(mapper.toJson(content).getBytes());
 
            // 5.4 Send the request
            wr.flush();
 
            // 5.5 close
            wr.close();
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

}
