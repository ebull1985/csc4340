import java.util.ArrayList;
import java.util.Stack;

/*
 * SQLNode represents any node that is not a WhereNode
 */
public class SQLNode {
	boolean distinct;
	ArrayList<Column> columns = null;
	ArrayList<String[]> relations = null;
	ArrayList<WhereNode> whereNodes = null;
	SQLNode parent = null;
	
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
	
	public void setWhereNodes(ArrayList<WhereNode> whereNodes) {
		this.whereNodes = whereNodes;
	}
	
	public ArrayList<Column> getColumns() {
		return this.columns;
	}
	
	public ArrayList<WhereNode> getWhereNodes() {
		return this.whereNodes;
	}
	
	public ArrayList<String[]> getRelations() {
		return this.relations;
	}
	
	public String[] getTable(int i) {
		return this.relations.get(i);
	}
	
	//Going to actually evaluate here.
	public Relation evaluate(String dbName) {
		Database db = new Database();
		db.initializeDatabase(dbName);
		ArrayList<String> usedTables = new ArrayList<String>();
		//Table names go onto a stack
		Stack<String> tableStack = new Stack<String>();
		for (String[] r : this.relations) {
			tableStack.push(r[0]);
		}
		
		//Pull table names from stack and create a relation from the db for each one
		//Set its prefix to its table name
		//multiply them together
		//Don't do this for duplicate tables
		String tn = tableStack.pop();
		Relation curRelation = db.getRelation(tn);
		curRelation.prefixColumnNames(tn);
		usedTables.add(tn);
		int count = 0;
		//System.out.println(count++);
		//curRelation.displayRelation();
		while (tableStack.size() > 0) {
			//System.out.println(count++);
			tn = tableStack.pop();
			if (!usedTables.contains(tn)) {
				//System.out.println(tn);
				Relation nextRelation = db.getRelation(tn);
				nextRelation.prefixColumnNames(tn);
				usedTables.add(tn);
				curRelation = curRelation.times(nextRelation);
				//curRelation.displayRelation();
			}
		}
		
		if (this.whereNodes != null) {
			for (WhereNode ewn : this.whereNodes) {
				try {
					Relation whereRelation = ewn.evaluate(curRelation, dbName);
					curRelation = whereRelation;
				}
				catch (NullPointerException e) {
					//This should never happen but if it does just means to stop processing them
				}
			}
		}
		
		//Project the columns from the select statement
		ArrayList<String> p = new ArrayList<String>();
		for (Column ec : columns) {
			String cname = String.format("%s.%s", ec.getPrefix(), ec.getName());
			p.add(cname);
		}

		if (curRelation.project(p) != null)
		curRelation = curRelation.project(p);

		
		//Algorithm
		//Multiply all the relations from this.relations together: curRelation
		//Get all WhereNodes
		//Intersection of curRelation and each WhereNode
		//Return result
		
		if (distinct) curRelation.removeDuplicates();
		return curRelation;
	}
	
}
