/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.model;

import br.unisc.rules.ChatBot;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Objeto do cliente, que gerencia a conexao com o servidor<br>
 * bem como o recebimento de respostas
 * 
 * @author Lucke
 */
public class Cliente {

	/**
	 * socket do cliente conectado ao servidor
	 */
	Socket cliente;
	/**
	 * stream de saida/escrita no socket
	 */
	private ObjectOutputStream output;

	/**
	 * estabelece uma conexao com o servidor, iniciando um socket
	 * 
	 * @param ip
	 *            do servidor
	 * @param porta
	 *            do servidor
	 * @return socket da conexao quando estabelecida
	 * @throws ConnectException
	 */
	public Socket Conectar(String ip, int porta) throws ConnectException {
		try {
			cliente = new Socket(ip, porta);
			output = new ObjectOutputStream(cliente.getOutputStream());
		} catch (ConnectException ce) {
			// erro de conexao, passa a excessao para cima, para ser tratada e
			// avisar o usuario
			throw ce;
		} catch (IOException ex) {
			Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
		}
		return cliente;
	}

	/**
	 * metodo responsaval por envio de dados do cliente ao servidor por meio do
	 * socket<br>
	 * 
	 * @param texto
	 * @throws IOException
	 */
	public void enviaMensagem(ChatBot texto) throws IOException {
		output.writeObject(texto);
	}

}
