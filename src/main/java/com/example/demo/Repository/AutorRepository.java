/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.Repository;

import com.example.demo.BD.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author RicardoV
 */
public interface AutorRepository extends JpaRepository<Autor, Long>{
    List<Autor> findByFechafallecimientoGreaterThan(Integer año);

    List<Autor> findByNombre(String nombre);
}
