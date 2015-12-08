package global;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail
{
  String toaddress = null;

  String title = null;

  String body = null;

  public SendEmail(String toaddress, String title, String body)
  {
    this.toaddress = toaddress;
    this.title = title;
    this.body = body;
  }

  public boolean execSend()
    throws Exception
  {
    Properties props = new Properties();
    props.put("mail.smtp.host", Constants.EMAIL_SMTP_HOST);
    props.put("mail.smtp.auth", "true");

    Session session = Session.getDefaultInstance(props, 
      new Authenticator() {
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(Constants.EMAIL_ACCOUNT, 
          Constants.EMAIL_PASSWORD);
      }
    });
    MimeMessage message = new MimeMessage(session);

    message.setSubject(this.title);

    message.setFrom(new InternetAddress(Constants.EMAIL_ACCOUNT));

    message.addRecipient(Message.RecipientType.TO, new InternetAddress(
      this.toaddress));

    message.setSentDate(new Date());

    Multipart mp = new MimeMultipart("related");

    BodyPart bodyPart = new MimeBodyPart();

    bodyPart.setContent(this.body, "text/html;charset=utf-8");

    mp.addBodyPart(bodyPart);

    message.setContent(mp);

    Transport.send(message);
    return true;
  }
}