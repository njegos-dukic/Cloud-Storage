import { KeycloakService } from "keycloak-angular";

export function initializeKeycloak (keycloak: KeycloakService) {
    return () =>
      keycloak.init({
        config: {
          url: 'http://localhost:8180/auth',
          realm: 'DocumentManagementSystem',
          clientId: 'dms-ui',
        },
          initOptions: {
          onLoad: 'login-required',
          checkLoginIframe: true
        }     
      }
    );
  }