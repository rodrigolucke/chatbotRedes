/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.rules;

import java.io.Serializable;

/**
 * Objeto serializavel via socket entre cliente e servidor<br>
 * Precisa ser uma copia exata da existente no outro lado.
 * 
 * @author Lucke
 */
public class ChatBot implements Serializable {

	/**
	 * conteudo da mensagem
	 */
	private String texto;
	/**
	 * owner da mensagem
	 */
	private String Nome;
	/**
	 * acao da mensagem
	 */
	private Acao acao;

	/**
	 * busca o owner
	 * 
	 * @return Nome
	 */
	public String getNome() {
		return Nome;
	}

	/**
	 * configura o owner
	 * 
	 * @param Nome
	 */
	public void setNome(String Nome) {
		this.Nome = Nome;
	}

	/**
	 * busca o texto
	 * 
	 * @return texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * configura o texto
	 * 
	 * @param texto
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * busca a acao
	 * 
	 * @return acao
	 */
	public Acao getAcao() {
		return acao;
	}

	/**
	 * configura a acao
	 * 
	 * @param acao
	 */
	public void setAcao(Acao acao) {
		this.acao = acao;
	}

	/**
	 * lista de acoes possiveis de serem invocadas com a troca deste objeto
	 * 
	 * @author jLuckeer
	 *
	 */
	public enum Acao {
		CONECTAR, DESCONECTAR, ENVIAR
	}

}
