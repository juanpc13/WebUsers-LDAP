/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm.entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

/**
 *
 * @author Juan
 */
public class UserLDAP implements Serializable {

    //DEBEN TENER GETTER Y SETTERS
    private String uid;//identificador
    private String cn;//nombres
    private String sn;//apellidos
    private String userPassword;//clave
    private String homeDirectory;//
    private String mailbox;//
    private String mail;//

    //NO DEBEN TENER GETTER Y SETTERS
    private String dn;//distinguished name
    private Attributes attributes;

    public UserLDAP() {
    }

    public UserLDAP(String uid, String cn, String sn, String userPassword, String homeDirectory, String mailbox, String mail, String dn) {
        this.uid = uid;
        this.cn = cn;
        this.sn = sn;
        this.userPassword = userPassword;
        this.homeDirectory = homeDirectory;
        this.mailbox = mailbox;
        this.mail = mail;
        this.dn = dn;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    ////////Variables NO DEBEN DEFINIR GETTER NI SETTER
    public String dn() {
        return dn;
    }

    public void dn(String dn) {
        this.dn = dn;
    }

    public Attributes Attributes() {
        return attributes;
    }

    public void Attributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Attributes generateAttributes() {
        Attribute attribute = attribute = new BasicAttribute("objectClass");
        attribute.add("inetOrgPerson");
        attribute.add("organizationalPerson");
        attribute.add("person");
        attribute.add("simpleSecurityObject");
        attribute.add("CourierMailAccount");
        attribute.add("top");
        attributes = new BasicAttributes();
        attributes.put(attribute);

        Map<String, String> map = this.toMap();
        for (String key : map.keySet()) {
            attributes.put(key, map.get(key));
        }
        return attributes;
    }

    public boolean isComplete() {
        if (cn == null || sn == null || uid == null || userPassword == null || homeDirectory == null || mailbox == null || mail == null) {
            return false;
        }
        if (cn.isEmpty() || sn.isEmpty() || uid.isEmpty() || userPassword.isEmpty() || homeDirectory.isEmpty() || mailbox.isEmpty() || mail.isEmpty()) {
            return false;
        }
        return true;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        for (Method m : this.getClass().getMethods()) {
            if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
                try {
                    Object r = m.invoke(this);
                    if (r != null && !r.toString().equals(this.getClass().toString())) {
                        // Getters a Key-Value
                        String key = m.getName().substring(3);
                        String firstLetter = key.substring(0, 1);
                        key = firstLetter.toLowerCase() + key.substring(1);
                        map.put(key, r.toString());
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(UserLDAP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return map;
    }

    @Override
    public String toString() {
        //return super.toString(); //To change body of generated methods, choose Tools | Templates.
        return this.uid;
    }

}
