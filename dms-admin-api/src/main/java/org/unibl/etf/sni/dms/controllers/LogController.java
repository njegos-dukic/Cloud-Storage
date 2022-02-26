package org.unibl.etf.sni.dms.controllers;

import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.sni.dms.models.entities.LogEntity;
import org.unibl.etf.sni.dms.services.LogService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/logs")
@AllArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping
    public ResponseEntity<List<LogEntity>> getLogs(HttpServletRequest request) {
        KeycloakAuthenticationToken authenticationToken = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) authenticationToken.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken =  session.getToken();
        AccessToken.Access access = accessToken.getRealmAccess();
        Set<String> roles = access.getRoles();
        if (roles.contains("dms-admin"))
            return new ResponseEntity<>(logService.getAllLogs(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
