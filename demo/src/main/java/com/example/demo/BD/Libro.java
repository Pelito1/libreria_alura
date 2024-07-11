/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.BD;
import jakarta.persistence.*;import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author RicardoV
 */
@Entity
@Table(name="libro")
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     @Column(unique = true)
    private String titulo;
    private String idioma;
    private double numDescargas;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Autor autor;

    public Libro(DatosLibro libro) {
        this.titulo = libro.titulo();
        Optional<DatosAutor> autor = libro.autores().stream()
                .findFirst();
        if (autor.isPresent()) {
            this.autor = new Autor(autor.get());
        } else {
            System.out.println("No se ha podido encontrar el autor");
        }
        this.idioma = libro.idioma().get(0);
        this.numDescargas = libro.numDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public double getNumDescargas() {
        return numDescargas;
    }

    public void setNumDescargas(double numDescargas) {
        this.numDescargas = numDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
}
