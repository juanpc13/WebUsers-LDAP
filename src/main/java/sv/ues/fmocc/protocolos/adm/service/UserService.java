/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.adm.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import sv.ues.fmocc.protocolos.adm.entity.UserLDAP;

/**
 *
 * @author jcpleitez
 */
public class UserService {
    
    private List<UserLDAP> usuarios;

    @PostConstruct
    public void init() {
        usuarios = new ArrayList<>();
        usuarios.add(new UserLDAP("Juan", "Pleitez", "juan.pleitez", "abc123"));
        usuarios.add(new UserLDAP("Cristian", "Ayala", "cristian.ayala", "abc123"));
        usuarios.add(new UserLDAP("Andrea", "Cuellar", "andrea.cuellar", "abc123"));
        usuarios.add(new UserLDAP("Roberto", "Menendez", "roberto.menendez", "abc123"));
    }

    public List<UserLDAP> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UserLDAP> usuarios) {
        this.usuarios = usuarios;
    }
    
}
