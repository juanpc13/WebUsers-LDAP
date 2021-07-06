/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import sv.ues.fmocc.protocolos.adm.entity.UserLDAP;
import sv.ues.fmocc.protocolos.adm.utils.SessionUtils;
import sv.ues.fmocc.protocolos.adm.utils.SingleLDAP;

/**
 *
 * @author jcpleitez
 */
@Named
@ViewScoped
public class AdmView implements Serializable {

    private UserLDAP user;
    private SingleLDAP singleLDAP;

    @PostConstruct
    public void init() {
        user = new UserLDAP();
        singleLDAP = new SingleLDAP(SessionUtils.getUserUserDn(), SessionUtils.getUserPassword());
    }

    public UserLDAP getUser() {
        return user;
    }

    public void setUser(UserLDAP user) {
        this.user = user;
    }
    
    public void updateUSER() {
        //user.setCn("");//user.setSn("");//user.setUserPassword("");//user.setUid("");       
        //Creando UID
        String uid = "";
        uid += user.getCn() == null ? "" : user.getCn().toLowerCase();
        uid += user.getCn() == null || user.getCn().isEmpty() || user.getSn() == null || user.getSn().isEmpty() ? "":".";
        uid += user.getSn() == null ? "" : user.getSn().toLowerCase();        
        user.setUid(uid);
        // Dependen del UID
        user.setHomeDirectory("/home/vmail/atol/" + user.getUid());
        user.setMail(user.getUid() + "@atol.com");
        user.setMailbox("atol/" + user.getUid() + "/");
    }

    public void crearUsuario() {
        if (user.isComplete()) {
            Attributes attributes = user.generateAttributes();
            try {
                singleLDAP.getContext().createSubcontext("uid=" + user.getUid() + ",ou=sistemas,ou=usuarios,dc=atol,dc=com", attributes);
            } catch (NamingException ex) {
                Logger.getLogger(AdmView.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
