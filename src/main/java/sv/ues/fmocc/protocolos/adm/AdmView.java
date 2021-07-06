/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
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
    private UserLDAP selectedUsuario;
    private List<UserLDAP> usuarios;
    private SingleLDAP singleLDAP;

    @PostConstruct
    public void init() {
        user = new UserLDAP();
        //Se buscan todos los usuarios
        usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    public UserLDAP getUser() {
        return user;
    }

    public void setUser(UserLDAP user) {
        this.user = user;
    }

    public UserLDAP getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(UserLDAP selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public List<UserLDAP> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UserLDAP> usuarios) {
        this.usuarios = usuarios;
    }

    public void updateUID() {
        //user.setCn("");//user.setSn("");//user.setUserPassword("");//user.setUid("");       
        //Creando UID
        String uid = "";
        uid += user.getCn() == null ? "" : user.getCn().toLowerCase();
        uid += user.getCn() == null || user.getCn().isEmpty() || user.getSn() == null || user.getSn().isEmpty() ? "" : ".";
        uid += user.getSn() == null ? "" : user.getSn().toLowerCase();
        user.setUid(uid);
        updateUSER();
    }

    public void updateUSER() {
        // Dependen del UID
        user.setHomeDirectory("/home/vmail/atol/" + user.getUid());
        user.setMail(user.getUid() + "@atol.com");
        user.setMailbox("atol/" + user.getUid() + "/");
    }

    public void crearUsuario() {
        if (user.isComplete()) {
            Attributes attributes = user.generateAttributes();
            try {
                singleLDAP = new SingleLDAP(SessionUtils.getUserUserDn(), SessionUtils.getUserPassword());
                singleLDAP.getContext().createSubcontext("uid=" + user.getUid() + ",ou=sistemas,ou=usuarios,dc=atol,dc=com", attributes);
                singleLDAP.getContext().close();
                addMessage(FacesMessage.SEVERITY_INFO, "Correo creado", "");
            } catch (NamingException ex) {
                if (ex instanceof NameAlreadyBoundException) {
                    //Logger.getLogger(AdmView.class.getName()).log(Level.SEVERE, null, ex);
                    addMessage(FacesMessage.SEVERITY_INFO, "Usuario ya existe", "");

                } else if (ex instanceof CommunicationException) {
                    Logger.getLogger(AdmView.class.getName()).log(Level.SEVERE, null, ex);
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error al comunicarse con el host", "");

                } else if (ex instanceof AuthenticationException) {
                    //Logger.getLogger(AdmView.class.getName()).log(Level.SEVERE, null, ex);
                    addMessage(FacesMessage.SEVERITY_ERROR, "Credenciales Invalidas", "");

                } else {
                    Logger.getLogger(AdmView.class.getName()).log(Level.SEVERE, null, ex);
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error no definido", "");

                }
            }
        }
    }

    public void cargarUsuarios() {
        String searchFilter = "(objectClass=CourierMailAccount)";
        //String[] reqAtt = {"mail", "mailbox"};
        String[] reqAtt = user.fieldsList();
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);

        try {
            singleLDAP = new SingleLDAP(SessionUtils.getUserUserDn(), SessionUtils.getUserPassword());
            NamingEnumeration users = singleLDAP.getContext().search("ou=sistemas,ou=usuarios,dc=atol,dc=com", searchFilter, controls);
            singleLDAP.getContext().close();

            SearchResult result = null;
            while (users.hasMore()) {
                result = (SearchResult) users.next();
                Attributes attr = result.getAttributes();
                UserLDAP u = new UserLDAP();
                u = u.BuildUserLDAP(attr);
                usuarios.add(u);
            }
        } catch (NamingException ex) {
            if (ex instanceof CommunicationException) {
                Logger.getLogger(AdmView.class.getName()).log(Level.SEVERE, null, ex);
                addMessage(FacesMessage.SEVERITY_ERROR, "Error al comunicarse con el host", "");

            } else if (ex instanceof AuthenticationException) {
                //Logger.getLogger(AdmView.class.getName()).log(Level.SEVERE, null, ex);
                addMessage(FacesMessage.SEVERITY_ERROR, "Credenciales Invalidas", "");

            } else {
                Logger.getLogger(AdmView.class.getName()).log(Level.SEVERE, null, ex);
                addMessage(FacesMessage.SEVERITY_ERROR, "Error no definido", "");

            }
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

}
