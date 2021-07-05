/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm.entity;

import java.io.Serializable;

/**
 *
 * @author Juan
 */
public class UserLDAP implements Serializable{
    
    private String cn;//nombres
    private String sn;//apellidos
    private String uid;//identificador
    private String userPassword;//clave
    private String homeDirectory;//
    private String mailbox;//
    
    private String dn;//distinguished name
    

    public UserLDAP() {
    }

    public UserLDAP(String cn, String sn, String uid, String userPassword, String homeDirectory, String mailbox, String dn) {
        this.cn = cn;
        this.sn = sn;
        this.uid = uid;
        this.userPassword = userPassword;
        this.homeDirectory = homeDirectory;
        this.mailbox = mailbox;
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

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    @Override
    public String toString() {
        //return super.toString(); //To change body of generated methods, choose Tools | Templates.
        return this.uid;
    }    
    
    
}
