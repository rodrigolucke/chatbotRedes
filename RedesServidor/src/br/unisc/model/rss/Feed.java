package br.unisc.model.rss;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores an RSS feed
 */
public class Feed {

	/**
	 * titulo da noticia
	 */
	final String title;
	/**
	 * link da noticia
	 */
	final String link;
	/**
	 * descricao da noticia
	 */
	final String description;
	/**
	 * idioma da noticia
	 */
	final String language;
	/**
	 * direitos autorais da noticia
	 */
	final String copyright;
	/**
	 * data de publicacao da noticia
	 */
	final String pubDate;

	/**
	 * lista de topicos/noticias lidos a partir do parser
	 */
	final List<FeedMessage> entries = new ArrayList<FeedMessage>();

	/**
	 * Construtor com parametros de feed
	 * 
	 * @param title
	 * @param link
	 * @param description
	 * @param language
	 * @param copyright
	 * @param pubDate
	 */
	public Feed(String title, String link, String description, String language, String copyright, String pubDate) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
		this.pubDate = pubDate;
	}

	/**
	 * Retorna os topicos/noticias publicadas e parseadas neste feed
	 * 
	 * @return entries
	 */
	public List<FeedMessage> getMessages() {
		return entries;
	}

	/**
	 * retorna o titulo do feed
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * retorna o link do feed
	 * 
	 * @return link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * retorna a descricao do feed
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * retorna o idioma do feed
	 * 
	 * @return language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * retorna os direitos autorais do feed
	 * 
	 * @return copyright
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * retorna a data de publicacao do feed
	 * 
	 * @return pubDate
	 */
	public String getPubDate() {
		return pubDate;
	}

	@Override
	public String toString() {
		return "Feed [copyright=" + copyright + ", description=" + description + ", language=" + language + ", link="
				+ link + ", pubDate=" + pubDate + ", title=" + title + "]";
	}

}