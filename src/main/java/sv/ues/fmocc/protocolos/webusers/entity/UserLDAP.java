/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.fmocc.protocolos.webusers.entity;

import java.io.Serializable;

/**
 *
 * @author Juan
 */
public class UserLDAP implements Serializable{
    
    private String nombres;
    private String apellidos;
    private String uid;
    private String password;

    public UserLDAP() {
    }

    public UserLDAP(String nombres, String apellidos, String uid, String password) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.uid = uid;
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    //Metodo para validar si todos los datos del pojo son correctos
    public boolean ok(){
        //SE VALIDA QUE NINGUN CAMPO ESTE NULO
        if(this == null || 
                this.nombres == null || this.apellidos != null ||
                this.uid != null || this.password != null){
            return false;
        }
        //SE VALIDA QUE NINGUN CAMPO ESTE VACIO
        if(this.nombres.isEmpty() || this.apellidos.isEmpty() ||
                this.uid.isEmpty() || this.password.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return super.toString(); //To change body of generated methods, choose Tools | Templates.
        return this.uid;
    }    
    
    
}
