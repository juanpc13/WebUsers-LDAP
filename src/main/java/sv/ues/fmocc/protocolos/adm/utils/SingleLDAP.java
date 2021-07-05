/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm.utils;

import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

/**
 *
 * @author jcpleitez
 */
public class SingleLDAP {

    //Instancia Singleton
    private static SingleLDAP instancia;
    //Propidades y Conexiones
    private Properties env;
    private DirContext context;

    public SingleLDAP(String userdn, String password) {
        String host = "192.168.122.68";
        env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://" + host + ":389");
        env.put(Context.SECURITY_PRINCIPAL, userdn);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            // Se realiza la conexion
            context = new InitialDirContext(env);
        } catch (NamingException ex) {
            Logger.getLogger(SingleLDAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DirContext getContext() {
        return context;
    }

    public void setContext(DirContext context) {
        this.context = context;
    }

    public static SingleLDAP getInstanceLDAP(String host, String domain) {
        if (instancia == null) {
            instancia = new SingleLDAP(host, domain);
        }
        return instancia;
    }

}
