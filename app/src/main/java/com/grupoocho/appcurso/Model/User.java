package com.grupoocho.appcurso.Model;

public class User {

    private String email;
    private String id;
    private String name;
    private String telefono;
    private String tipouser;

    public User() {
    }

    public User(String email, String id, String name, String telefono, String tipouser) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.telefono = telefono;
        this.tipouser = tipouser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipouser() {
        return tipouser;
    }

    public void setTipouser(String tipouser) {
        this.tipouser = tipouser;
    }
}
