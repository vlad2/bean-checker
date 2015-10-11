# bean-checker

Bean checker is a library to check that all properties of a Java bean have been set (no matter if they were set to not-null or null values).

For instance, imagine this bean:

```java
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

	public void setBody(String body) {
		this.body = body;
	}

}
```


And the calling code:


```java
SimpleBean messageBean = new SimpleBean();

messageBean.setAuthor("vlad");
messageBean.setWhen(new Date());
```

You see above that the developer forgot to call the setBody() setter.

You can avoid situations like these by using this library, like this:


```java
BeanChecker<SimpleBean> beanChecker = new BeanCheckerImpl<>(SimpleBean.class);
SimpleBean messageBean = beanChecker.getBeanProxy();

messageBean.setAuthor("vlad");
messageBean.setWhen(new Date());

beanChecker.allSettersCalled(); // this will throw an exception if not all setters were called
```
