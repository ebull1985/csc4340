import java.util.ArrayList;

/*
 * SQLNode represents any node that is not a WhereNode
 */
public class SQLNode {
	boolean distinct;
	ArrayList<Column> columns;
	ArrayList<String[]> relations;
	ArrayList<WhereNode> whereNodes;
	SQLNode parent;
	
	public SQLNode() {
		
	}
	
	public SQLNode(boolean distinct,
				ArrayList<Column> columns, 
				ArrayList<String[]> relations) {
		this.distinct = distinct;
		this.columns = columns;
		this.relations = relations;
	}
	
	public void setParent(SQLNode parent) {
		this.parent = parent;
	}
	
	public String toString() {
		ArrayList<String> result = new ArrayList<String>();
		result.add("\nSQLNode");
		result.add("\ndistinct: " + distinct);
		ArrayList<String> colStrings = new ArrayList<String>();
		for(Column col : columns) colStrings.add(col.toString());
		result.add("\ncolumns:\n" + String.join("\n", colStrings));
		ArrayList<String> relStrings = new ArrayList<String>();
		for(String[] rel : relations) {
			if(rel[1] == null) relStrings.add(rel[0]);
			else relStrings.add(rel[0] + ":" + rel[1]);
		}
		result.add("\nrelations:\n" + String.join("\n", relStrings));
		result.add("===================");
		return String.join("\n", result);
	}
	
}
