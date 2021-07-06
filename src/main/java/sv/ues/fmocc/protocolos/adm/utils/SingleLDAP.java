/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm.utils;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;

/**
 *
 * @author jcpleitez
 */
public class SingleLDAP {

    //Propidades y Conexiones
    private final Properties env;
    private DirContext context;

    public SingleLDAP(String userdn, String password) throws NamingException {
        String host = "192.168.122.68";
        env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://" + host + ":389");
        env.put(Context.SECURITY_PRINCIPAL, userdn);
        env.put(Context.SECURITY_CREDENTIALS, password);
        // Se realiza la conexion
        context = new InitialDirContext(env);
    }

    public DirContext getContext() {
        return context;
    }

    public void setContext(DirContext context) {
        this.context = context;
    }

}
