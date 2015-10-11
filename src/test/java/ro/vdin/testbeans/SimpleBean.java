package ro.vdin.testbeans;

import java.util.Date;

import ro.vdin.OptionalSetter;

public class SimpleBean {
	private Date when;
	private String author;
	private String body;

	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBody() {
		return body;
	}

	@OptionalSetter
	public void setBody(String body) {
		this.body = body;
	}

}
