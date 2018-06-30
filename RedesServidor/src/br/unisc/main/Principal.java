/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.main;

import br.unisc.model.Servidor;
import java.io.IOException;

/**
 * Classe inicial do servidor<br>
 * Serve apenas para iniciar em thread o recurso do servidor
 * @author Lucke
 */
public class Principal {
    public static void main(String[] args) throws IOException {
        new Servidor();
    }
}
