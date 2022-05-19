package com.example.demo.entidades;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class Foto {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;

    private String mime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

    @Temporal(TemporalType.DATE)
    private Date creado;

    @Temporal(TemporalType.DATE)
    private Date editado;

    private boolean alta;

}
