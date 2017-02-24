import static org.junit.Assert.*; 

import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.InputStreamReader; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Map; 

import org.junit.Test; 


public class UnitTests { 

    public static int passed = 0;     
     
    @Test 
    public void testTreeNode() { 
        TreeNode.count = 0; 
        Tester node = new Tester(null); 
        assertEquals(null,node.getChildren()); 
        assertEquals(null, node.getParent()); 
        assertEquals("1", node.getId()); 
         
        ArrayList<TreeNode> children = new ArrayList<TreeNode>(); 
        children.add(node); 
        Tester node2 = new Tester(children); 
        node.setParent(node2); 
        assertEquals(node, node2.getChildren().get(0)); 
        assertEquals(node2,node.getParent()); 
        assertEquals("2", node2.getId()); 
         
        Tester node3 = new Tester(null); 
        node2.addChild(node3); 
        assertEquals(node2, node3.getParent()); 
        assertEquals(node3, node2.getChildren().get(1)); 
        assertNotEquals(0,++passed);         
    } 
     
    @Test 
    public void testTagNode1(){ 
        TagNode html = new TagNode("html"); 
        assertEquals(0, html.getChildren().size()); 
        assertEquals("html", html.getTag()); 
         
        TagNode body = new TagNode("body"); 
        html.addChild(body); 
        assertEquals(html, body.getParent()); 
        assertEquals(body, html.getChildren().get(0)); 
        html.addAttribute("name", "value"); 
        assertEquals("value", html.getValue("name")); 
        assertNotEquals(0,++passed);         
    } 
     
    @Test 
    public void testTagNode2(){ 
        TreeNode.count = 0;             
        TagNode html = new TagNode("html"); 
         
        TagNode body = new TagNode("body"); 
        TextNode text1 = new TextNode("hello"); 
        TextNode text2 = new TextNode("world");         
        html.addChild(body); 
        html.addChild(text1); 
        html.addChild(text2); 
         
        html.addAttribute("alt", "slogan"); 
        assertEquals("helloworld", html.mineCloseText()); 
        assertNotEquals(0,++passed);         
    }     
     
    @Test 
    public void testTagNode3(){     
        TagNode html = new TagNode("html"); 
         
        TagNode body = new TagNode("body"); 
        //TextNode text1 = new TextNode("hello"); 
        //TextNode text2 = new TextNode("world");         
        html.addChild(body); 
        //html.addChild(text1); 
        //html.addChild(text2); 
         
        html.addAttribute("alt", "slogan"); 
        assertEquals("", html.mineCloseText()); 
        assertNotEquals(0,++passed);         
    }         
     
    @Test 
    public void testTagNode4(){ 
        TreeNode.count = 0;             
        TagNode html = new TagNode("html"); 
         
        TagNode body = new TagNode("body"); 
        TextNode text1 = new TextNode("hello"); 
        TagNode tag = new TagNode("p");         
        TextNode text3 = new TextNode("bye");         
        html.addChild(body); 
        html.addChild(text1); 
        html.addChild(tag); 
        html.addChild(text3);         
         
        html.addAttribute("alt", "slogan"); 
        assertEquals("hello bye", tag.mineCloseText()); 
        assertNotEquals(0,++passed);         
    }         
     
    @Test 
    public void testTagNode5(){ 
        TreeNode.count = 0;             
        TagNode html = new TagNode("html"); 
         
        TagNode body = new TagNode("body"); 
        TextNode text1 = new TextNode("hello"); 
        TagNode tag = new TagNode("p");         
        TextNode text3 = new TextNode("bye");         
        html.addChild(body); 
        html.addChild(text1); 
        html.addChild(tag); 
        html.addChild(text3);     
         
        tag.addChild(new TextNode("more")); 
         
        html.addAttribute("alt", "slogan"); 
        assertEquals("more hello bye", tag.mineCloseText()); 
        assertNotEquals(0,++passed);         
    }     
     
    @Test 
    public void testParser1(){ 
        ArrayList<String> data = new ArrayList<String>(); 
        data.add("<HTML>"); 
        data.add("<HEAD>");         
        data.add("<TITLE>"); 
        data.add("</TITLE>");         
        data.add("</HEAD>"); 
        data.add("<BODY>"); 
        data.add("this is my webpage");     
        data.add("<img src=\"./headshot.jpg\"/>");     
        data.add("this is a photo of me");         
        data.add("<TABLE>"); 
        data.add("<TR>");         
        data.add("a table"); 
        data.add("</TR>");         
        data.add("</TABLE>"); 
        data.add("</BODY>");     
        data.add("</HTML>");     
         
        FileParser parser = new FileParser(); 
        parser.createTree(data); 
         
        TagNode root = (TagNode) parser.getRoot(); 
        assertEquals("HTML", root.getTag()); 
        assertEquals("HEAD", ((TagNode)root.getChildren().get(0)).getTag()); 
        assertEquals("BODY", ((TagNode)root.getChildren().get(1)).getTag()); 
        assertEquals(2, root.getChildren().size());         
         
        assertEquals("TITLE", ((TagNode)root.getChildren().get(0).getChildren().get(0)).getTag()); 
        assertEquals(1, root.getChildren().get(0).getChildren().size());             
         
        assertEquals("this is my webpage", ((TextNode)root.getChildren().get(1).getChildren().get(0)).getText()); 
        assertEquals("img", ((TagNode)root.getChildren().get(1).getChildren().get(1)).getTag()); 
        assertEquals("this is a photo of me", ((TextNode)root.getChildren().get(1).getChildren().get(2)).getText()); 
        assertEquals("TABLE", ((TagNode)root.getChildren().get(1).getChildren().get(3)).getTag());     
        assertEquals(4, root.getChildren().get(1).getChildren().size());             
         
        assertEquals("TR", ((TagNode)root.getChildren().get(1).getChildren().get(3).getChildren().get(0)).getTag());     
        assertEquals("a table", ((TextNode)root.getChildren().get(1).getChildren().get(3).getChildren().get(0).getChildren().get(0)).getText());        
        assertNotEquals(0,++passed); 
    } 
     
     
    @Test 
    public void testParser2(){ 
        ArrayList<String> data = new ArrayList<String>(); 
        data.add("<HTML>"); 
        data.add("<BODY>"); 
        data.add("this is my webpage");     
        data.add("<img src=\"./headshot.jpg\"/>");     
        data.add("this is a photo of me");         
        data.add("<TABLE>"); 
        data.add("<TR>");         
        data.add("a table"); 
        data.add("</TR>");         
        data.add("</TABLE>"); 
        data.add("</BODY>"); 
        data.add("<HEAD>");         
        data.add("<TITLE>"); 
        data.add("</TITLE>");         
        data.add("</HEAD>");         
        data.add("</HTML>");     
         
        FileParser parser = new FileParser(); 
        parser.createTree(data); 
         
        TagNode root = (TagNode) parser.getRoot(); 
        assertEquals("HTML", root.getTag()); 
        assertEquals("HEAD", ((TagNode)root.getChildren().get(1)).getTag()); 
        assertEquals("BODY", ((TagNode)root.getChildren().get(0)).getTag()); 
        assertEquals(2, root.getChildren().size());         
         
        assertEquals("TITLE", ((TagNode)root.getChildren().get(1).getChildren().get(0)).getTag()); 
        assertEquals(1, root.getChildren().get(1).getChildren().size());             
         
        assertEquals("this is my webpage", ((TextNode)root.getChildren().get(0).getChildren().get(0)).getText()); 
        assertEquals("img", ((TagNode)root.getChildren().get(0).getChildren().get(1)).getTag()); 
        assertEquals("this is a photo of me", ((TextNode)root.getChildren().get(0).getChildren().get(2)).getText()); 
        assertEquals("TABLE", ((TagNode)root.getChildren().get(0).getChildren().get(3)).getTag());     
        assertEquals(4, root.getChildren().get(0).getChildren().size());             
         
        assertEquals("TR", ((TagNode)root.getChildren().get(0).getChildren().get(3).getChildren().get(0)).getTag());     
        assertEquals("a table", ((TextNode)root.getChildren().get(0).getChildren().get(3).getChildren().get(0).getChildren().get(0)).getText());        
        assertNotEquals(0,++passed); 
    }     
     
    @Test 
    public void testParser3(){ 
        ArrayList<String> data = new ArrayList<String>(); 
        data.add("<HTML>"); 
        data.add("<HEAD>");         
        data.add("<TITLE>"); 
        data.add("</TITLE>");         
        data.add("</HEAD>"); 
        data.add("<BODY>"); 
        data.add("<img src=\"./headshot.jpg\"/>");     
        data.add("this is a photo of me");     
        data.add("this is my webpage");             
        data.add("<TABLE>"); 
        data.add("<TR>");         
        data.add("a table"); 
        data.add("</TR>");         
        data.add("</TABLE>"); 
        data.add("this is my webpage2");             
        data.add("</BODY>");     
        data.add("</HTML>");     
         
        FileParser parser = new FileParser(); 
        parser.createTree(data); 
         
        TagNode root = (TagNode) parser.getRoot(); 
        assertEquals("HTML", root.getTag()); 
        assertEquals("HEAD", ((TagNode)root.getChildren().get(0)).getTag()); 
        assertEquals("BODY", ((TagNode)root.getChildren().get(1)).getTag()); 
        assertEquals(2, root.getChildren().size());         
         
        assertEquals("TITLE", ((TagNode)root.getChildren().get(0).getChildren().get(0)).getTag()); 
        assertEquals(1, root.getChildren().get(0).getChildren().size());             
         
        assertEquals("this is a photo of me", ((TextNode)root.getChildren().get(1).getChildren().get(1)).getText()); 
        assertEquals("img", ((TagNode)root.getChildren().get(1).getChildren().get(0)).getTag()); 
        assertEquals("this is my webpage", ((TextNode)root.getChildren().get(1).getChildren().get(2)).getText()); 
        assertEquals("TABLE", ((TagNode)root.getChildren().get(1).getChildren().get(3)).getTag());     
        assertEquals("this is my webpage2", ((TextNode)root.getChildren().get(1).getChildren().get(4)).getText());         
        assertEquals(5, root.getChildren().get(1).getChildren().size());             
         
        assertEquals("TR", ((TagNode)root.getChildren().get(1).getChildren().get(3).getChildren().get(0)).getTag());     
        assertEquals("a table", ((TextNode)root.getChildren().get(1).getChildren().get(3).getChildren().get(0).getChildren().get(0)).getText());        
        assertNotEquals(0,++passed); 
    }     
     
    @Test 
    public void testParser4(){ 
        ArrayList<String> data = new ArrayList<String>(); 
        data.add("<HTML>"); 
        data.add("<HEAD>");         
        data.add("<TITLE>"); 
        data.add("</TITLE>");         
        data.add("</HEAD>"); 
        data.add("<BODY>"); 
        data.add("this is my webpage");     
        data.add("<img src=\"./headshot.jpg\"/>");     
        data.add("this is a photo of me");         
        data.add("<TABLE>"); 
        data.add("<TR>");         
        data.add("a table"); 
        data.add("</TR>");         
        data.add("</TABLE>"); 
        data.add("</BODY>");     
        data.add("<TAG>"); 
        data.add("<TAG>");         
        data.add("</HTML>");     
         
        FileParser parser = new FileParser(); 
        parser.createTree(data); 
         
        TagNode root = (TagNode) parser.getRoot(); 
        assertEquals("HTML", root.getTag()); 
        assertEquals("HEAD", ((TagNode)root.getChildren().get(0)).getTag()); 
        assertEquals("BODY", ((TagNode)root.getChildren().get(1)).getTag()); 
        assertEquals("TAG", ((TagNode)root.getChildren().get(2)).getTag());         
        assertEquals(3, root.getChildren().size());         
         
        assertEquals("TITLE", ((TagNode)root.getChildren().get(0).getChildren().get(0)).getTag()); 
        assertEquals(1, root.getChildren().get(0).getChildren().size());             
         
        assertEquals("this is my webpage", ((TextNode)root.getChildren().get(1).getChildren().get(0)).getText()); 
        assertEquals("img", ((TagNode)root.getChildren().get(1).getChildren().get(1)).getTag()); 
        assertEquals("this is a photo of me", ((TextNode)root.getChildren().get(1).getChildren().get(2)).getText()); 
        assertEquals("TABLE", ((TagNode)root.getChildren().get(1).getChildren().get(3)).getTag());     
        assertEquals(4, root.getChildren().get(1).getChildren().size());             
         
        assertEquals("TR", ((TagNode)root.getChildren().get(1).getChildren().get(3).getChildren().get(0)).getTag());     
        assertEquals("a table", ((TextNode)root.getChildren().get(1).getChildren().get(3).getChildren().get(0).getChildren().get(0)).getText());        
        assertNotEquals(0,++passed); 
    } 
         
    @Test 
    public void testMine1(){ 
        ArrayList<String> data = new ArrayList<String>(); 
        data.add("<HTML>"); 
        data.add("<HEAD>");         
        data.add("<TITLE>"); 
        data.add("<img src=\"./photo.jpg\"/>");             
        data.add("</TITLE>");         
        data.add("</HEAD>"); 
        data.add("<BODY>"); 
        data.add("this is my webpage");     
        data.add("<img src=\"./headshot.jpg\"/>");     
        data.add("this is a photo of me");         
        data.add("<TABLE>"); 
        data.add("<TR>");         
        data.add("a table"); 
        data.add("<img src=\"./sky1.jpg\" alt=\"nighttime sky\"/>");             
        data.add("</TR>");         
        data.add("</TABLE>"); 
        data.add("</BODY>");     
        data.add("</HTML>");     
         
        FileParser parser = new FileParser(); 
        parser.createTree(data); 
         
        ArrayList<TagNode> images = new ArrayList<TagNode>(); 
        parser.mineImages(images, parser.getRoot()); 

        assertEquals("./photo.jpg",images.get(0).getValue("src")); 
        assertEquals("./headshot.jpg",images.get(1).getValue("src")); 
        assertEquals("./sky1.jpg",images.get(2).getValue("src"));         
        assertNotEquals(0,++passed);         
    }     
     
    @Test 
    public void testKeywords1(){ 
        ArrayList<String> data = new ArrayList<String>(); 
        data.add("<HTML>"); 
        data.add("<HEAD>");         
        data.add("<TITLE>"); 
        data.add("<img src=\"./photo.jpg\"/>");             
        data.add("</TITLE>");         
        data.add("</HEAD>"); 
        data.add("<BODY>"); 
        data.add("this is my webpage");     
        data.add("<img src=\"./headshot.jpg\"/>");     
        data.add("this is a photo of me");         
        data.add("<TABLE>"); 
        data.add("<TR>");         
        data.add("a table"); 
        data.add("<img src=\"./sky.jpg\" alt=\"nighttime\"/>");             
        data.add("</TR>");         
        data.add("</TABLE>"); 
        data.add("</BODY>");     
        data.add("</HTML>");     
         
        FileParser parser = new FileParser(); 
        parser.createTree(data); 

        assertEquals("",parser.getKeywordsForImage("photo.jpg")); 
        assertEquals("this is my webpage this is a photo of me",parser.getKeywordsForImage("headshot.jpg")); 
        assertEquals("a table nighttime",parser.getKeywordsForImage("sky.jpg"));         
        assertNotEquals(0,++passed); 
    }         
     
    private class Tester extends TreeNode{ 

        public Tester(List<TreeNode> children) { 
            super(children); 
            
        } 
         
    } 
  
         

} 

