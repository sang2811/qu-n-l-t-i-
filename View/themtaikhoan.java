package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connection.DBController;

public class themtaikhoan extends JDialog {

	private DefaultTableModel dm ;
	private dangnhap dn;
	JLabel lbthemtk = new JLabel("Tài Khoản :");
	JTextField tfthemtk = new JTextField(20);

	JLabel lbthemmk = new JLabel("Mật Khẩu :");
	JTextField tfthemmk = new JTextField(20);

	JLabel lbhovaten = new JLabel("Họ và Tên :");
	JTextField tfhovaten = new JTextField(20);

	JLabel lbbanbe = new JLabel("Bạn Bè :");
	JTextField tfbanbe = new JTextField(20);

	JLabel lbthemgmail = new JLabel("Gmail tạo (or sdt) :");
	JTextField tfthemgmail = new JTextField(20);

	JLabel lbmkmail = new JLabel("Mật Khẩu Mail :");
	JTextField tfmkmail = new JTextField(20);

	JButton btadd = new JButton("Thêm");

	
	
	public themtaikhoan(dangnhap dn, DefaultTableModel dm) {
		this.dn = dn;
		this.dm = dm;
		setTitle("Nhập thông tin");
		setSize(300, 150);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(dn);

		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(3, 4,10,10));
		jp1.add(lbthemtk);
		jp1.add(tfthemtk);
		jp1.add(lbthemmk);
		jp1.add(tfthemmk);
		jp1.add(lbhovaten);
		jp1.add(tfhovaten);
		jp1.add(lbbanbe);
		jp1.add(tfbanbe);
		jp1.add(lbthemgmail);
		jp1.add(tfthemgmail);
		jp1.add(lbmkmail);
		jp1.add(tfmkmail);

		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		jp2.add(btadd);

		btadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Thêm dữ liệu mới vào bảng
				dm.addRow(new Object[] {false , tfthemtk.getText(),tfthemmk.getText(),tfhovaten.getText(),tfbanbe.getText(),tfthemgmail.getText(),tfmkmail.getText()});

				String sql = "INSERT INTO sang.tkfb VALUES (?,?,?,?,?,?)";
				try {
					Connection con = new DBController().getConnection();
					PreparedStatement stm = con.prepareStatement(sql);

					stm.setString(1, tfthemtk.getText());
					stm.setString(2, tfthemmk.getText());
					stm.setString(3, tfhovaten.getText());
					stm.setString(4, tfbanbe.getText());
					stm.setString(5, tfthemgmail.getText());
					stm.setString(6, tfmkmail.getText());
					stm.execute();
					JOptionPane.showMessageDialog(null, "Đăng ký thành công");

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				dispose();
			}

		
		});
        //set anh
		URL sang = hamView.class.getResource("hinhnen.jpg");
		Image img = Toolkit.getDefaultToolkit().createImage(sang);
		this.setIconImage(img);
		setLayout(new BorderLayout());
		add(jp1, BorderLayout.NORTH);
		add(jp2, BorderLayout.CENTER);
		setModal(true);

	}

	
	
}
