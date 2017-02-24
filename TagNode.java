import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagNode extends TreeNode {
	
	private String tag; 
	private Map<String,String> attributes = new HashMap<String, String>(); // name-value pairs

	public TagNode(String tag) {
		super(new ArrayList<TreeNode>());
		this.tag = tag;
	}

	public void addAttribute(String name, String value) {
		attributes.put(name, value);
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public String getValue(String key){
		return this.attributes.get(key);
	}

	public String mineCloseText() {
		String text = "";
		int count = 0;
		for (int i = 0; this.getChildren() != null && i < this.getChildren().size(); i++){
			TreeNode child = this.getChildren().get(i);
			if (child instanceof TextNode){
				text += ((TextNode) child).getText();
				count ++;
			}
			else {
				if (count > 0){
					text += " ";
				}
			}
		}
		for (int j = 0; this.getParent() != null && this.getParent().getChildren() != null && j < this.getParent().getChildren().size(); j++) {
			TreeNode node = this.getParent().getChildren().get(j);
			if (node instanceof TextNode){
				text += ((TextNode) node).getText();
				count ++;
			}
		
			else {
				if (count > 0){
					text += " ";
				}
			}
		}
		if (this.getAttributes().entrySet().size() > 1){
			for (String key: this.getAttributes().keySet()){
				if (!key.equals("src")){
					text += this.getAttributes().get(key);
				}
			}
		}
		return text;
	}
}