package live.gaskell.baco.Cuenta;

public class Usuario {
    private String Usuario;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private String Sexo;
    private String Fecha_de_nacimiento;
    private String Cedula_de_identidad;
    private String Id;
    private String Contraseña;

    public Usuario() {
    }

    public Usuario(String usuario, String correo, String contraseña) {
        Usuario = usuario;
        Correo = correo;
        this.Contraseña = contraseña;
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
        return Cedula_de_identidad;
    }

    public void setCedula_de_identidad(String cedula_de_identidad) {
        this.Cedula_de_identidad = cedula_de_identidad;
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

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        this.Contraseña = contraseña;
    }

}
