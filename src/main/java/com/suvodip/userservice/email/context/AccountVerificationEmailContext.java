package com.suvodip.userservice.email.context;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import com.suvodip.userservice.models.User;

public class AccountVerificationEmailContext extends AbstractEmailContext {
    private String token;
    
    @Override
    public  <T> void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        User user = (User) context; // we pass the customer information
        put("name", user.getName());
        setTemplateLocation("emails/email-verification");
        setSubject("Complete your registration");
        setFrom("no-reply@javadevjournal.com");
        setTo(user.getUsername());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
    	// UriComponentsBuilder.fromHttpUrl encodes symbols in different way which RequestParams cannot decode
    	DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
    	factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);
        final URI url= factory.uriString(baseURL)
                .path("/user/verify").queryParam("token", token).build();
        put("verificationURL", url);
        System.out.println("Verification url"+ url);
    }
}
