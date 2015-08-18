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
import com.thinkup.ranning.server.rest.TipoEmail;

public class EmailService {
	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	private static final int SMTP_HOST_PORT = 587;

	public void sendConfirmacion(UsuarioDTO usuario, String token,
			TipoEmail tipo) {
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

			if (tipo.equals(TipoEmail.CONFIRMAR_USUARIO)) {
				part2.setContent(getConfirmarEmail(usuario, token), "text/html");
			} else {
				part2.setContent(getRecuperarEmail(usuario, token), "text/html");
			}
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

	private String getConfirmarEmail(UsuarioDTO usuario, String token) {
		StringBuffer sb = new StringBuffer();
		String url = "http://recorriendo.cloudapp.net/RestServicesRunning-0.0.1-SNAPSHOT/running/usuarios/token/"
				+ usuario.getEmail() + "/" + token;
		sb.append("<div style='margin-top: 20px;'><img src='http://191.237.16.124/RestServicesRunning-0.0.1-SNAPSHOT/resources/img/logoRecorriendo.png' style='padding-left: 20px'> </div>");
		sb.append("<hr/>");
		sb.append("<P><b> Estimad@: </b></P>");
		sb.append(" ");
		sb.append("<P>Gracias por registrarte como Usuario en ReCorriendo.</P>");
		sb.append(" ");
		sb.append("<P>Para finalizar el proceso, ingresá al siguiente vínculo:</P>");
		sb.append(" ");
		sb.append("<a href='" + url + "' >Click Aquí</a>");
		sb.append(" ");
		sb.append("<P>Si no podés acceder al vínculo, por favor copiá el siguiente enlace y pegalo en tu navegador: </P>");
		sb.append(" ");
		sb.append("<span>" + url + "</span>");
		sb.append(" ");
		sb.append("<P>Muchas Gracias.</P>");
		sb.append(" ");
		sb.append("  ");
		sb.append("<hr/>");
		sb.append("<P><b> ReCorriendo </b></P>");
		return sb.toString();
	}

	private String getRecuperarEmail(UsuarioDTO usuario, String token) {
		StringBuffer sb = new StringBuffer();
		String url = "http://recorriendo.cloudapp.net/RestServicesRunning-0.0.1-SNAPSHOT/running/usuarios/recuperarPasswordRequest/"
				+ usuario.getEmail() + "/" + token;
		sb.append("<div style='margin-top: 20px;'><img src='http://191.237.16.124/RestServicesRunning-0.0.1-SNAPSHOT/resources/img/logoRecorriendo.png' style='padding-left: 20px'> </div>");
		sb.append("<hr/>");
		sb.append("<P><b> Estimad@: </b></P>");
		sb.append(" ");
		sb.append("<P>Se inició el proceso de recupero de tu contraseña.</P>");
		sb.append(" ");
		sb.append("<P>Para confirmarlo, ingresá al siguiente vínculo:</P>");
		sb.append(" ");
		sb.append("<a href='" + url + "' >Click Aquí</a>");
		sb.append(" ");
		sb.append("<P>Si no podés acceder al vínculo, por favor copiá el siguiente enlace y pegalo en tu navegador: </P>");
		sb.append(" ");
		sb.append("<span>" + url + "</span>");
		sb.append(" ");
		sb.append("<P>Muchas Gracias.</P>");
		sb.append(" ");
		sb.append("  ");
		sb.append("<hr/>");
		sb.append("<P><b> ReCorriendo </b></P>");
		return sb.toString();
	}
}
