package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class hienthongtin extends JDialog {
  private dangnhap dn;
  JLabel JLabel_tk = new JLabel("Tài khoản :");
  JLabel JLabel_tttk = new JLabel();
  JLabel JLabel_mk = new JLabel("Mật Khẩu :");
  JLabel JLabel_ttmk = new JLabel();
  JLabel JLabel_ten = new JLabel("Họ và Tên :");
  JLabel JLabel_ttten = new JLabel();
  JLabel JLabel_bb = new JLabel("Số lượng bạn bè :");
  JLabel JLabel_ttbb = new JLabel();
  JLabel JLabel_gmail = new JLabel("Tài Khoản gmail");
  JLabel JLabel_ttgmail = new JLabel();
  JLabel JLabel_mkmail = new JLabel("Mật Khẩu gmail :");
  JLabel JLabel_ttmkmail = new JLabel();
   
  JButton jbutton_ok = new JButton("OK");
public hienthongtin(dangnhap dn) {
	this.dn = dn;
	
	setTitle("Thông Tin Bạn Cần Tìm");
	setSize(400, 250);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setLocationRelativeTo(dn);
	
	JPanel jp1 = new JPanel();
	
	jp1.setLayout(new GridLayout(6, 2,10,10));;
	
	
	jp1.add(JLabel_tk);
	jp1.add(JLabel_tttk);
	jp1.add(JLabel_mk);
	jp1.add(JLabel_ttmk);
	jp1.add(JLabel_ten);
	jp1.add(JLabel_ttten);
	jp1.add(JLabel_bb);
	jp1.add(JLabel_ttbb);
	jp1.add(JLabel_gmail);
	jp1.add(JLabel_ttgmail);
	jp1.add(JLabel_mkmail);
	jp1.add(JLabel_ttmkmail);
	
	JPanel jp2 = new JPanel();
	jp2.setLayout(new FlowLayout());
	jp2.add(jbutton_ok);
	
	jbutton_ok.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dispose();
			
		}
	});
	add(jp1, BorderLayout.NORTH);
	add(jp2, BorderLayout.SOUTH);
	
	
	setModal(true);
}
}
