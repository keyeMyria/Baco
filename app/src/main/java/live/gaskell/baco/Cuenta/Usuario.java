package live.gaskell.baco.Cuenta;

public class Usuario {
    private String
            Nombre,
            Apellido,
            Correo,
            Sexo,
            Fecha_de_nacimiento,
            cedula_de_identidad,
            id;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String correo) {
        Nombre = nombre;
        Apellido = apellido;
        Correo = correo;
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

    public String getCedula_de_identidad() {
        return cedula_de_identidad;
    }

    public void setCedula_de_identidad(String cedula_de_identidad) {
        this.cedula_de_identidad = cedula_de_identidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
