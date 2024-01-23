package View;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import connection.DBController;

public class hamView extends JFrame {

	JLabel lbmoibandangnhap = new JLabel("Mời bạn đăng nhập!");
	JLabel lbtaikhoan = new JLabel("Tài khoản:");
	JTextField tftaikhoan = new JTextField(20);
	JLabel lbmatkhau = new JLabel("Mật Khẩu:");
	JPasswordField tfmatkhau = new JPasswordField(20);
	JButton btdangnhap = new JButton("Đăng Nhập");
	JButton btdangki = new JButton("Đăng Kí");
	

	JLabel sang = new JLabel("đăng nhập thành công");

	public hamView() {
		init();
	}

	void init() {
		setTitle("Đăng Nhập");
		setSize(330, 160);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		tfmatkhau.setEchoChar('•');
		
		btdangnhap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Connection con = new DBController().getConnection();
				String sql = "Select * from sang.dangnhap WHERE taikhoan = ? AND matkhau = ?";
				PreparedStatement stm;

				try {
					stm = con.prepareStatement(sql);
					stm.setString(1, tftaikhoan.getText());
					stm.setString(2, tfmatkhau.getText());
					ResultSet rs = stm.executeQuery();
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
						
						new dangnhap();
					} else
						JOptionPane.showMessageDialog(null, "Tài khoản và mật khẩu không đúng");
				} catch (SQLException e1) {
					// TODO: handle exception
				}
			}
		}
		)
		;

		btdangki.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mocuaso();

			}
		});

		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		jp1.add(lbmoibandangnhap);

		JPanel jp2 = new JPanel();
		jp2.setLayout(new GridLayout(2, 2, 5, 5));
		jp2.add(lbtaikhoan);
		jp2.add(tftaikhoan);
		jp2.add(lbmatkhau);
		jp2.add(tfmatkhau);

		URL sang = hamView.class.getResource("hinhnen.jpg");
		Image img = Toolkit.getDefaultToolkit().createImage(sang);
		this.setIconImage(img);
		
		
		
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		jp3.add(btdangnhap);
		jp3.add(btdangki);

		add(jp1, BorderLayout.NORTH);
		add(jp2, BorderLayout.CENTER);
		add(jp3, BorderLayout.SOUTH);

		setVisible(true);

	}
	public void mocuaso() {
		dangki dialog = new dangki(this);
		dialog.setVisible(true);
	}
	 public void updateTextField(String tf1, String tf2) {
		 tftaikhoan.setText(tf1);
		 tfmatkhau.setText(tf2);
		 
	 }
}
