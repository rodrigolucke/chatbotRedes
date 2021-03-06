package br.unisc.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.unisc.model.rss.Feed;
import br.unisc.model.rss.FeedMessage;
import br.unisc.model.rss.RSSFeedParser;
import br.unisc.rules.ChatBot;
import br.unisc.rules.ChatBot.Acao;
import br.unisc.rules.Comando;
import br.unisc.rules.Emoji;

/**
 * Classe servidor<br>
 * Responsavel por receber conexoes, aguardar requisicoes, analisar e responder
 * 
 * @author Pazinato
 */
public class Servidor {

	/**
	 * Servidor de sockets<br>
	 * porta 1234
	 */
	private ServerSocket servidor;

	/**
	 * Instancia de socket com o cliente<br>
	 * instancia que ficara aguardando solicitacoes
	 */
	private Socket cliente;

	/**
	 * Contador de numero de cliente que requisitou conexao.<br>
	 * Utilizado para identificar o cliente e registrar no log de servidor suas
	 * requisicoes
	 */
	private int cont;

	/**
	 * Construtor padrao da classe servidor<br>
	 * Constroe o servidor, cria o serversocket e o registra na porta padrao
	 * 1234
	 */
	public Servidor() {
		this.cont = 1;
		try {
			// iniciando servidor
			servidor = new ServerSocket(1234);
			System.out.println("SERVIDOR INICIADO!\n\t AGUARDANDO CONEXOES...");
			while (true) {
				// aguardando conexao
				cliente = servidor.accept();
				// quando recebida nova conexao, abre uma thread de
				// monitoramente do novo cliente conectado
				new Thread(new ThreadServer(cliente)).start();
			}
		} catch (IOException ex) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Thread de servico do servidor<br>
	 * Responsavel por armazenar ou input e o output de cada conexao.<br>
	 * Recomendado o uso de nova instancia para cada conexao cliente
	 * estabelecida
	 * 
	 * @author jschuler
	 *
	 */
	public class ThreadServer implements Runnable {

		/**
		 * Stream de saida do do cliente
		 */
		private ObjectOutputStream output;
		/**
		 * Stream de entrada do cliente
		 */
		private ObjectInputStream input;

		/**
		 * Construtor do socket do servidor
		 * 
		 * @param cliente
		 *            o qual esta se conectando neste servidor
		 * @see Servidor
		 */
		public ThreadServer(Socket cliente) {
			try {
				this.output = new ObjectOutputStream(cliente.getOutputStream());
				this.input = new ObjectInputStream(cliente.getInputStream());
			} catch (IOException ex) {
				Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		/**
		 * Thread de analise<br>
		 * Responsavel por ficar escutando o cliente e analisar as
		 * requisicoes<br>
		 * alem de preparar a escrita das respostas a serem retornadas.
		 */
		@Override
		public void run() {
			ChatBot comando = null;
			try {
				// aguarda recebimento de um comando do cliente
				while ((comando = (ChatBot) input.readObject()) != null) {
					// quando o comando for recebido, e separado em tres tipos
					Acao acao = comando.getAcao();
					switch (acao) {
					// commando que solicita conexao
					case CONECTAR:
						System.out.println(comando.getTexto() + cliente.getLocalAddress().getHostAddress());
						comando.setNome("Cliente " + cont);
						comando.setTexto("Conectado como '" + comando.getNome() + "' ");
						cont++;
						enviar(comando, output);
						break;
					// comando que solicita a desconexao
					case DESCONECTAR:
						System.out.println(comando.getTexto());
						comando.setTexto("Adeus " + comando.getNome() + " !");
						enviar(comando, output);
						return;
					// commando que identifica como mensagem a ser processada
					case ENVIAR:
						System.out.println("Nova solicitacao do (" + comando.getNome() + "):" + comando.getTexto());
						String temp = "Servidor retornando " + comando.getTexto() + " ao " + comando.getNome();
						// requisita analise do comando (texto preenchido pelo
						// usuario)
						analisarRequisicaoCliente(comando);
						enviar(comando, output);
						System.out.println(temp);
						break;
					default:
						break;
					}
				}
			} catch (IOException | ClassNotFoundException ex) {
				Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		/**
		 * Metodo que recebe e analisa o objeto enviado pelo cliente
		 * conectado,<br>
		 * separa suas partes, processa e configura o retorno.
		 * 
		 * @param comando
		 *            recebido
		 */
		private void analisarRequisicaoCliente(ChatBot comando) {
			comando.setNome("Servidor: ");
			comando.setAcao(Acao.ENVIAR);
			String texto = comando.getTexto().trim();
			// verifica se o texto da mensagem possui conteudo
			if (texto != null && texto.length() > 0) {
				// reparte o conteudo utilizando e espaco como token
				String[] textoRepartido = texto.split(" ");
				if (textoRepartido.length > 0) {
					// se o comando possui parte, a primeira e o inicio do
					// comando
					String comandoSolicitado = textoRepartido[0];
					if (comandoSolicitado.startsWith("/")) {
						// se for enviado com barra, exclui o caractere, pois o
						// enum nao contem barras
						comandoSolicitado = comandoSolicitado.substring(1);
					}
					try {
						// tenta a identificacao do comando
						Comando comandoReconhecido = Comando.valueOf(comandoSolicitado);
						// como nao houve erro, o comando foi reconhecido
						StringBuffer sb = new StringBuffer();
						GregorianCalendar now = new GregorianCalendar();
						SimpleDateFormat sdf = null;
						// testa qual tipo de comando foi requisitado
						switch (comandoReconhecido) {
						case HELP:
							// monta saida processada do help, com todos os
							// comandos implementados, utilizando o enum.
							sb.append("Ola, estes sao os comandos que eu estou programado para aceitar:\n");
							Comando[] values = Comando.values();
							for (Comando acao2 : values) {
								String[] definition = acao2.getDefinition();
								String[] explanation = acao2.getExplanation();
								for (int i = 0; i < definition.length; i++) {
									sb.append(definition[i] + "\t\t" + explanation[i] + "\n");
								}
							}
							comando.setTexto(sb.toString());
							break;
						case AUTORES:
							// montagem simples e fixa dos autores
							sb.append("Fabricio Pazinato e Jonatas Schuler");
							comando.setTexto(sb.toString());
							break;
						case DATA:
							// montagem de um formatador de data para
							// configuracao correta do retorno
							sdf = new SimpleDateFormat("dd-MM-yyyy");
							sb.append(sdf.format(now.getTime()));
							comando.setTexto(sb.toString());
							break;
						case HORA:
							// montagem de um formatador de hora para
							// configuracao correta do retorno
							sdf = new SimpleDateFormat("HH:mm");
							sb.append(sdf.format(now.getTime()));
							comando.setTexto(sb.toString());
							break;
						case DATAHORA:
							// montagem de um formatador de data e hora para
							// configuracao correta do retorno
							sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
							sb.append(sdf.format(now.getTime()));
							comando.setTexto(sb.toString());
							break;
						case EMOJI:
							// busca a lista de emojis no enum
							Emoji[] emojis = Emoji.values();
							// cria um indice randomico valido
							int length = emojis.length;
							int nextInt = new Random().nextInt(length);
							// cria um emoji randomico
							Emoji emoji = emojis[nextInt];
							comando.setTexto(emoji.getDefinition());
							break;
						case NEWS:
							// invoca a requisicao para acesso externo e busca
							// de noticias randomicas
							FeedMessage randomNews = getRandomNews();
							comando.setTexto(randomNews.getTitle());
							break;
						
						default:
							break;
						}
					} catch (IllegalArgumentException e) {

					}
				}
			}
		}

	}

	

	/**
	 * Invoca uma requisicao ao parser de rss, que busca novos dados de forma
	 * online.<br>
	 * Apos buscado a lista, seleciona uma noticia de forma randomica para
	 * retorno.
	 * 
	 * @return feed de noticia randomico
	 */
	private FeedMessage getRandomNews() {
		RSSFeedParser parser = new RSSFeedParser("http://feeds.bbci.co.uk/news/rss.xml");
		Feed feed = parser.readFeed();
		List<FeedMessage> messages = feed.getMessages();
		int size = messages.size();
		int nextInt = new Random().nextInt(size);
		FeedMessage feedMessage = messages.get(nextInt);
		return feedMessage;
	}

	/**
	 * Metodo rensposavel por escrever/enviar o objeto de resposta no cliente
	 * conectado
	 * 
	 * @param comando
	 * @param output
	 * @throws IOException
	 */
	private void enviar(ChatBot comando, ObjectOutputStream output) throws IOException {
		output.writeObject(comando);
		//torna o comando nulo para que nao seja reutilizado
		comando = null;
	}

}
