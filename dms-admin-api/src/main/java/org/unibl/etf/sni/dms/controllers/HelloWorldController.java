package org.unibl.etf.sni.dms.controllers;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping
public class HelloWorldController {

    @GetMapping("/hello")
    public String sayIt(HttpServletRequest request) {

        KeycloakAuthenticationToken authenticationToken = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) authenticationToken.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        String username = accessToken.getPreferredUsername();
        String emailID = accessToken.getEmail();
        String lastname = accessToken.getFamilyName();
        String firstname = accessToken.getGivenName();
        String realmName = accessToken.getIssuer();

        return "<p>Hello " + firstname + " " + lastname + " with username: " + username + "</p>" +
                "<hr>" +
                "<img src=\"https://i.kym-cdn.com/photos/images/facebook/000/764/965/47a.jpg\" width=300></img>" +
                "<hr>" +
                "<a href='/logout'>Logout</a>";
    }

    @GetMapping(path = "/logout")
    private String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.logout();

        return "<p>Successfully logged out!</p>" +
                "<hr>" +
                "<a href=\"http://localhost:9000/hello\">Home</a>";
    }
}
