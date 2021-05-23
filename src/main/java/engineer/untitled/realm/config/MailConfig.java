package engineer.untitled.realm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String user;
    @Value("${spring.mail.password}")
    private String pass;
    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(user);
        mailSender.setPassword(pass);
        mailSender.setProtocol(protocol);

        Properties properties = mailSender.getJavaMailProperties();

        //properties.setProperty("mail.transport.protocol", protocol);
        //properties.setProperty("mail.debug", debug);
        properties.put("mail.transport.protocol", protocol);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.debug", "true");


        return mailSender;
    };
}
