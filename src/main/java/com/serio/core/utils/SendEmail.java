package com.serio.core.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail{
	
  private String from = ConfigFileParams.getEmailFrom();
  private String username = ConfigFileParams.getEmailUsername();
  private String password = ConfigFileParams.getEmailPassword();
  private String host = ConfigFileParams.getEmailHost();


 
  public boolean start( String email, String emailTemplate, String subject ) {
    Boolean result = false;
    try {
      // 认证server邮箱
      Properties prop = new Properties();
      prop.setProperty("mail.host", host);
      prop.setProperty("mail.transport.protocol", "smtp");
      prop.setProperty("mail.smtp.auth", "true");
      Session session = Session.getInstance(prop);
      session.setDebug(true); //启动JavaMail调试,从控制台中可以看到服务器的响应信息
      Transport ts = session.getTransport();
      ts.connect(host, username, password);
      //创建 邮件内容
      Message message = createEmail(session, email, emailTemplate, subject);
      //发送邮件 
      ts.sendMessage(message, message.getAllRecipients());
      ts.close();
      result = true;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  /**
   * 创建 邮箱标题 以及文本内容
   * @param session
   * @param user
   * @return
   * @throws Exception
   */
  public Message createEmail(Session session, String email, String emailTemplate, String subject) throws Exception {

    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(from));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
    message.setSubject("vpes 注册邮件");
    message.setContent(emailTemplate, "text/html;charset=UTF-8");
    message.saveChanges();
    return message;
  }

}
