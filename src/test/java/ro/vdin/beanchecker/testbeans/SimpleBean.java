package ro.vdin.beanchecker.testbeans;

import java.util.Date;

import ro.vdin.beanchecker.OptionalSetter;

public class SimpleBean {
	private String author;
	private String body;
	private Date when;

	public void setAuthor(String author) {
		this.author = author;
	}

	@OptionalSetter
	public void setBody(String body) {
		this.body = body;
	}

	public void setWhen(Date when) {
		this.when = when;
	}

	public String getBody() {
		return body;
	}

	public Date getWhen() {
		return when;
	}

	public String getAuthor() {
		return author;
	}
}
