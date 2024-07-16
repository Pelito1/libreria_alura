/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo;

import com.example.demo.BD.Autor;
import com.example.demo.BD.Datos;
import com.example.demo.BD.DatosLibro;
import com.example.demo.BD.Libro;
import com.example.demo.Repository.AutorRepository;
import com.example.demo.Repository.LibroRepository;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import com.example.demo.service.ConsumoAPI;
import com.example.demo.service.ConvierteDatos;
import java.util.stream.Collectors;

/**
 *
 * @author RicardoV
 */
class Principal {
    
    private Scanner teclado = new Scanner(System.in);
    private static final String URL = "https://gutendex.com/books/";
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private ConvierteDatos conversor = new ConvierteDatos();

    
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    
     public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """ 
                       \n\t\tMENU BIBLIOTECA ALURA
                       \t\t---------------------
                    1 - Buscar libros por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado anio
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            teclado.nextLine();
            opcion = teclado.nextInt();
           

            switch (opcion) {
                case 1:
                    buscarLibroAPI();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresPorAño();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }
 
     private void buscarLibroAPI() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        teclado.nextLine();
        String nombreLibro = teclado.nextLine();
        String json = consumoApi.obtenerDatos(URL + "?search=" + nombreLibro.replace(" ", "+"));
         System.out.println(json);
        List<DatosLibro> libros = conversor.obtenerDatos(json, Datos.class).resultados();
        Optional<DatosLibro> libroOptional = libros.stream()
                .filter(l -> l.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();
        if (libroOptional.isPresent()) {
            var libro = new Libro(libroOptional.get());
            libroRepository.save(libro);
            imprimirLibroRespuestaConsumirAPI(libro);
        }else {
        System.out.println("El libro no ha podido ser encontrado");}
    }
   

    private void imprimirLibroRespuestaConsumirAPI(Libro libro) {
        System.out.printf("""
                        \n****************************************
                        \t\tLIBRO 
                        Titulo: %s
                        Autor: %s
                        Idioma: %s
                        Cantidad de descargas:  %.2f
                        **************************************** \n
                        """,
                libro.getTitulo(),
                libro.getAutor().getNombre(),
                libro.getIdioma(),
                libro.getNumDescargas());
    }
     
    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        libros.stream()
                .forEach(this::imprimirLibroRespuestaConsumirAPI);
    }
    
     private void leerAutor(Autor autor) {
        System.out.printf("""
                        \n****************************************
                        Autor: %s
                        Fecha de nacimiento: %s
                        Fecha de fallecimiento: %s
                        """,
                autor.getNombre(),
                autor.getFechaNacimiento(),
                autor.getFechafallecimiento());

        var libros = autor.getLibro().stream()
                .map(a -> a.getTitulo())
                .collect(Collectors.toList());
        System.out.println("Libros: " + libros + "\n****************************************");
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.stream()
                .forEach(this::leerAutor);
    }

    private void listarAutoresPorAño() {
        System.out.println("Ingresa el año vivo de autor(es) que desea buscar");
        Integer año = teclado.nextInt();
        List<Autor> autores = autorRepository.findByFechafallecimientoGreaterThan(año);
        autores.stream()
                .forEach(this::leerAutor);
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros
                es - español
                en - ingles
                fr - frances
                pt - portugues
                """);
        String idioma = teclado.next();
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        libros.stream()
                .forEach(this::imprimirLibroRespuestaConsumirAPI);
    }

    
}
