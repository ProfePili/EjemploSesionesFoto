package com.example.demo.entidades;

import com.example.demo.enumeraciones.Rol;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

// misma entidad que siempre , algunas anotaciones nuevitas
@Entity
public class Usuario {

    // ATRIBUTOS
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id; // id autogenerado de tipo string

    @Column(unique = true) // es opcional, indica que los valores de esta columna son unicos.
    private String email; // NO SE PUEDE REPETIR EL VALOR

    @Column(nullable = false) // no se permiten valores nulos
    private String nombre;

    @Column(name = "APELLIDOS")
    private String apellido;

    @Temporal(TemporalType.DATE) // si o si al manejar fechas debemos especificar el tipo
    private Date fechaNacimiento;

    private Boolean estaActivo; // por buenas prácticas, no borramos registros, sino que le damos de baja

    @Transient // no va a persistir en la DB
    private Integer edad;

    @OneToOne
    private Direccion direccion; // tiene relación uno a uno con Direccion

    @Enumerated(EnumType.STRING)
    private Rol rol; // ahora tienen un rol    

    private String clave; // necesitamos una clave

    @OneToOne
    private Foto foto;

    // CONSTRUCTORES    
    public Usuario() {
    }

    public Usuario(String email, String nombre, String apellido, Date fechaNacimiento, Boolean estaActivo, Direccion direccion, Rol rol, String clave, Foto foto) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estaActivo = estaActivo;
        this.direccion = direccion;
        this.rol = rol;
        this.clave = clave;
        this.foto = foto;
    }

    // GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getEdad() { // una lógica adicional para este dato que no persiste
        Date fechaActual = new Date(); // cuando creamos un new Date(), toma por default el valor de la fecha actual
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd"); // creamos un formatter para dar formato y recibir la fecha
        int fecha1 = Integer.parseInt(formatter.format(this.fechaNacimiento)); // lo parseamos a entero 
        int fecha2 = Integer.parseInt(formatter.format(fechaActual));
        int edad = (fecha2 - fecha1) / 10000; // realizamos la resta y lo dividimos.
        return edad; // retornamos la edad
    }

    public void setEdad() {
        this.edad = getEdad();
    }

    public Boolean getEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(Boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    // MÉTODO TOSTRING
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", email=" + email + ", nombre=" + 
                nombre + ", apellido=" + apellido + ", fechaNacimiento=" + 
                fechaNacimiento + ", estaActivo=" + estaActivo + ", edad=" + 
                edad + ", direccion=" + direccion + ", rol=" + rol + ", clave=" + 
                clave + ", foto=" + foto + '}';
    }

}
