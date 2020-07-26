
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Swingfile extends JFrame implements ActionListener {
	JButton btnReset, btnInsert, btnSelectbook, btnSelectorders, btnSelectcustomer;
	JButton okInsert, cancelInsert;
	PreparedStatement ps = null;
	JTextArea txtResult;
	JTextField tf1, tf2, tf3, tf4;
	JPanel pn1, pn2;
	JScrollPane scrollPane;
	int rowCnt = 0;

	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public Swingfile() {
		super("15010970/�ڼ���");
		layInit();
		conDB();
		setVisible(true);
		setBounds(200, 200, 600, 400); // ������ġ,������ġ,���α���,���α���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void layInit() {
		btnReset = new JButton("�ʱ�ȭ");
		btnInsert = new JButton("�Է�1");
		btnSelectbook = new JButton("�˻�1");
		btnSelectorders = new JButton("�˻�2");
		btnSelectcustomer = new JButton("�˻�3");
		pn1 = new JPanel();
		txtResult = new JTextArea();
		
		
		pn1.add(btnReset);
		pn1.add(btnInsert);
		pn1.add(btnSelectbook);
		pn1.add(btnSelectorders);
		pn1.add(btnSelectcustomer);

		txtResult.setEditable(false);
		scrollPane = new JScrollPane(txtResult);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add("Center", scrollPane);
		add("North", pn1);
		
		setVisible(true);

		btnReset.addActionListener(this);
		btnInsert.addActionListener(this);
		btnSelectbook.addActionListener(this);
		btnSelectorders.addActionListener(this);
		btnSelectcustomer.addActionListener(this);

	}

	public void Insert() {
		setLayout(new BorderLayout());
		pn2 = new JPanel(new GridLayout(5, 2));
		add(pn2, BorderLayout.CENTER);
		pn2.setSize(600, 400);
		okInsert = new JButton("�Է�");
		cancelInsert = new JButton("���ư���");
		tf1 = new JTextField("");
		tf2 = new JTextField("");
		tf3 = new JTextField("");
		tf4 = new JTextField("");
		JLabel label1 = new JLabel("CUSTID", JLabel.CENTER);
		JLabel label2 = new JLabel("BOOKID", JLabel.CENTER);
		JLabel label3 = new JLabel("SALEPRICE", JLabel.CENTER);
		JLabel label4 = new JLabel("ORDERDATE (YYYY-MM-DD)", JLabel.CENTER);

		pn1.setVisible(false);
		scrollPane.setVisible(false);

		pn2.add(label1);
		pn2.add(tf1);

		pn2.add(label2);
		pn2.add(tf2);

		pn2.add(label3);
		pn2.add(tf3);

		pn2.add(label4);
		pn2.add(tf4);

		pn2.add(okInsert);
		pn2.add(cancelInsert);

		setVisible(true);
		add(pn2);

		okInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int custid = 0;
				int bookid = 0;
				int saleprice = 0;
				String orderdate = null;
				try {
					custid = Integer.parseInt(tf1.getText());
					bookid = Integer.parseInt(tf2.getText());
					saleprice = Integer.parseInt(tf3.getText());
					orderdate = tf4.getText();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println("�����߻�");
				}
				try {
					rs = stmt.executeQuery("SELECT COUNT(*) FROM Orders");
					if (rs.next())
						rowCnt = rs.getInt(1);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				String insertSQL = "INSERT INTO orders VALUES (?, ?, ?, ?,?)";
				try {
					ps = con.prepareStatement(insertSQL);
					ps.setInt(1, rowCnt + 1);
					ps.setInt(2, custid);
					ps.setInt(3, bookid);
					ps.setInt(4, saleprice);
					ps.setString(5, orderdate);
					ps.execute();

					JOptionPane.showMessageDialog(null, "�Է¿Ϸ�!");

				} catch (SQLException e7) {
					JOptionPane.showMessageDialog(null, e7.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} finally {
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					tf4.setText("");
				}

			}
		});
		cancelInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pn2.setVisible(false);
				layInit();
			}

		});

	}

	public void addData() {
		try {
			
			String addcust1 = "INSERT INTO Customer VALUES (1, '������', '���� ��ü��Ÿ', '000-5000-0001')";
			String addcust2 = "INSERT INTO Customer VALUES (2, '�迬��', '���ѹα� ����', '000-6000-0001')";  
			String addcust3 = "INSERT INTO Customer VALUES (3, '��̶�', '���ѹα� ������', '000-7000-0001')";
			String addcust4 = "INSERT INTO Customer VALUES (4, '�߽ż�', '�̱� Ŭ������', '000-8000-0001')";
			String addcust5 = "INSERT INTO Customer VALUES (5, '�ڼ���', '���ѹα� ����',  NULL)";
			String addcust6 = "INSERT INTO customer VALUES(6, '������', '���ѹα� �뱸', '010-1111-1111')";
			String addcust7 = "INSERT INTO customer VALUES(7, '�㰡��', '�Ϻ� ����ī', '010-2222-2222')";
			String addcust8 = "INSERT INTO customer VALUES(8, '������', '���ѹα� ����', '010-3333-3333')";
			String addcust9 = "INSERT INTO customer VALUES(9, '������', '�̱� ������', '010-4444-4444')";
			String addcust10 = "INSERT INTO customer VALUES(10, '�Ǽ���', '������ �ĸ�', '010-5555-5555')";
				
			
			
			String addbook1 = "INSERT INTO Book VALUES(1, '�౸�� ����', '�½�����', 7000)";
			String addbook2 = "INSERT INTO Book VALUES(2, '�౸�ƴ� ����', '������', 13000)";
			String addbook3 = "INSERT INTO Book VALUES(3, '�౸�� ����', '���ѹ̵��', 22000)";
			String addbook4 = "INSERT INTO Book VALUES(4, '���� ���̺�', '���ѹ̵��', 35000)";
			String addbook5 = "INSERT INTO Book VALUES(5, '�ǰ� ����', '�½�����', 8000)";
			String addbook6 = "INSERT INTO Book VALUES(6, '���� �ܰ躰���', '�½�����', 6000)";
			String addbook7 = "INSERT INTO Book VALUES(7, '�߱��� �߾�', '�̻�̵��', 20000)";
			String addbook8 = "INSERT INTO Book VALUES(8, '�߱��� ��Ź��', '�̻�̵��', 13000)";
			String addbook9 = "INSERT INTO Book VALUES(9, '�ø��� �̾߱�', '�Ｚ��', 7500)";
			String addbook10 = "INSERT INTO Book VALUES(10, 'Olympic Champions', 'Pearson', 13000)";
			String addbook11 = "INSERT INTO book VALUES(11, '�ٴҶ������Ʈ', '�Ҹ���Ŀ��', 5000)";
			String addbook12 = "INSERT INTO book VALUES(12, '��������Ǫġ��', '��Ÿ����', 29000)";
			String addbook13 = "INSERT INTO book VALUES(13, '�ް���', '��ٹ�', 9000)";
			String addbook14 = "INSERT INTO book VALUES(14, 'ī���', '�̵��Ŀ��', 13000)";
			String addbook15 = "INSERT INTO book VALUES(15, 'ī���ī', '�������ʽ�', 22000)";
			String addbook16 = "INSERT INTO book VALUES(16, '�ٴҶ��', '�����÷��̽�', 7000)";
			String addbook17 = "INSERT INTO book VALUES(17, '���Ʈ �׶�ϵ�', '�Ľ�����', 4000)";
			String addbook18 = "INSERT INTO book VALUES(18, '���⽺����', 'Ŀ�Ǻ�', 19000)";
			String addbook19 = "INSERT INTO book VALUES(19, '�������̵�', 'Ž��Ž��', 24000)";
			String addbook20 = "INSERT INTO book VALUES(20, '����������', '���ټ�', 6000)";

			String addorder1 = "INSERT INTO Orders VALUES (1, 1, 1, 6000, STR_TO_DATE('2014-07-01','%Y-%m-%d'))"; 
			String addorder2 = "INSERT INTO Orders VALUES (2, 1, 3, 21000, STR_TO_DATE('2014-07-03','%Y-%m-%d'))";
			String addorder3 = "INSERT INTO Orders VALUES (3, 2, 5, 8000, STR_TO_DATE('2014-07-03','%Y-%m-%d'))"; 
			String addorder4 = "INSERT INTO Orders VALUES (4, 3, 6, 6000, STR_TO_DATE('2014-07-04','%Y-%m-%d'))"; 
			String addorder5 = "INSERT INTO Orders VALUES (5, 4, 7, 20000, STR_TO_DATE('2014-07-05','%Y-%m-%d'))";
			String addorder6 = "INSERT INTO Orders VALUES (6, 1, 2, 12000, STR_TO_DATE('2014-07-07','%Y-%m-%d'))";
			String addorder7 = "INSERT INTO Orders VALUES (7, 4, 8, 13000, STR_TO_DATE( '2014-07-07','%Y-%m-%d'))";
			String addorder8 = "INSERT INTO Orders VALUES (8, 3, 10, 12000, STR_TO_DATE('2014-07-08','%Y-%m-%d'))"; 
			String addorder9 = "INSERT INTO Orders VALUES (9, 2, 10, 7000, STR_TO_DATE('2014-07-09','%Y-%m-%d'))"; 
			String addorder10 = "INSERT INTO Orders VALUES (10, 3, 8, 13000, STR_TO_DATE('2014-07-10','%Y-%m-%d'))";
			String addorder11 = "INSERT INTO orders VALUES(11, 4, 4, 33000, STR_TO_DATE('2019-10-21','%Y-%m-%d'))";
			String addorder12 = "INSERT INTO orders VALUES(12, 9, 15, 21000, STR_TO_DATE('2019-10-23','%Y-%m-%d'))";
			String addorder13 = "INSERT INTO orders VALUES(13, 2, 20, 4000, STR_TO_DATE('2019-10-26','%Y-%m-%d'))";
			String addorder14 = "INSERT INTO orders VALUES(14, 10, 15, 20000, STR_TO_DATE('2019-12-21','%Y-%m-%d'))";
			String addorder15 = "INSERT INTO orders VALUES(15, 3, 17, 4000, STR_TO_DATE('2020-01-05','%Y-%m-%d'))";
			String addorder16 = "INSERT INTO orders VALUES(16, 7, 19, 24000, STR_TO_DATE('2020-01-21','%Y-%m-%d'))";
			String addorder17 = "INSERT INTO orders VALUES(17, 1, 8, 11000, STR_TO_DATE('2020-02-18','%Y-%m-%d'))";
			String addorder18 = "INSERT INTO orders VALUES(18, 9, 11, 5000, STR_TO_DATE('2020-03-04','%Y-%m-%d'))";
			String addorder19 = "INSERT INTO orders VALUES(19, 5, 11, 4000, STR_TO_DATE('2020-04-12','%Y-%m-%d'))";
			String addorder20 = "INSERT INTO orders VALUES(20, 6, 10, 12000, STR_TO_DATE('2020-04-15','%Y-%m-%d'))";

			stmt = con.createStatement();

			stmt.executeUpdate(addcust1);
			stmt.executeUpdate(addcust2);
			stmt.executeUpdate(addcust3);
			stmt.executeUpdate(addcust4);
			stmt.executeUpdate(addcust5);
			stmt.executeUpdate(addcust6);
			stmt.executeUpdate(addcust7);
			stmt.executeUpdate(addcust8);
			stmt.executeUpdate(addcust9);
			stmt.executeUpdate(addcust10);

			
			stmt.executeUpdate(addbook1);
			stmt.executeUpdate(addbook2);
			stmt.executeUpdate(addbook3);
			stmt.executeUpdate(addbook4);
			stmt.executeUpdate(addbook5);
			stmt.executeUpdate(addbook6);
			stmt.executeUpdate(addbook7);
			stmt.executeUpdate(addbook8);
			stmt.executeUpdate(addbook9);
			stmt.executeUpdate(addbook10);
			stmt.executeUpdate(addbook11);
			stmt.executeUpdate(addbook12);
			stmt.executeUpdate(addbook13);
			stmt.executeUpdate(addbook14);
			stmt.executeUpdate(addbook15);
			stmt.executeUpdate(addbook16);
			stmt.executeUpdate(addbook17);
			stmt.executeUpdate(addbook18);
			stmt.executeUpdate(addbook19);
			stmt.executeUpdate(addbook20);

			stmt.executeUpdate(addorder1);
			stmt.executeUpdate(addorder2);
			stmt.executeUpdate(addorder3);
			stmt.executeUpdate(addorder4);
			stmt.executeUpdate(addorder5);
			stmt.executeUpdate(addorder6);
			stmt.executeUpdate(addorder7);
			stmt.executeUpdate(addorder8);
			stmt.executeUpdate(addorder9);
			stmt.executeUpdate(addorder10);
			stmt.executeUpdate(addorder11);
			stmt.executeUpdate(addorder12);
			stmt.executeUpdate(addorder13);
			stmt.executeUpdate(addorder14);
			stmt.executeUpdate(addorder15);
			stmt.executeUpdate(addorder16);
			stmt.executeUpdate(addorder17);
			stmt.executeUpdate(addorder18);
			stmt.executeUpdate(addorder19);
			stmt.executeUpdate(addorder20);
			
		} catch (Exception e9) {
			System.out.println("���� �б� ���� :" + e9);
		}

	}

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("����̹� �ε� ����");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { /* �����ͺ��̽��� �����ϴ� ���� */
			System.out.println("�����ͺ��̽� ���� �غ�...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("�����ͺ��̽� ���� ����");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			stmt = con.createStatement();

			String sbookquery = "SELECT * FROM Book ";
			String sordersquery = "SELECT * FROM Orders ";
			String scustomerquery = "SELECT * FROM Customer ";
			String delquery = "DELETE FROM ? WHERE ? = ?";
			if (e.getSource() == btnSelectbook) {
				txtResult.setText("");
				txtResult.setText("BOOKNO	BOOK NAME	PUBLISHER	PRICE\n");
				rs = stmt.executeQuery(sbookquery);
				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
							+ "\n";
					txtResult.append(str);
				}
			} else if (e.getSource() == btnSelectorders) {
				txtResult.setText("");
				txtResult.setText("ORDERID	CUSTID	BOOKID	SALEPRICE	ORDERDATE\n");
				rs = stmt.executeQuery(sordersquery);
				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
							+ rs.getString(5) + "\n";
					txtResult.append(str);
				}
			} else if (e.getSource() == btnSelectcustomer) {
				txtResult.setText("");
				txtResult.setText("CUSTID	NAME	ADDRESS	PHONE\n");
				rs = stmt.executeQuery(scustomerquery);
				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+ "\n";
					txtResult.append(str);
				}
			}

			else if (e.getSource() == btnReset) {
				txtResult.setText("");
				try {
					rs = stmt.executeQuery("SELECT COUNT(*) FROM Orders");
					if (rs.next())
						rowCnt = rs.getInt(1);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				while (rowCnt > 0) {
					ps = con.prepareStatement("DELETE FROM Orders WHERE orderid = ?");
					ps.setInt(1, rowCnt);
					ps.executeUpdate();
					--rowCnt;
				}

				try {
					rs = stmt.executeQuery("SELECT COUNT(*) FROM Book");
					if (rs.next())
						rowCnt = rs.getInt(1);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				while (rowCnt > 0) {
					ps = con.prepareStatement("DELETE FROM Book WHERE bookid = ?");
					ps.setInt(1, rowCnt);
					ps.execute();
					--rowCnt;
				}

				try {
					rs = stmt.executeQuery("SELECT COUNT(*) FROM customer");
					if (rs.next())
						rowCnt = rs.getInt(1);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				while (rowCnt > 0) {
					ps = con.prepareStatement("DELETE FROM customer WHERE custid = ?");
					ps.setInt(1, rowCnt);
					ps.execute();
					--rowCnt;
				}
				
				addData();

			} else if (e.getSource() == btnInsert) {
				Insert();
			}
		} catch (Exception e2) {
			System.out.println("���� �б� ���� :" + e2);
		}

	}

	public static void main(String[] args) {
		Swingfile BLS = new Swingfile();

		BLS.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					con.close();
				} catch (Exception e4) {
				}
				System.out.println("���α׷� ���� ����!");
				System.exit(0);
			}
		});
	}
}
