package ro.vdin.beanchecker.testbeans;

import java.util.Date;

import ro.vdin.beanchecker.OptionalSetter;

public class SimpleBean {
	private String author;
	private String body;
	private Date date;

	public void setAuthor(String author) {
		this.author = author;
	}

	@OptionalSetter
	public void setBody(String body) {
		this.body = body;
	}

	public void setDate(Date when) {
		this.date = when;
	}

	public String getBody() {
		return body;
	}

	public Date getDate() {
		return date;
	}

	public String getAuthor() {
		return author;
	}
}
