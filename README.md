# bean-checker

## What is it?

Bean checker is a Java library which helps to check that all properties of a Java bean have been set (no matter if they were set to not-null or null values).

For instance, imagine this bean:

```java
public class SimpleBean {
	private Date date;
	private String author;
	private String body;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
messageBean.setDate(new Date());
```

You see above that the developer forgot to call the setBody() setter.

You can avoid situations like these by using this library, like this:


```java
BeanChecker<SimpleBean> beanChecker = BeanCheckerFactory.createBeanChecker(SimpleBean.class);
SimpleBean messageBean = beanChecker.getBeanProxy();

messageBean.setAuthor("vlad");
messageBean.setDate(new Date());

beanChecker.mandatorySettersCalled(); // this will throw an exception if not all setters were called
```

## Maven 

Maven repository:
```xml
	<repositories>
		<repository>
			<id>bean-checker</id>
			<url>https://github.com/vlad2/mvn-repo/raw/master/releases</url>
		</repository>
	</repositories>
```

Maven dependency:
```xml
		<dependency>
			<groupId>ro.vdin</groupId>
			<artifactId>bean-checker</artifactId>
			<version>1.2</version>
		</dependency>
```
