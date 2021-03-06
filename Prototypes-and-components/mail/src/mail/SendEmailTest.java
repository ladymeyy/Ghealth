package mail;

import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
/**
 * @author Pinto.com
 */
 
public class SendEmailTest {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	Address[] addresses = new Address[10]; //for future needs
 
	/**
	 * Main for testing
	 * @param args
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void main(String args[]) throws AddressException, MessagingException {
		generateAndSendEmail();
		System.out.println("\n\n ===> The System has just sent an Email successfully. Check your email..");
	}
	
	/**
	 * This function will be available for outside class interfacing 
	 * here it is only for testing
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void generateAndSendEmail() throws AddressException, MessagingException {
		/**
		 *  Step1: Setting mail Server Properties
		 */
		System.out.println("Setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		/**
		 *  Step2: getting mail session, using the GMailAuthenticator class
		 */
		System.out.println("\n\nGetting Mail Session..");
		getMailSession = Session.getInstance(mailServerProperties, new GMailAuthenticator("braudehospital", "maabadag5"));
		
		/**
		 * another Authenticator if we'll need it
		 */
		/*
		getMailSession = Session.getInstance(mailServerProperties, new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication("", "");
		    }
		});
		*/
		
		//NO Authentication session
		//getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		
		/**
		 * MIME and recipients
		 */
		generateMailMessage = new MimeMessage(getMailSession);
		//one recipient
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("pinto.com@gmail.com"));
		//multiple recipient
		//generateMailMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse("pinto.com@gmail.com,amirsht@gmail.com,orindrel@gmail.com,ladymeyy@gmail.com,beartur89@gmail.com"));
		
		
		//if we want CC
		//generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("cc@cc.com"));
		
		
		/**
		 * Message itself
		 */
		generateMailMessage.setSubject("Email subject");
		String emailBody = "email body message" + "<br><br> Regards, <br>Braude Hospital" + "<br><br><br><br> this mail sent to you through BraudeHospital new java mail system (that works)";
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session has been created successfully..");
 
		/**
		 *  Step3: Choose transport type
		 */
		System.out.println("\nSending mail.......");
		Transport transport = getMailSession.getTransport("smtp");
 
		/**
		 *  Step 4: Connection
		 */
		transport.connect("smtp.gmail.com", "braudehospital", "maabadag5");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}

/**
 * Authenticator class
 * Made for Gmail ass-wipe Security system 
 *
 * @author Pinto.com
 */
class GMailAuthenticator extends Authenticator {
    String user;
    String pw;
    public GMailAuthenticator (String username, String password)
    {
       super();
       this.user = username;
       this.pw = password;
    }
   public PasswordAuthentication getPasswordAuthentication()
   {
      return new PasswordAuthentication("braudehospital", "maabadag5");
   }
}
