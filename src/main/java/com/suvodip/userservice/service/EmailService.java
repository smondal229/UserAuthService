package com.suvodip.userservice.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.suvodip.userservice.email.context.AbstractEmailContext;

public interface EmailService {
	void sendMail(AbstractEmailContext email) throws AddressException, MessagingException, IOException;
}
