package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import connection.DBController;

public class dangnhap extends JFrame implements ActionListener {
	Statement stmt;
	 hamView v;
	
	 
	public dangnhap(hamView v) {
		this.v = v;
	}
	

	// Các thành phần giao diện

	JButton btthemtaikhoan = new JButton("Thêm Tài Khoản");

	JTextField tfnhaptt = new JTextField(20);
	JButton bttimkiem = new JButton();
	
	JMenuBar jmnb = new JMenuBar();
	JMenu jmenu_trangchu = new JMenu("Trang chủ");
	JMenuItem JMenuItem_dangxuat = new JMenuItem("Đăng Xuất");
	JLabel JLabel_1 = new JLabel("                                                                                                                                                                                  ");
	
	String[] options = { "Tài Khoản", "Mật Khẩu", "Gmail", "Mật Khẩu Mail" };
	JComboBox<String> cbb = new JComboBox<>(options);
	private int currentIndex = -1;
	private DefaultTableModel dm;
	private JTable tbl;
	
	JLabel time = new JLabel();
	

	public void setDm(DefaultTableModel dm) {
		this.dm = dm;
	}

	public dangnhap() {
		// Thiết lập cửa sổ
		setTitle("Quản lí tài khoản cá nhân");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1200, 600);
		setLocationRelativeTo(null);

		dm = new DefaultTableModel() {
			public Class<?> getColumnClass(int column) {
				if (column == 0) {
					return Boolean.class; // Thiết lập kiểu dữ liệu của cột ô tích chọn là boolean
				}
				return super.getColumnClass(column);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0; // Cho phép chỉnh sửa cột ô tích chọn
			}

		};
		tbl = new JTable(dm);

		// Panel chứa các component

		JPanel jp1 = new JPanel();
		jp1.setBackground(Color.LIGHT_GRAY);
		
		jp1.setLayout(new FlowLayout(0,0,10));
		jp1.add(cbb);
		jp1.add(tfnhaptt);
		jp1.add(bttimkiem);
		jp1.add(btthemtaikhoan);
		jp1.add(JLabel_1);
		
		jp1.add(time);
		
		
		
		
		
		
		jmenu_trangchu.add(JMenuItem_dangxuat);
		
	    jmnb.add(jmenu_trangchu);
		
		
		
		// exit
	   JMenuItem_dangxuat.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setVisible(false);
		}
	});
			
			
			
		//update time
			
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		ActionListener updatetime = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Date date = new Date();
					time.setText(form.format(date));
					
				}
		};
		Timer t = new Timer(1000, updatetime);
		t.start();  
			
		
		// Tạo bảng
		dm.addColumn("Chọn");
		dm.addColumn("Tài Khoản");
		dm.addColumn("Mật Khẩu");
		dm.addColumn("Họ và Tên");
		dm.addColumn("Bạn Bè");
		dm.addColumn("Gmail Khôi Phục");
		dm.addColumn("Mật Khẩu Mail");

		// thanh cuộn
		JScrollPane sc = new JScrollPane(tbl);

		btthemtaikhoan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				cuasothemtaikhoan();
			}
		});

		// đưa dữ liệu vào bảng khi bắt đầu
		themdulieuvaobang();
		// xử lí buttontimkiem
		bttimkiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String thongtin = (String) tfnhaptt.getText();
				if (thongtin != null) {
					String luachoncbb = (String) cbb.getSelectedItem();
					if ("Tài Khoản".equals(luachoncbb)) {
						// Xử lý tìm kiếm theo Tài khoản
						currentIndex = timKiemVaHienThi(thongtin, currentIndex + 1, 1);	
					} else if ("Mật Khẩu".equals(luachoncbb)) {
						currentIndex = timKiemVaHienThi(thongtin, currentIndex + 1, 2);
					} else if ("Gmail".equals(luachoncbb)) {
						currentIndex = timKiemVaHienThi(thongtin, currentIndex + 1, 5);
					} else if ("Mật Khẩu Mail".equals(luachoncbb)) {
						currentIndex = timKiemVaHienThi(thongtin, currentIndex + 1, 6);
					}

				}
			}
		});

		// khi ấn chuột phải vào chuột
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = tbl.rowAtPoint(e.getPoint());
				int c = tbl.columnAtPoint(e.getPoint());

				if (SwingUtilities.isRightMouseButton(e) && r != -1 && c != -1) {

					JPopupMenu menu = new JPopupMenu();
					JMenuItem xoataikhoan = new JMenuItem("xóa tài khoản");
					JMenu capnhat = new JMenu("Cập Nhập");
					JMenuItem JMenuItem_matkhau = new JMenuItem("Mật Khẩu");
					JMenuItem JMenuItem_hoten = new JMenuItem("Họ Tên");
					JMenuItem JMenuItem_banbe = new JMenuItem("Bạn Bè");
					JMenuItem JMenuItem_gmail = new JMenuItem("Gmail");
					JMenuItem JMenuItem_mkgmail = new JMenuItem("Mật Khẩu Gmail");

					capnhat.add(JMenuItem_matkhau);
					capnhat.add(JMenuItem_hoten);
					capnhat.add(JMenuItem_banbe);
					capnhat.add(JMenuItem_gmail);
					capnhat.add(JMenuItem_mkgmail);
					

					// xử lí các jmenuitem
					// cập nhập mật khẩu
					JMenuItem_matkhau.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							capNhatThongTin("Nhập Mật Khẩu Mới: ", "MatKhau");
							dm.fireTableDataChanged();
						}
					});

					// cập nhật họ tên mới
					JMenuItem_hoten.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							capNhatThongTin("Nhập Họ Tên Mới: ", "Hoten");
							dm.fireTableDataChanged();
						}
					});
					// cập nhật bạn bè
					JMenuItem_banbe.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							capNhatThongTin("Nhập Số Lượng Bạn Bè: ", "Hoten");
							dm.fireTableDataChanged();
						}
					});
					// cập nhập gmail
					JMenuItem_gmail.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							capNhatThongTin("Nhập Gmail Mới: ", "Gmail");
							dm.fireTableDataChanged();
						}
					});
					// cập nhập mk gmail
					JMenuItem_mkgmail.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							capNhatThongTin("Nhập Mật khẩu mail Mới: ", "MatKhauMail");
							dm.fireTableDataChanged();
						}
					});

					xoataikhoan.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							xoataikhoan();

						}
					});
					menu.add(xoataikhoan);
					menu.add(capnhat);
					menu.show(e.getComponent(), e.getX(), e.getY());

				}
			}
		});

		// set ảnh theo mặc định
		// URL sang = dangnhap.class.getResource("hinhnen.jpg");
		// Image img = Toolkit.getDefaultToolkit().createImage(sang);

		// set ảnh theo kích thước tùy chỉnh
		// ImageIcon icon = new ImageIcon(getClass().getResource("timkiem.png"));
		// Image img = icon.getImage();
		// Image newImg = img.getScaledInstance(20 , 20, Image.SCALE_SMOOTH);
		// icon = new ImageIcon(newImg);
		bttimkiem.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("timkiem.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

		// bttimkiem.setIcon( new
		// ImageIcon(Toolkit.getDefaultToolkit().createImage(dangnhap.class.getResource("timkiem.png")))
		// );
		// bttimkiem.setSize(10, 10);

		// Thiết lập layout và đưa components vào content pane
		setLayout(new BorderLayout());
		setJMenuBar(jmnb);
		add(jp1, BorderLayout.NORTH);
		add(sc, BorderLayout.CENTER);
        
		setVisible(true);

	}

	private void cuasothemtaikhoan() {
		themtaikhoan dialog = new themtaikhoan(this, dm);
		dialog.setVisible(true);
	}
	private void hienthongtintimkiem(int i) {
    	hienthongtin tt = new hienthongtin(this);
    	tt.JLabel_tttk.setText((String) tbl.getValueAt(i, 1));
    	tt.JLabel_ttmk.setText((String) tbl.getValueAt(i, 2));
    	tt.JLabel_ttten.setText((String) tbl.getValueAt(i, 3));
    	tt.JLabel_ttbb.setText((String) tbl.getValueAt(i, 4));
    	tt.JLabel_ttgmail.setText((String) tbl.getValueAt(i, 5));
    	tt.JLabel_ttmkmail.setText((String) tbl.getValueAt(i, 6));
    	tt.setVisible(true);
    }
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	private void themdulieuvaobang() {
		DBController db = new DBController();
		ResultSet rs = db.tkfb();
		try {
			while (rs.next()) {

				dm.addRow(new Object[] { false, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), });

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void capNhatThongTin(String Nhaptt, String tencot) {
		String thongtin = JOptionPane.showInputDialog(null, Nhaptt);

		if (thongtin != null && !thongtin.isEmpty()) {
			for (int i = tbl.getRowCount() - 1; i >= 0; i--) {
				String taiKhoan = (String) tbl.getValueAt(i, 1);

				if ((boolean) tbl.getValueAt(i, 0)) {
					try {
						String sql = "UPDATE sang.tkfb SET " + tencot + " = ? WHERE TaiKhoan = ? ";
						Connection con = new DBController().getConnection();
						PreparedStatement stm = con.prepareStatement(sql);
						stm.setString(1, thongtin);
						stm.setString(2, taiKhoan);
						stm.executeUpdate();
						con.close();

						dm.setRowCount(0);

						// Đưa dữ liệu mới vào DefaultTableModel
						themdulieuvaobang();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	private void xoataikhoan() {
		int dialogResult = JOptionPane.showConfirmDialog(null,
				"Bạn có muốn xóa tài khoản đã chọn?\n(Tài khoản sau khi xóa sẽ không thể khôi phục)", "Xác nhận xóa",
				JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION) {
			// Xóa các dòng được chọn
			for (int i = tbl.getRowCount() - 1; i >= 0; i--) {
				String taikhoan = (String) tbl.getValueAt(i, 1);

				if ((boolean) tbl.getValueAt(i, 0)) {
					dm.removeRow(i);

					String sql = "DELETE FROM sang.tkfb WHERE `TaiKhoan` = ?;";

					try {
						Connection con = new DBController().getConnection();
						PreparedStatement stm = con.prepareStatement(sql);
						stm.setString(1, taikhoan);
						stm.executeUpdate();
						con.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		}
	}

	private int timKiemVaHienThi(String thongtin, int startIndex, int colum) {
		// Bắt đầu từ startIndex, tìm kiếm và hiển thị kết quả
		for (int i = startIndex; i < tbl.getRowCount(); i++) {
			// Lấy giá trị ở cột cần tìm kiếm
			String giaTriCot = tbl.getValueAt(i, colum).toString().toLowerCase();

			// So sánh với từ khóa
			if (giaTriCot.equals(thongtin)) {
				// Nếu tìm thấy, hiển thị và trả về vị trí
				tbl.getSelectionModel().setSelectionInterval(i, i);
				tbl.scrollRectToVisible(tbl.getCellRect(i, 0, true));
				hienthongtintimkiem(i);
				return i;
				
			}
			
		}

		// Nếu không tìm thấy, hiển thị thông báo hoặc thực hiện hành động khác
		JOptionPane.showMessageDialog(null, "Không tìm thấy kết quả nào khác");

		// Trả về -1 để bắt đầu lại từ đầu nếu cần
		return -1;
		
	}
	
	
	
	

}