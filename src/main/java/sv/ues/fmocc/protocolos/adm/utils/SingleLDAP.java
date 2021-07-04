/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

/**
 *
 * @author jcpleitez
 */
public class SingleLDAP {
    //Instancia Singleton
    private static SingleLDAP instancia;
    //Atributos de getter y setter
    private String host;//atol.com
    private String LectorDn;//cn=admin,dc=atol,dc=com
    private String LectorPassword;//abc123
    //Conexiones
    private LdapConnection connection;
    private LdapConnectionConfig connectionConfig;
    

    private SingleLDAP(String currentURL) throws LdapInvalidDnException, LdapException, URISyntaxException {
        //Se obtiene el host
        this.host = new URI(currentURL).getHost();
        host = (host.startsWith("www.") ? host.substring(4) : host);
        //Declaraciones
        Dn userDn = new Dn(LectorDn);
        //Se configura la conexion
        connectionConfig = new LdapConnectionConfig();
        connectionConfig.setLdapHost(host);
        connectionConfig.setLdapPort(390);
        //Se crea la conexion
        connection = new LdapNetworkConnection(connectionConfig);
        connection.setTimeOut(-1);
        connection.bind(userDn, this.LectorPassword);
    }

    public static SingleLDAP getInstanceLDAP(String currentURL) throws LdapException {
        if (instancia == null) {
            try {
                instancia = new SingleLDAP(currentURL);
            } catch (LdapInvalidDnException | URISyntaxException ex) {
                Logger.getLogger(SingleLDAP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instancia;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    
    public String getLectorDn() {
        return LectorDn;
    }

    public void setLectorDn(String LectorDn) {
        this.LectorDn = LectorDn;
    }

    public String getLectorPassword() {
        return LectorPassword;
    }

    public void setLectorPassword(String LectorPassword) {
        this.LectorPassword = LectorPassword;
    }
    
}
