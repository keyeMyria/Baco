package live.gaskell.baco.Cuenta;

import android.widget.TextView;

public class Usuario {
    private String Usuario;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private String Telefono;
    private String Sexo;
    private String Fecha_de_nacimiento;
    private String Cedula;
    private String Id;
    private String Direccion;

    public Usuario() {
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getFecha_de_nacimiento() {
        return Fecha_de_nacimiento;
    }

    public void setFecha_de_nacimiento(String fecha_de_nacimiento) {
        Fecha_de_nacimiento = fecha_de_nacimiento;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        this.Cedula = cedula;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }


    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
