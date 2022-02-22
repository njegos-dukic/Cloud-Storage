import { KeycloakService } from "keycloak-angular";

export function initializeKeycloak (keycloak: KeycloakService) {
    return () =>
      keycloak.init({
        config: {
          url: 'http://localhost:8180' + '/auth',
          realm: 'Document Management System',
          clientId: 'dms-ui',
        },
          initOptions: {
          onLoad: 'login-required',
          checkLoginIframe: true
        }     
      }
    );
  }