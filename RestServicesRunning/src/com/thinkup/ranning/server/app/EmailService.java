package com.thinkup.ranning.server.app;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.thinkup.ranning.dtos.UsuarioDTO;

public class EmailService {
    private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
    private static final int SMTP_HOST_PORT = 587;
    
	public void sendConfirmacion(UsuarioDTO usuario, String token) {
		final String username = "azure_968640146b384b396774c13b7131d2d4@azure.com";
		final String password = "TtGyE5z49aKXSiH";

		Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.auth", "true");

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        

      

  

		try {
			Transport transport = mailSession.getTransport();

			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress("no-replay@recorriendo.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(usuario.getEmail()));
			message.setSubject("Confirmar Usuario");
			message.setText("Confirmar,"
					+ "http://recorriendo.cloudapp.net/RestServicesRunning-0.0.1-SNAPSHOT/running/usuarios/token/"+usuario.getEmail()+"/"+token);
		      transport.connect
	          (SMTP_HOST_NAME, SMTP_HOST_PORT, username, password);

	        transport.sendMessage(message,
	            message.getRecipients(Message.RecipientType.TO));
	        transport.close();

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					});

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
