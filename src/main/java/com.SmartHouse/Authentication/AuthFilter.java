package com.SmartHouse.Authentication;



import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.SmartHouse.Database.DBConnection;
import org.glassfish.jersey.internal.util.Base64;

import java.util.List;
import java.util.StringTokenizer;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_PREFIX = "Basic ";
    private static final String LOGIN_URL_PREFIX = "login";


    @Override
    public void filter(ContainerRequestContext requestContext) {

        if (requestContext.getUriInfo().getPath().contains(LOGIN_URL_PREFIX)) {
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
            if (authHeader != null && authHeader.size() > 0) {
                String authToken = authHeader.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_PREFIX, "");
                String decodingString = Base64.decodeAsString(authToken);
                StringTokenizer tokenizer = new StringTokenizer(decodingString, ":");
                String userName = tokenizer.nextToken();
                String password = tokenizer.nextToken();


                if (new DBConnection().emailExists(userName)) {
                    if (password.equals(new DBConnection().getPassword(userName))) {
                        return;
                    }
                }

            }
            Response unauthorizedResponse = Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid login! Please check your email and password")
                    .build();
            requestContext.abortWith(unauthorizedResponse);

        }
    }
}
