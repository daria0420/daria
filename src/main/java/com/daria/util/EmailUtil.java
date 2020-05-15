package com.daria.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @Author Daria
 * @Description
 * @Date 2019/7/30 -- 19:23
 */
public class EmailUtil {

    public static void toEmail(String toEmail, String name, String toMsg)  {
        Properties props = new Properties();


        try{
            // 开启debug调试
            props.setProperty("mail.debug", "true");
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", "smtp.qq.com");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");

            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props);

            Message msg = new MimeMessage(session);
            msg.setSubject("用户忘记密码邮件");
            StringBuilder builder = new StringBuilder();
            builder.append("尊敬的用户 " + name + " 您的密码是：" + toMsg + "，请妥善保管");
            msg.setText(builder.toString());
            msg.setFrom(new InternetAddress("1274063780@qq.com"));

            Transport transport = session.getTransport();
            transport.connect("smtp.qq.com",
                    "1274063780@qq.com",
                    "fgimqjpltueogcjj"
            );

            transport.sendMessage(msg, new Address[] { new InternetAddress(toEmail) });
            transport.close();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}

