package com.example.demo.repositorios;

import com.example.demo.entidades.Foto;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {

    @Query("SELECT a FROM Foto a WHERE a.alta = true")
    public List<Foto> buscarPorAlta();
}
