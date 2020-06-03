
package by.module6.library.XMLDAO;

public class XMLNode {
	String name;
	String value;
	
	public XMLNode(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public XMLNode(String name, Number value) {
		this.name = name;
		this.value = value + "";
	}
	
	public XMLNode(String name, Object value) {
		this.name = name;
		this.value = value.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
