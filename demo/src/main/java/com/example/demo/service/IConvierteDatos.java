/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.service;

/**
 *
 * @author RicardoV
 */
public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}