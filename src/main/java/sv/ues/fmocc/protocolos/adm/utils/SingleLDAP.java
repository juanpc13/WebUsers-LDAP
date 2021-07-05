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
    //private String LectorDn;//cn=admin,dc=atol,dc=com
    private String UserDn = "cn=admin,dc=atol,dc=com";
    private String LectorPassword = "abc123";
    //Conexiones
    private LdapConnection connection;
    private LdapConnectionConfig connectionConfig;

    private SingleLDAP(String host) {
        //Se configura la conexion
        connectionConfig = new LdapConnectionConfig();
        //connectionConfig.setLdapHost(host);
        connectionConfig.setLdapHost("192.168.122.68");
        connectionConfig.setLdapPort(389);
        //Se crea la conexion
        connection = new LdapNetworkConnection(connectionConfig);
        connection.setTimeOut(-1);
        try {
            Dn dn = new Dn(UserDn);
            connection.bind(dn, this.LectorPassword);
            System.out.println("Is Connected? " + connection.isConnected());
        } catch (LdapInvalidDnException ex) {
            Logger.getLogger(SingleLDAP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LdapException ex) {
            Logger.getLogger(SingleLDAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static SingleLDAP getInstanceLDAP(String currentURL) {
        if (instancia == null) {
            instancia = new SingleLDAP(currentURL);
        }
        return instancia;
    }

    public LdapConnection getConnection() {
        return connection;
    }

    public void setConnection(LdapConnection connection) {
        this.connection = connection;
    }

    public String getLectorDn() {
        return UserDn;
    }

    public void setLectorDn(String LectorDn) {
        this.UserDn = LectorDn;
    }

    public String getLectorPassword() {
        return LectorPassword;
    }

    public void setLectorPassword(String LectorPassword) {
        this.LectorPassword = LectorPassword;
    }

}
