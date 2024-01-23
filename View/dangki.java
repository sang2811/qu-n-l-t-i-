package View;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import connection.DBController;

public class dangki extends JDialog {
    private hamView hv;
	JLabel sang = new JLabel("Mời Bạn Đăng Ký");
	
	JLabel lbtaikhoan = new JLabel("Tài Khoản");
	JTextField tfdktaikhoan = new JTextField(20);
	JLabel lbmatkhau = new JLabel("Mật Khẩu");
	JTextField tfdkmatkhau = new JTextField(20);
	JLabel lbnhaplaimatkhau = new JLabel("Nhập Lại Mật Khẩu");
	JTextField tfnhaplaimatkhau = new JTextField(20);
	JButton bttaotaikhoan = new JButton("Tạo Tài Khoản");
	
	
	
	
	public  dangki(hamView hv ) {
		this.hv = hv;
		
		setTitle("Đăng Kí");
		setSize(300, 180);
		setLocationRelativeTo(hv);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		URL ảnh = hamView.class.getResource("sang.jpg");
//		Image img = Toolkit.getDefaultToolkit().createImage(ảnh);
//		this.setIconImage(img);
 
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		jp1.add(sang);

		JPanel jp2 = new JPanel();
		jp2.setLayout(new GridLayout(3, 3));
		jp2.add(lbtaikhoan);
		jp2.add(tfdktaikhoan);
		jp2.add(lbmatkhau);
		jp2.add(tfdkmatkhau);
		jp2.add(lbnhaplaimatkhau);
		jp2.add(tfnhaplaimatkhau);
 
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		jp3.add(bttaotaikhoan);
        
        
        
		add(jp1, BorderLayout.NORTH);
		add(jp2, BorderLayout.CENTER);
		add(jp3, BorderLayout.SOUTH);
       
		bttaotaikhoan.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				
				
				//lệnh thêm vào sql
				//hv.updateTextField(tfdktaikhoan.getText(), tfdkmatkhau.getText());
				String sql = "INSERT INTO sang.dangnhap VALUES (?,?)";
				
				try {
					Connection con = new DBController().getConnection();
					PreparedStatement stm = con.prepareStatement(sql);
				
				
                    if(tfdkmatkhau.getText().equals(tfnhaplaimatkhau.getText())) {
                    	stm.setString(1, tfdktaikhoan.getText());  
    			      	stm.setString(2, tfdkmatkhau.getText());  
    					stm.execute();
                    	JOptionPane.showMessageDialog(null, "Đăng ký thành công");
                    }
                    else JOptionPane.showMessageDialog(null, "Nhập lại mật khẩu không chính xác");

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				dispose();
			}
			

		});
		//set ảnh
		URL sang = hamView.class.getResource("hinhnen.jpg");
		Image img = Toolkit.getDefaultToolkit().createImage(sang);
		this.setIconImage(img);
		setModal(true);
	}



	
	
}
