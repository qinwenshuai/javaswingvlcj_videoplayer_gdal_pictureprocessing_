package com.map.ui;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.map.utils.PictureShow;
 
public class TreeDemo {
	    private DefaultMutableTreeNode top, node1, node2 ,node3, node4, node5;
	    private DefaultTreeModel treeModel;
	    private JTree tree;
	    public  JTree test1(){
 
        // 创建没有父节点和子节点、但允许有子节点的树节点，并使用指定的用户对象对它进行初始化。
        // public DefaultMutableTreeNode(Object userObject)
        node1 = new DefaultMutableTreeNode("图像波段");
        node1.add(new DefaultMutableTreeNode(new User("波段1")));
        node1.add(new DefaultMutableTreeNode(new User("波段2")));
        node1.add(new DefaultMutableTreeNode(new User("波段3")));
        node1.add(new DefaultMutableTreeNode(new User("波段4")));

        /*node2 = new DefaultMutableTreeNode("1");
        node2.add(new DefaultMutableTreeNode(new User("2")));
        node2.add(new DefaultMutableTreeNode(new User("3")));
        node2.add(new DefaultMutableTreeNode(new User("4")));*/
 
        top = new DefaultMutableTreeNode("图像");
 
        //top.add(new DefaultMutableTreeNode(new User("5")));
        top.add(node1);
        //top.add(node2);
        tree = new JTree(top);
        // 添加选择事件
        tree.addTreeSelectionListener(new TreeSelectionListener() {
 
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
 
                if (node == null)
                    return;
 
                Object object = node.getUserObject();
                //node.getLevel() == 2
                if (node.isLeaf()) {
                    User user = (User) object;
                    //System.out.println("你选择了：" + user.toString());
                    if (object.toString().equals("波段1")) {
                    	JFrame frame = new JFrame("波段1");//构造一个新的JFrame，作为新窗口。
                    	frame.setBounds(200, 200, 600, 600);
                    	frame.setLocationRelativeTo(null);
                    	frame.setAlwaysOnTop(true);
                    	JLabel jl = new JLabel();// 注意类名别写错了。
                    	frame.getContentPane().add(jl);
                    	jl.setIcon(new PictureShow(new ImageIcon("images//A.jpg")));
                    	jl.setVerticalAlignment(JLabel.CENTER);
                    	jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
                    	frame.setVisible(true);
                    }
                    if (object.toString().equals("波段2")) {
                    	JFrame frame = new JFrame("波段2");//构造一个新的JFrame，作为新窗口。
                    	frame.setBounds(200, 200, 600, 600);
                    	frame.setLocationRelativeTo(null);
                    	frame.setAlwaysOnTop(true);
                    	JLabel jl = new JLabel();// 注意类名别写错了。
                    	frame.getContentPane().add(jl);
                    	jl.setIcon(new PictureShow(new ImageIcon("images//B.png")));
                    	jl.setVerticalAlignment(JLabel.CENTER);
                    	jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
                    	frame.setVisible(true);
                    }
                    if (object.toString().equals("波段3")) {
                    	JFrame frame = new JFrame("波段3");//构造一个新的JFrame，作为新窗口。
                    	frame.setBounds(200,200, 600, 600);
                    	frame.setLocationRelativeTo(null);
                    	frame.setAlwaysOnTop(true);
                    	JLabel jl = new JLabel();// 注意类名别写错了。
                    	frame.getContentPane().add(jl);
                    	jl.setIcon(new PictureShow(new ImageIcon("images//C.png")));
                    	jl.setVerticalAlignment(JLabel.CENTER);
                    	jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
                    	frame.setVisible(true);
                    }
                    if (object.toString().equals("波段4")) {
                    	JFrame frame = new JFrame("波段4");//构造一个新的JFrame，作为新窗口。
                    	frame.setBounds(200, 200, 600, 600);
                    	frame.setLocationRelativeTo(null);
                    	frame.setAlwaysOnTop(true);
                    	JLabel jl = new JLabel();
                    	frame.getContentPane().add(jl);
                    	jl.setIcon(new PictureShow(new ImageIcon("images//D.jpg")));
                    	jl.setVerticalAlignment(JLabel.CENTER);
                    	jl.setHorizontalAlignment(JLabel.CENTER);
                    	frame.setVisible(true);
                    }
                }
 
            }
        });       
		return tree;
    }
}
 
class User {
    private String name;
 
    public User(String n) {
        name = n;
    }
 
    // 重点在toString，节点的显示文本就是toString
    public String toString() {
        return name;
    }
}