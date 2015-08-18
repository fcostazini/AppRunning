package com.thinkup.ranning.dtos;

import java.io.Serializable;

/**
 * Created by Facundo on 17/08/2015.
 *
 */
public class CheckUsuarioPassDTO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -5474204225317532922L;
	String usuario;
    String password;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
