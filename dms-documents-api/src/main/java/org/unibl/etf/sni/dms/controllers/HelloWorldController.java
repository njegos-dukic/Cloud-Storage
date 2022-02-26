package org.unibl.etf.sni.dms.controllers;

import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.sni.dms.model.entities.LogEntity;
import org.unibl.etf.sni.dms.services.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class HelloWorldController {

    private final LogService logService;

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

        Map<String, Object> claims = accessToken.getOtherClaims();
        StringBuilder claimsAsString = new StringBuilder();
        for (var claim : claims.entrySet()) {
            claimsAsString.append("<p>").append(claim.getKey()).append(" ➞ ").append(claim.getValue()).append("</p>");
        }

        accessToken.setOtherClaims("can-read", false);
        accessToken.setOtherClaims("root", "/updated-root");

        return "<p>Hello " + firstname + " " + lastname + " with username: " + username + "</p>" +
                "<hr>" +
                "<p>" + claimsAsString + "</p>" +
                "<hr>" +
                "<img src=\"https://guide.quickscrum.com/wp-content/uploads/2021/04/how-to-define-roles-and-responsibilities-for-team-members.png\" width=300></img>" +
                "<hr>" +
                "<a href='/logout'>Logout</a>";
    }

    @GetMapping(path = "/logout")
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = getKeycloakUser(request).getPreferredUsername();
        LogEntity logEntity = new LogEntity(0, "INFO", user + " logged out", Instant.now());
        logService.insert(logEntity);

        request.logout();
        response.sendRedirect("http://localhost:4200");
    }

    private AccessToken getKeycloakUser(HttpServletRequest request) {
        KeycloakAuthenticationToken authenticationToken = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) authenticationToken.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        return session.getToken();
    }
}
