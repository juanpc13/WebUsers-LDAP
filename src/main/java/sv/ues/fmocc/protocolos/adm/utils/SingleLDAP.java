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
    private DirContext connection;

    private SingleLDAP(String host, String domain) {
        host = "192.168.122.68";
        domain = "atol";
        env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://" + host + ":389");
        //env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=atol,dc=com");
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=atol,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "abc123");

        try {
            // Se realiza la conexion
            connection = new InitialDirContext(env);
        } catch (NamingException ex) {
            Logger.getLogger(SingleLDAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static SingleLDAP getInstanceLDAP(String host, String domain) {
        if (instancia == null) {
            instancia = new SingleLDAP(host, domain);
        }
        return instancia;
    }

    public void reconnect(String host, String domain) {
        instancia = new SingleLDAP(host, domain);
    }

    public boolean authAdm(String userCn, String password) {
        if (env != null && connection != null) {
            if (env.getProperty("userCn").equals(userCn) && env.getProperty("java.naming.security.credentials").equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void search(String search, String filter) {
        if(connection == null){
            return;
        }
        String[] reqAtt = {"cn", "sn"};
        SearchControls ctls = new SearchControls();
        ctls.setReturningObjFlag(true);

        //Asignamos los atributos a devolver
        ctls.setReturningAttributes(reqAtt);
        ctls.setSearchScope(SearchControls.OBJECT_SCOPE);

        NamingEnumeration answer;
        try {
            answer = connection.search(search, filter, ctls);
            while (answer.hasMore()) {
                SearchResult result = (SearchResult) answer.next();
                Attributes attr = result.getAttributes();
                //String name = attr.get("cn").get(0).toString();
                //deleteUserFromGroup(name,"Administrators");
                System.out.println(attr.get("cn"));
                System.out.println(attr.get("uid"));
                //doSomething(map);
            }
        } catch (NamingException ex) {
            Logger.getLogger(SingleLDAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
