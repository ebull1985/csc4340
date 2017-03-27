
public class Column {

	public String prefix = null;
	public String name;
	public SQLNode sn = null;
	public int ci;
	
	public Column(String name) {
		this.name = name;
	}
	
	public Column(String prefix, String name) {
		this.name = name;
		this.prefix = prefix;
	}
	
	public void setSN(SQLNode sn) {
		this.sn = sn;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public SQLNode getSN() {
		return sn;
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
