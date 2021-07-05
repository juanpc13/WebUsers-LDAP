/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.directory.ldap.client.api.LdapConnection;
import sv.ues.fmocc.protocolos.adm.entity.UserLDAP;
import sv.ues.fmocc.protocolos.adm.service.UserService;
import sv.ues.fmocc.protocolos.adm.utils.SessionUtils;
import sv.ues.fmocc.protocolos.adm.utils.SingleLDAP;

/**
 *
 * @author jcpleitez
 */
@Named
@ViewScoped
public class LogInView implements Serializable {

    private UserLDAP user;    
    private List<UserLDAP> usuarios;
    
    private SingleLDAP singleLDAP;

    @PostConstruct
    public void init() {
        user = new UserLDAP();
        usuarios = new UserService().getUsuarios();
        
        //Dominio y Conexion 
        //HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //String serverName = req.getServerName();
        singleLDAP = SingleLDAP.getInstanceLDAP("192.168.122.68", "atol");
        //System.out.println(singleLDAP.authAdm("admin", "abc123"));
        singleLDAP.search("ou=sistemas,ou=usuarios,dc=atol,dc=com", "(objectClass=top)");
    }

    public UserLDAP find(String uid) {
        Iterator<UserLDAP> iUsuarios = usuarios.iterator();
        while (iUsuarios.hasNext()) {
            UserLDAP user = iUsuarios.next();
            if (uid.equals(user.getUid())) {
                return user;
            }
        }
        return null;
    }

    public UserLDAP getUser() {
        return user;
    }

    public void setUser(UserLDAP user) {
        this.user = user;
    }

    public void iniciarSesion() {
        
    }

}
