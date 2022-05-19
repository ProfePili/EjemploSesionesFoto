package com.example.demo.servicios;

import com.example.demo.entidades.Foto;
import com.example.demo.excepciones.ExcepcionPropia;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.repositorios.FotoRepositorio;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio photoRepository;

    @Transactional
    public Foto multiPartToEntity(MultipartFile file) throws ExcepcionPropia {

        if (file != null) {
            Foto foto = new Foto();
            foto.setMime(file.getContentType());
            foto.setNombre(file.getName());
            try {
                foto.setContenido(file.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(FotoServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            return photoRepository.save(foto);

        } else {

            throw new ExcepcionPropia("No se puede cargar la foto");

        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Foto guardar(Foto foto) throws Exception {
        if (foto.getCreado() != null) {
            foto.setEditado(new Date());
        } else {
            foto.setAlta(true);
            foto.setCreado(new Date());
        }

        return photoRepository.save(foto);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Foto baja(String id) {

        Foto entity = photoRepository.getById(id);
        entity.setAlta(false);
        return photoRepository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Foto alta(String id) {

        Foto entity = photoRepository.getById(id);
        entity.setAlta(true);
        return photoRepository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void borrar(String id) {
        Foto entity = photoRepository.getById(id);
        photoRepository.delete(entity);

    }

    @Transactional(readOnly = true)
    public List<Foto> mostrarTodos() {
        return photoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Foto> mostrarActivos() {
        return photoRepository.buscarPorAlta();
    }

    @Transactional(readOnly = true)
    public Foto getOne(String id) {
        return photoRepository.getById(id);
    }

}
