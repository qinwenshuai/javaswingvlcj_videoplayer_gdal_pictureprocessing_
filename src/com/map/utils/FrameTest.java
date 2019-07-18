package com.map.utils;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.map.test.EarthDemo;
	
public class FrameTest  {
	 
     JFrame jFrame = new JFrame("登录");
     Container c = jFrame.getContentPane();
     JLabel a1 = new JLabel("用户名");
     JTextField username = new JTextField();
     JLabel a2 = new JLabel("密   码");
     JPasswordField password = new JPasswordField();
     JButton okbtn = new JButton("确定");
     JButton cancelbtn = new JButton("取消");
    
    public FrameTest() {
    	//简化登录，正式打开！
//    	jFrame.setBounds(800, 400, 300, 220);
//        c.setLayout(new BorderLayout());//布局管理器
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         init();
//        jFrame.setVisible(true);
//        jFrame.setTitle("遥感图像超分辨率重建软件系统");
//        jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images//1.png"));
    }
    public void init() {
    	//正式去掉
    	new EarthDemo();
        /*标题部分--North*/
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("遥感图像超分辨率重建软件登录系统"));
        c.add(titlePanel, "North");
        
        /*输入部分--Center*/
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(null);
        a1.setBounds(50, 20, 50, 20);
        a2.setBounds(50, 60, 50, 20);
        fieldPanel.add(a1);
        fieldPanel.add(a2);
        username.setBounds(110, 20, 120, 20);
        password.setBounds(110, 60, 120, 20);
        fieldPanel.add(username);
        fieldPanel.add(password);
        c.add(fieldPanel, "Center");
        
        /*按钮部分--South*/
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(okbtn);
        buttonPanel.add(cancelbtn);
        c.add(buttonPanel, "South");
        okbtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					if (username.getText().contentEquals("admin") && password.getText().contentEquals("admin")) {	
						JOptionPane.showMessageDialog(null,"登录成功！" );
						jFrame.setVisible(false);
						new EarthDemo();
					}else {
						JOptionPane.showMessageDialog(null, "用户名或密码错误！");
					}
					
			}});
        cancelbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}});
        
    }

}