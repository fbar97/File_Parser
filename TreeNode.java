import java.util.ArrayList;
import java.util.List;

abstract class TreeNode {

	static int count = 0; // number of nodes that are created, used to generate their IDs
	private String id; // The first node to be created would have an id of 1
	private List<TreeNode> children = new ArrayList<TreeNode>(); 
	private TreeNode parent; 
	
	public TreeNode(List children) {
		/* set the id of the node as well as hook up all the children 
		* passed in to the current node as their parent */
		this.children = children;
		if (this.children != null) {
			for (int i = 0; i < this.children.size(); i++) {
				if (this.children.get(i) != null) {
					this.children.get(i).setParent(this);
				}
			}
		}
		count++;
		this.id = "" + count;
		if (count > 1){
			parent = this;
		}

	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		TreeNode.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public void addChild(TreeNode child) {
		
		if (this.children == null) {
			children = new ArrayList<TreeNode>();
		}
		children.add(child);
		this.setChildren(children);
		child.setParent(this);

	}
}