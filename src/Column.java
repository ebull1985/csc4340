
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
	
	public void setIndex(int ci) {
		this.ci = ci;
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
	
	public int getIndex() {
		return ci;
	}
	
	public String evaluate() {
		String result = "";
		//Get the SQLNode
		//What if this is in a WhereNode and resolves to somewhere else?
		
		return result;
	}
}
