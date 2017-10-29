package com.example.pollingtest.servicepoller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.net.SocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.pollingtest.model.CallerConfiguration;
import com.example.pollingtest.model.ClientService;
import com.example.pollingtest.repository.ClientServiceRepository;

@Component
public class ServicePollerAsync {
	
	@Autowired
	private ClientServiceRepository clientServiceRepository;
	
	@Autowired
    public JavaMailSender emailSender;
	
	@Value("${com.example.pollingtest.notification.email.subject}")
	private String emailSubject;
	
	@Value("${com.example.pollingtest.notification.email.text}")
	private String emailText;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Async("processExecutor")
	public void pollSingleService(final ClientService clientService, final Date now) {
		if (clientService.getOutage() == null || !(now.after(clientService.getOutage().getStartTime())
				&& now.before(clientService.getOutage().getEndTime()))) {

			boolean servicePollStatus = false;

			// general service polling function.
			List<CallerConfiguration> callerConfigurationList = clientService.getCallerConfigs();
			for (CallerConfiguration callerConfiguration : callerConfigurationList) {
				if (callerConfiguration.getNextPoll() == 1) { // run by nextPoll since last service status is true

					try {
						servicePollStatus = pollServices(clientService.getHost(), clientService.getPort());
						// get back to starting point of polling
						callerConfiguration.setNextPoll(callerConfiguration.getPollingFrequency());
					} catch (IOException e) {
						servicePollStatus = false;
					}
					clientService.setUpStatus(servicePollStatus);

				} else {
					callerConfiguration.setNextPoll((callerConfiguration.getNextPoll() - 1));
				}
			} // callerConfiguration for loop end

			if (!clientService.getUpStatus()) { // if service is down as of last poll
				for (CallerConfiguration callerConfiguration : callerConfigurationList) {
					if (callerConfiguration.getGraceTimeExpiration() == 1) { // run by graceTimeExpiration since
																				// last service status is false

						try {
							servicePollStatus = pollServices(clientService.getHost(), clientService.getPort());
							if (!servicePollStatus) {
								for (String notifyEmail : callerConfiguration.getNotifyEmail()) {
									sendNotificationEmail(notifyEmail, clientService.getHost(), clientService.getPort());
									LOGGER.info("Email notification is sent to " + notifyEmail + " about "
											+ clientService.getHost() + ":" + clientService.getPort());
								}
							}
							// get back to starting point of downtime polling
							callerConfiguration.setGraceTimeExpiration(callerConfiguration.getGraceTime());
						} catch (IOException e) {
							servicePollStatus = false;
						}
						clientService.setUpStatus(servicePollStatus);

					} else {
						callerConfiguration
								.setGraceTimeExpiration((callerConfiguration.getGraceTimeExpiration() - 1));
					}
				} // callerConfiguration for loop end

			}

			clientServiceRepository.save(clientService);

		} // service outage if check end
	}
	
	private boolean pollServices(final String host, int port) throws IOException {

		Socket socket = null;
		boolean isConnected = false;

		try {
			socket = SocketFactory.getDefault().createSocket(host, port);
			isConnected = socket.isConnected();
			LOGGER.info("CONNECTED: " + isConnected);
		} catch (UnknownHostException e) {
			LOGGER.warn("ERROR unknown host: " + e);
		} catch (IOException e) {
			LOGGER.error("ERROR IO: " + e);
		} finally {
			if (socket != null) {
				socket.close();
			}
		}

		return isConnected;

	}
	
	private boolean sendNotificationEmail(final String toEmailAddress, final String host, int port) {

		boolean isEmailSent = false;
		
		try {
			SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setTo(toEmailAddress); 
	        message.setSubject(emailSubject); 
	        message.setText(String.format(emailText, host, port));
	        //emailSender.send(message);
	        isEmailSent = true;
			
		} catch(MailException mailException) {
			isEmailSent = false;
		}
		
		return isEmailSent;

	}
	
}