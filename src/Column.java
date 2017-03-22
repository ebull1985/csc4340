
public class Column {

	public String prefix;
	public String name;
	public SQLNode sn;
	public int ci;
	
	public Column(String name) {
		this.name = name;
	}
	
	public Column(String prefix, String name) {
		this.name = name;
		this.prefix = prefix;
	}
	
	public String toString() {
		String p;
		if(prefix == null) {
			p = "";
		} else {
			p = prefix;
		}
		return p + '.' + name;
	}
}