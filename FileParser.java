import java.util.ArrayList;
import java.util.Map;

class FileParser {
	private TagNode root; // This will hold the root of the HTML file parsed.

	public void createTree(ArrayList<String> lines) {
		//This method will create a tree based on the HTML lines provided
		TagNode rootNode = new TagNode(lines.get(0).substring(1, lines.get(0).length() - 1));
		this.root = rootNode;
		for (int i = 1; i < lines.size(); i++) {
			// if the tag is self closing, looks for attributes
			if (lines.get(i).contains("<") && lines.get(i).contains("/>")) {
				if (lines.get(i).contains("=")) {
					String[] split = lines.get(i).split(" ");
					TagNode child = new TagNode(split[0].substring(1));
					String[] split2 = lines.get(i).split("=");
					int count = 1;
					for (int j = 1; j < split2.length; j++) {
						String name = split[count].substring(0, split[count].indexOf("="));
						String value = split2[j].substring(split2[j].indexOf("\"") + 1, split2[j].lastIndexOf("\""));
						child.addAttribute(name, value);
						count++;
					}
					rootNode.addChild(child);
				}
			} 
			//if the tag is an opening tag, then it adds a node to the tree 
			else if (!lines.get(i).contains("</") && lines.get(i).contains("<")) {
				TagNode node = new TagNode(lines.get(i).substring(1, lines.get(i).length() - 1));
				rootNode.addChild(node);
				rootNode = node;

			} 
			//if the tag is a closing tag, then change the reference to the parent node
			else if (lines.get(i).contains("</")) {
				rootNode = (TagNode) rootNode.getParent();
			} 
			else {
				TextNode child = new TextNode(lines.get(i));
				rootNode.addChild(child);
			}
		}
	}

	public TagNode getRoot() {
		return root;
	}

	public void setRoot(TagNode root) {
		this.root = root;
	}

	public void mineImages(ArrayList images, TreeNode node) {
		// Uses recursion to populate the incoming argument with all the nodes
		// that have IMG as their tag, starting with the node passed in. 
		if (node instanceof TagNode) {
			if (((TagNode) node).getTag().equals("img")) {
				images.add(node);
			}
		}
		if (node.getChildren() != null) {
			for (TreeNode child : node.getChildren()) {
				mineImages(images, child);
			}
		}
	}

	public String getKeywordsForImage(String filename) {
		/*Looks for a node with an IMG tag, and if its src name-value pair matches the 
		* incoming filename, attempts to mine nearby text. First it will call the 
		* mineCloseText method on the node, and then sees if there is an alt name-value 
		* pair and collect the value, appending to the result. Otherwise, 
		* if it hasn't found the image yet, it makes recursive calls to all of the 
		* current node's children.*/
		String keywords = "";

		ArrayList<TreeNode> image = new ArrayList<TreeNode>();
		this.getKeywordsForImageHelper(filename, this.getRoot(), image);
		for (int i = 0; i < image.size(); i++) {
			keywords += ((TagNode) image.get(i)).mineCloseText();
		}

		keywords = keywords.trim();
		return keywords;
	}

	public void getKeywordsForImageHelper(String filename, TreeNode node, ArrayList<TreeNode> images) {
		if (node instanceof TagNode) {
			if (((TagNode) node).getTag().equals("img")) {
				Map<String, String> attributes = ((TagNode) node).getAttributes();
				for (String key : attributes.keySet()) {
					if (((TagNode) node).getValue(key).substring(2).equals(filename)) {
						images.add(node);
					}
				}
			}
		}
		if (node.getChildren() != null) {
			for (int i = 0; i < node.getChildren().size(); i++) {
				getKeywordsForImageHelper(filename, node.getChildren().get(i), images);
			}
		}
	}

}