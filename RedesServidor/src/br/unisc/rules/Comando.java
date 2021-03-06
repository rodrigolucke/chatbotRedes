package br.unisc.rules;

/**
 * Lista de comandos suportados pelo servidor, com suas definicoes
 * 
 * @author Lucke
 *
 */
public enum Comando {
	/**
	 * Lista os comandos existentes no servidor
	 */
	HELP {
		@Override
		public String[] getDefinition() {
			return new String[] { "/HELP" };
		}

		@Override
		public String[] getExplanation() {
			return new String[] { "Retorna uma lista dos comandos suportados" };
		}
	},
	/**
	 * Lista os autores do aplicativo
	 */
	AUTORES {
		@Override
		public String[] getDefinition() {
			return new String[] { "/AUTORES" };
		}

		@Override
		public String[] getExplanation() {
			return new String[] { "Retorna os nomes dos autores do projeto" };
		}
	},
	/**
	 * Mosta a data do servidor
	 */
	DATA {
		@Override
		public String[] getDefinition() {
			return new String[] { "/DATA" };
		}

		@Override
		public String[] getExplanation() {
			return new String[] { "Retorna a data do servidor" };
		}
	},
	/**
	 * Mostra a hora do servidor
	 */
	HORA {
		@Override
		public String[] getDefinition() {
			return new String[] { "/HORA" };
		}

		@Override
		public String[] getExplanation() {
			return new String[] { "Retorna a hora do servidor" };
		}
	},
	/**
	 * mostra a data e a hora do servidor
	 */
	DATAHORA {
		@Override
		public String[] getDefinition() {
			return new String[] { "/DATAHORA" };
		}

		@Override
		public String[] getExplanation() {
			return new String[] { "Retorna a data e a hora do servidor" };
		}
	},
	/**
	 * cria um emoji aleaotrio, conforme o enum de emojis
	 * 
	 * @see Emoji
	 */
	EMOJI {
		@Override
		public String[] getDefinition() {
			return new String[] { "/EMOJI" };
		}

		@Override
		public String[] getExplanation() {
			return new String[] { "Retorna um emoji randomico" };

		}
	},
        NEWS {
		@Override
		public String[] getDefinition() {
			return new String[] { "/NEWS" };
		}

		@Override
		public String[] getExplanation() {
			return new String[] { "Retorna uma noticia de capa do portal BBC News" };
		}
	};

	/**
	 * busca a definicao do comando, ou seja, como ele deve ser invocado
	 * 
	 * @return definition
	 */
	public String[] getDefinition() {
		return new String[] { "OPS" };
	}

	/**
	 * busca a descricao completa do comando, o que ele ira retornar e de que
	 * forma
	 * 
	 * @return explanation
	 */
	public String[] getExplanation() {
		return new String[] { "OPS" };
	}
}