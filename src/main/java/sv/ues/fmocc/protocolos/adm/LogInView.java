/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.directory.ldap.client.api.LdapConnection;
import sv.ues.fmocc.protocolos.adm.entity.UserLDAP;
import sv.ues.fmocc.protocolos.adm.utils.SessionUtils;

/**
 *
 * @author jcpleitez
 */
@Named
@ViewScoped
public class LogInView implements Serializable{
    
    private UserLDAP user;
    private LdapConnection masterConnection;
    
    private List<UserLDAP> usuarios;
    
    
    @PostConstruct
    public void init() {
        
        try {
            String uid = SessionUtils.getUserUID();
            if (uid != null) {
                System.out.println("UID="+uid);
                //user = masterConnection.find(uid);
                
                Iterator<UserLDAP> iUsuarios = usuarios.iterator();
                while (iUsuarios.hasNext()) {
                    UserLDAP user = iUsuarios.next();
                    if(uid.equals(user.getUid())){
                        this.user = user;
                    }
                }
                
            }
        } catch (IOException ex) {            
            Logger.getLogger(LogInView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
