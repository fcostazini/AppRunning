package com.thinkup.ranning.server.app;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.thinkup.ranning.dtos.UsuarioDTO;

public class EmailService {
	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	private static final int SMTP_HOST_PORT = 587;

	public void sendConfirmacion(UsuarioDTO usuario, String token) {
		final String username = "azure_968640146b384b396774c13b7131d2d4@azure.com";
		final String password = "TtGyE5z49aKXSiH";

		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", SMTP_HOST_NAME);
		properties.put("mail.smtp.port", SMTP_HOST_PORT);
		properties.put("mail.smtp.auth", "true");

		Authenticator auth = new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		try {


		
			Session mailSession = Session.getInstance(properties, auth);
			mailSession.setDebug(true);

			
			Multipart multipart = new MimeMultipart("alternative");
			BodyPart part2 = new MimeBodyPart();
			
			StringBuffer sb = new StringBuffer();
				
			sb.append("<P><b> Estimad@: </b></P>");
			sb.append(" ");
			sb.append("<P>Gracias por registrarte como Usuario en ReCorriendo.</P>");
			sb.append(" ");
			sb.append("<P>Para finalizar el proceso, ingresá al siguiente vínculo:</P>");
			sb.append(" ");
			sb.append("  ");
			sb.append(" ");
			sb.append("<P>Si no podés acceder al vínculo, por favor copiá el siguiente enlace y pegalo en tu navegador: </P>");
			sb.append(" ");
			sb.append("<a href='http://recorriendo.cloudapp.net/RestServicesRunning-0.0.1-SNAPSHOT/running/usuarios/token/" + usuario.getEmail() + "/" + token+ "' >Click Aquí</a>");
			sb.append(" ");
			sb.append("<P>Muchas Gracias.</P>");
			sb.append(" ");
			sb.append("  ");
			sb.append(" ");
			sb.append("<P><b> ReCorriendo </b></P>");
			part2.setContent(sb.toString(),"text/html");
			multipart.addBodyPart(part2);

			
			
			Message message = new MimeMessage(mailSession);
			message.setContent(multipart);
			message.setFrom(new InternetAddress("no-reply@recorriendo.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(usuario.getEmail()));
			message.setSubject("Confirmar Usuario");
			Transport transport = mailSession.getTransport();
			// Connect the transport object.
			transport.connect();
			// Send the message.
			transport.sendMessage(message, message.getAllRecipients());
			// Close the connection.
			transport.close();

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
