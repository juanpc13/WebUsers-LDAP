/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.webusers;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.directory.ldap.client.api.LdapConnection;
import sv.ues.fmocc.protocolos.webusers.entity.UserLDAP;

/**
 *
 * @author jcpleitez
 */
@Named
@ViewScoped
public class AdmView implements Serializable{
    
    private String DOMAIN;
    private String DIR_ROOT = "ou=usuarios,dc=DOMAIN,dc=occ,dc=ues,dc=edu,dc=sv";
    
    private UserLDAP user;
    private LdapConnection masterConnection;
    
    @PostConstruct
    public void init() {
        user = new UserLDAP();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        DOMAIN = req.getServerName();
        //SE TIENE QUE HACER LA DIVISION DEL COM CON EL DOMINIO
        //String[] parts = DOMAIN.split(".");
        //DOMAIN = parts[0];
    }

    public UserLDAP getUser() {
        return user;
    }

    public void setUser(UserLDAP user) {
        this.user = user;
    }
    
    public void saveUser(){
        if(this.user != null){
            //Se debe guardar en LDAP el user
            System.out.println(user.getUid());
            System.out.println(DOMAIN);
        }else{
            System.out.println("El User esta null");
        }
    }
    
    public void crearLdap() {
        if (user.ok()) {
            
//            makeMasterConnection(s);
//            if (masterConnection.isConnected()) {
////            encriptar contraseña
//                StringBuilder builder = new StringBuilder();
//                builder.append("uid=").append(user.getUid()).append("," + DIR_ROOT);
//                String dnInsertar = builder.toString();
//
//                try {
//                    masterConnection.add(
//                            new DefaultEntry(
//                                    dnInsertar, // The Dn
//                                    "objectClass: inetOrgPerson",
//                                    "objectClass: organizationalPerson",
//                                    "objectClass: person",
//                                    "objectClass: top",
//                                    "cn", user.getCn(),
//                                    "sn", user.getSn(),
//                                    "uid", user.getUid()
//                            ));
//                    usuariosList = getLdapUsers();
//                    user = new Usuarioldap();
//                    PrimeFaces current = PrimeFaces.current();
//                    current.executeScript("PF('ouAgregar').hide();");
//                    addMessage("Se creó el nuevo usuario", false);
//                } catch (LdapException ex) {
//                    Logger.getLogger(FormView.class.getName()).log(Level.SEVERE, null, ex);
//                    if (ex.getClass() == LdapEntryAlreadyExistsException.class) {
//                        addMessage("El usuario ya existe dentro de la base de datos", true);
//                    } else {
//                        addMessage("Ocurrió un error con el servidor al tratar de crear el usuario", true);
//                    }
//                } finally {
//                    try {
//                        if (masterConnection != null) {
//                            masterConnection.unBind();
//                            masterConnection.close();
//                        }
//                    } catch (LdapException | IOException ex) {
//                        Logger.getLogger(FormView.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//            } else {
//                addMessage("En ese momento el servidor no está disponible para crear usuarios", true);
//            }
        }
    }
    
}
