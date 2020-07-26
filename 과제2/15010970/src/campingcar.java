import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

import javax.swing.*;

@SuppressWarnings("serial")
public class campingcar extends JFrame implements ActionListener {
	JButton btnAdmin, btnUsr;
	JButton InitDB, changedb, rtcar, repairinfo, searchthing;
	JButton searchcar, rentcar;
	JPanel mtitle, pmadmin1, pmadmin2, pmusr1, pmusr2, srhusr1, srhusr2;
	JPanel cdb0, cdb1, cdb2, cdbs;
	JScrollPane scrollPane, scrollPane1;
	int rowCnt = 0;
	PreparedStatement ps = null;
	
	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	
	public campingcar() {
		super("15010970/박서인");
		layInit();
		conDB();
		setVisible(true);
		setBounds(200, 200, 600, 400); // 가로위치,세로위치,가로길이,세로길이
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void layInit() {
		btnAdmin = new JButton("관리자");
		btnUsr = new JButton("사용자");
		mtitle = new JPanel();
		mtitle.setLayout(new GridLayout(2,1));
		mtitle.add(btnAdmin);
		mtitle.add(btnUsr);
		add(mtitle);
		
		mtitle.setVisible(true);
		btnAdmin.addActionListener(this);
		btnUsr.addActionListener(this);
	}
	
	public void selectAdmin() {
		InitDB = new JButton("DB 초기화");
		changedb = new JButton("DB 접근");
		rtcar = new JButton("자동차 반환");
		repairinfo = new JButton("정비관련");
		searchthing = new JButton("검색");
		JButton goback = new JButton("돌아가기");
		pmadmin1 = new JPanel();
		pmadmin2 = new JPanel();
		
		add(pmadmin1);
		add("South", pmadmin2);
		
		pmadmin1.setVisible(true);
		pmadmin1.setLayout(new GridLayout(1, 5));

		pmadmin1.add(InitDB);
		pmadmin1.add(changedb);
		pmadmin1.add(rtcar);
		pmadmin1.add(repairinfo);
		pmadmin1.add(searchthing);
		
		pmadmin2.setVisible(true);
		pmadmin2.add(goback);
		
		goback.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pmadmin1.setVisible(false);
				pmadmin2.setVisible(false);
				mtitle.setVisible(true);
			}
			
		});
		InitDB.addActionListener(this);
		changedb.addActionListener(this);
		
	}
	public void changedb() {
		JButton goback = new JButton("돌아가기");
		JLabel check = new JLabel("편집할 데이터베이스를 선택하세요", JLabel.CENTER);
		JTextArea result = new JTextArea();
		check.setFont(check.getFont().deriveFont(12.0f));
		
		JButton insert = new JButton("입력");
		JButton delete = new JButton("삭제");
		JButton update = new JButton("변경");
		
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("");
		combo.addItem("campingcar");
		combo.addItem("company");
		combo.addItem("user");
		combo.addItem("repairshop");
		
		cdb0 = new JPanel();
		cdb1 = new JPanel();
		cdb2 = new JPanel();
		cdbs = new JPanel();
		
		
		//cdbs.setBounds(350, );
		add("North", cdb0);
		add("South",cdb2);
		add("East",cdbs);
		cdbs.setPreferredSize(new Dimension(240, 340));
		add(cdb1);
		cdb1.setLayout(null);
		
		cdb0.setVisible(true);
		cdb1.setVisible(true);
		cdb2.setVisible(true);
		cdbs.setVisible(true);
		
		cdbs.setLayout(new GridLayout(3, 1));
		cdbs.add(insert);
		cdbs.add(delete);
		cdbs.add(update);
		
		cdb0.add(check,BorderLayout.CENTER);
		cdb0.add(combo, BorderLayout.SOUTH);
		
		result.setEditable(false);
		scrollPane1 = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		cdb1.add(scrollPane1);
		scrollPane1.setBounds(0,0, 340, 290);
		
		
		
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String table = combo.getSelectedItem().toString();
				try {
					stmt = con.createStatement();
					if(table == "") {
					JOptionPane.showMessageDialog(null, "데이터베이스를 입력해주세요!", "오류", JOptionPane.ERROR_MESSAGE);
					}else if(table == "campingcar") {
						String showcar = "SELECT * FROM campingcar";
						result.setText("");
						result.setText("CARID	NAME	CARNUM	CAPACITY	MADIN	MADEYEAR	DISTANCE	RENTCHARGE	COMID	DATE\n");
						rs = stmt.executeQuery(showcar);
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getInt(7) + "\t" + rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getString(10)
									+ "\n";
							result.append(str);
						}
					}else if(table == "company") {
						String showcom = "SELECT * FROM company";
						result.setText("");
						result.setText("COMPANY ID	NAME	ADDRESS	PHONE	MANAGERNAME	MANAGERMAIL\n");
						rs = stmt.executeQuery(showcom);
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t"+ "\t" + rs.getString(6) 
									+ "\n";
							result.append(str);
						}
					}else if(table == "user") {
						String showusr = "SELECT * FROM user";
						result.setText("");
						result.setText("LICENUM	NAME	ADDRESS	PHONE	MAIL\n");
						rs = stmt.executeQuery(showusr);
						while (rs.next()) {
							String str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5)
									+ "\n";
							result.append(str);
						}
					}else if(table == "repairshop") {
						String showrs = "SELECT * FROM repairshop";
						result.setText("");
						result.setText("SHOPID	NAME	ADDRESS	PHONE	MANAGER	MANAGERMAIL\n");
						rs = stmt.executeQuery(showrs);
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6)
									+ "\n";
							result.append(str);
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		insert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String table = combo.getSelectedItem().toString();
				if(table == "") {
					JOptionPane.showMessageDialog(null, "입력할 데이터베이스를 골라주세요!", "오류", JOptionPane.ERROR_MESSAGE);
				}else if(table == "campingcar") {
				cdbs.setVisible(false);
				JPanel insertc = new JPanel();
				add("East",insertc);
				insertc.setPreferredSize(new Dimension(250, 340));
				insertc.setLayout(new GridLayout(9,2));
				insertc.setVisible(true);
				
				JButton okInsert = new JButton("입력");
				JButton cancelInsert = new JButton("돌아가기");
				
				JTextField tf1 = new JTextField("");
				JTextField tf2 = new JTextField("");
				JTextField tf3 = new JTextField("");
				JTextField tf4 = new JTextField("");
				JTextField tf5 = new JTextField("");
				JTextField tf6 = new JTextField("");
				JTextField tf7 = new JTextField("");
				JTextField tf8 = new JTextField("");
				
				JLabel c1 = new JLabel("차량이름 : ", JLabel.CENTER);
				JLabel c2 = new JLabel("차량번호 : ", JLabel.CENTER);
				JLabel c3 = new JLabel("승차인원 : ", JLabel.CENTER);
				JLabel c4 = new JLabel("제조회사 : ", JLabel.CENTER);
				JLabel c5 = new JLabel("제조연도 : ", JLabel.CENTER);
				JLabel c6 = new JLabel("주행거리 : ", JLabel.CENTER);
				JLabel c7 = new JLabel("1박비용 : ", JLabel.CENTER);
				JLabel c8 = new JLabel("회사ID : ", JLabel.CENTER);
				
				insertc.add(c1);
				insertc.add(tf1);

				insertc.add(c2);
				insertc.add(tf2);

				insertc.add(c3);
				insertc.add(tf3);

				insertc.add(c4);
				insertc.add(tf4);
				
				insertc.add(c5);
				insertc.add(tf5);
				
				insertc.add(c6);
				insertc.add(tf6);
				
				insertc.add(c7);
				insertc.add(tf7);
				
				insertc.add(c8);
				insertc.add(tf8);
				
				insertc.add(okInsert);
				insertc.add(cancelInsert);
				
				
				okInsert.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String carname = null;
						int carnum = 0;
						int capa = 0;
						String madein = null;
						int madeyear = 0;
						int dist = 0;
						int charge = 0;
						int comid = 0;
						String date = null;
						try {
							carname = tf1.getText();
							carnum = Integer.parseInt(tf2.getText());
							capa = Integer.parseInt(tf3.getText());
							madein = tf4.getText();
							madeyear = Integer.parseInt(tf5.getText());
							dist = Integer.parseInt(tf6.getText());
							charge = Integer.parseInt(tf7.getText());
							comid = Integer.parseInt(tf8.getText());
							date = LocalDate.now().toString();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("오류발생");
						}
						try {
							rs = stmt.executeQuery("SELECT COUNT(*) FROM campingcar");
							if (rs.next())
								rowCnt = rs.getInt(1);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						String insertSQL = "INSERT INTO campingcar VALUES (?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
						try {
							ps = con.prepareStatement(insertSQL);
							ps.setInt(1, rowCnt + 1);
							ps.setString(2, carname);
							ps.setInt(3, carnum);
							ps.setInt(4, capa);
							ps.setString(5, madein);
							ps.setInt(6, madeyear);
							ps.setInt(7, dist);
							ps.setInt(8, charge);
							ps.setInt(9, comid);
							ps.setString(10, date);
							ps.execute();
						
							JOptionPane.showMessageDialog(null, "입력완료!");
							String showcar = "SELECT * FROM campingcar";
							rs = stmt.executeQuery(showcar);
							while (rs.next()) {
								String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getInt(7) + "\t" + rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getString(10)
										+ "\n";
								result.append(str);
							}

						} catch (SQLException e7) {
							JOptionPane.showMessageDialog(null, e7.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						} finally {
							cdbs.setVisible(true);
							insertc.setVisible(false);
						}

					}
				});
				
				cancelInsert.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						insertc.setVisible(false);
						cdbs.setVisible(true);
					}

				}); 
				}else if(table == "company") {
					cdbs.setVisible(false);
					JPanel insertp = new JPanel();
					add("East",insertp);
					insertp.setPreferredSize(new Dimension(250, 340));
					insertp.setLayout(new GridLayout(6,2));
					insertp.setVisible(true);
					
					JButton okInsert = new JButton("입력");
					JButton cancelInsert = new JButton("돌아가기");
					
					JTextField tf1 = new JTextField("");
					JTextField tf2 = new JTextField("");
					JTextField tf3 = new JTextField("");
					JTextField tf4 = new JTextField("");
					JTextField tf5 = new JTextField("");
					
					JLabel c1 = new JLabel("회사이름 : ", JLabel.CENTER);
					JLabel c2 = new JLabel("회사주소 : ", JLabel.CENTER);
					JLabel c3 = new JLabel("회사번호 : ", JLabel.CENTER);
					JLabel c4 = new JLabel("담당자이름 : ", JLabel.CENTER);
					JLabel c5 = new JLabel("담당자메일 : ", JLabel.CENTER);
					
					insertp.add(c1);
					insertp.add(tf1);

					insertp.add(c2);
					insertp.add(tf2);

					insertp.add(c3);
					insertp.add(tf3);

					insertp.add(c4);
					insertp.add(tf4);
					
					insertp.add(c5);
					insertp.add(tf5);
					
					insertp.add(okInsert);
					insertp.add(cancelInsert);
					
					okInsert.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String comname = null;
							String comadd = null;
							String comphone = null;
							String commana = null;
							String commail = null;
							boolean isName = false;
							
							while(!isName) {
							comname = tf1.getText();
							comadd = tf2.getText();
							comphone = tf3.getText();
							commana = tf4.getText();
							commail = tf5.getText();

							if(tf1.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "데이터를 확인하세요", "Error", JOptionPane.ERROR_MESSAGE);
								break;
							}else {
								isName = true;
							
							try {
								rs = stmt.executeQuery("SELECT COUNT(*) FROM company");
								if (rs.next())
									rowCnt = rs.getInt(1);
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}

							String insertSQL = "INSERT INTO company VALUES (?, ?, ?, ?, ?, ?)";
							try {
								ps = con.prepareStatement(insertSQL);
								ps.setInt(1, rowCnt + 1);
								ps.setString(2, comname);
								ps.setString(3, comadd);
								ps.setString(4, comphone);
								ps.setString(5, commana);
								ps.setString(6, commail);
								ps.execute();

								JOptionPane.showMessageDialog(null, "입력완료!");
								String showcar = "SELECT * FROM company";
								rs = stmt.executeQuery(showcar);
								while (rs.next()) {
									String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t"+ "\t" + rs.getString(6) 
									+ "\n";
									result.append(str);
								}

							} catch (SQLException e7) {
								JOptionPane.showMessageDialog(null, e7.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							} finally {
								cdbs.setVisible(true);
								insertp.setVisible(false);
							}
							}
							}
						}
						
					});//okinsert
					cancelInsert.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							cdbs.setVisible(true);
							insertp.setVisible(false);
						}
					});
				}//company insert
				else if(table == "user") {
					cdbs.setVisible(false);
					JPanel insertu = new JPanel();
					add("East",insertu);
					insertu.setPreferredSize(new Dimension(250, 340));
					insertu.setLayout(new GridLayout(6,2));
					insertu.setVisible(true);
					
					JButton okInsert = new JButton("입력");
					JButton cancelInsert = new JButton("돌아가기");
					
					JTextField tf1 = new JTextField("");
					JTextField tf2 = new JTextField("");
					JTextField tf3 = new JTextField("");
					JTextField tf4 = new JTextField("");
					JTextField tf5 = new JTextField("");
					
					JLabel c1 = new JLabel("면허번호 : ", JLabel.CENTER);
					JLabel c2 = new JLabel("이름 : ", JLabel.CENTER);
					JLabel c3 = new JLabel("주소 : ", JLabel.CENTER);
					JLabel c4 = new JLabel("전화번호 : ", JLabel.CENTER);
					JLabel c5 = new JLabel("메일주소 : ", JLabel.CENTER);
					
					insertu.add(c1);
					insertu.add(tf1);

					insertu.add(c2);
					insertu.add(tf2);

					insertu.add(c3);
					insertu.add(tf3);

					insertu.add(c4);
					insertu.add(tf4);
					
					insertu.add(c5);
					insertu.add(tf5);
					
					insertu.add(okInsert);
					insertu.add(cancelInsert);
					
					okInsert.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String licenum = null;
							String cusname = null;
							String cusadd = null;
							String cusphone = null;
							String cusmail = null;
							boolean isName = false;
							
							while(!isName) {
								licenum = tf1.getText();
								cusname = tf2.getText();
								cusadd = tf3.getText();
								cusphone = tf4.getText();
								cusmail = tf5.getText();
							
								if(tf1.getText().isEmpty()) {
									JOptionPane.showMessageDialog(null, "데이터를 확인하세요", "Error", JOptionPane.ERROR_MESSAGE);
									break;
								}else {
									isName = true;

							String insertSQL = "INSERT INTO user VALUES (?, ?, ?, ?, ?)";
							try {
								ps = con.prepareStatement(insertSQL);
								ps.setString(1, licenum);
								ps.setString(2, cusname);
								ps.setString(3, cusadd);
								ps.setString(4, cusphone);
								ps.setString(5, cusmail);
								ps.execute();

								JOptionPane.showMessageDialog(null, "입력완료!");
								String showcar = "SELECT * FROM user";
								rs = stmt.executeQuery(showcar);
								while (rs.next()) {
									String str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"+ rs.getString(5) 
									+ "\n";
									result.append(str);
								}

							} catch (SQLException e7) {
								JOptionPane.showMessageDialog(null, e7.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							} finally {
								cdbs.setVisible(true);
								insertu.setVisible(false);
							}
						}
							}
						}
					}); //okinsert
					cancelInsert.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							cdbs.setVisible(true);
							insertu.setVisible(false);
						}

					});	
				}//user insert
				else if(table == "repairshop") {
					cdbs.setVisible(false);
					JPanel insertr = new JPanel();
					add("East",insertr);
					insertr.setPreferredSize(new Dimension(250, 340));
					insertr.setLayout(new GridLayout(6,2));
					insertr.setVisible(true);
					
					JButton okInsert = new JButton("입력");
					JButton cancelInsert = new JButton("돌아가기");
					
					JTextField tf1 = new JTextField("");
					JTextField tf2 = new JTextField("");
					JTextField tf3 = new JTextField("");
					JTextField tf4 = new JTextField("");
					JTextField tf5 = new JTextField("");
					
					JLabel c1 = new JLabel("정비소명 : ", JLabel.CENTER);
					JLabel c2 = new JLabel("주소 : ", JLabel.CENTER);
					JLabel c3 = new JLabel("전화번호 : ", JLabel.CENTER);
					JLabel c4 = new JLabel("담당자이름 : ", JLabel.CENTER);
					JLabel c5 = new JLabel("메일주소 : ", JLabel.CENTER);
					
					insertr.add(c1);
					insertr.add(tf1);

					insertr.add(c2);
					insertr.add(tf2);

					insertr.add(c3);
					insertr.add(tf3);

					insertr.add(c4);
					insertr.add(tf4);
					
					insertr.add(c5);
					insertr.add(tf5);
					
					insertr.add(okInsert);
					insertr.add(cancelInsert);
					
					okInsert.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String wsname = null;
							String wsadd = null;
							String wsphone = null;
							String wsmana = null;
							String wsmail = null;
							boolean isName = false;
							
							while(!isName) {
							wsname = tf1.getText();
							wsadd = tf2.getText();
							wsphone = tf3.getText();
							wsmana = tf4.getText();
							wsmail = tf5.getText();

							if(tf1.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "데이터를 확인하세요", "Error", JOptionPane.ERROR_MESSAGE);
								break;
							}else {
								isName = true;
								
							try {
								rs = stmt.executeQuery("SELECT COUNT(*) FROM repairshop");
								if (rs.next())
									rowCnt = rs.getInt(1);
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}

							String insertSQL = "INSERT INTO repairshop VALUES (?, ?, ?, ?, ?, ?)";
							try {
								ps = con.prepareStatement(insertSQL);
								ps.setInt(1, rowCnt+1);
								ps.setString(2, wsname);
								ps.setString(3, wsadd);
								ps.setString(4, wsphone);
								ps.setString(5, wsmana);
								ps.setString(6,  wsmail);
								ps.execute();

								JOptionPane.showMessageDialog(null, "입력완료!");
								String showcar = "SELECT * FROM repairshop";
								rs = stmt.executeQuery(showcar);
								while (rs.next()) {
									String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6)
									+ "\n";
									result.append(str);
								}

							} catch (SQLException e7) {
								JOptionPane.showMessageDialog(null, e7.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							} finally {
								cdbs.setVisible(true);
								insertr.setVisible(false);
							}
						}
							}
						}
					});//okinsert
					cancelInsert.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							cdbs.setVisible(true);
							insertr.setVisible(false);
						}
					});	
				}
			}
			
		});
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String table = combo.getSelectedItem().toString();
				if(table == "") {
						JOptionPane.showMessageDialog(null, "삭제할 데이터베이스를 골라주세요!", "오류", JOptionPane.ERROR_MESSAGE);
				}
				else if(table == "campingcar") {
					cdbs.setVisible(false);
					JPanel delcar = new JPanel();
					JLabel dele = new JLabel("삭제할 캠핑카의 id를 입력해주세요 ");
					JTextField tf = new JTextField("");
					JButton okdel = new JButton("삭제");
					JButton cancel = new JButton("돌아가기");
					
					add("East",delcar);
					delcar.setPreferredSize(new Dimension(250, 340));
					delcar.setVisible(true);
					delcar.setLayout(new GridLayout(4,1));
					
					delcar.add(dele);
					delcar.add(tf);
					delcar.add(okdel);
					delcar.add(cancel);
					
					okdel.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							int carid = 0;
							try {
							carid = Integer.parseInt(tf.getText());
						}catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("오류발생");
						}
						String delSQL = "DELETE FROM campingcar WHERE carid = ?";
						
							try {
								ps = con.prepareStatement(delSQL);
								ps.setInt(1, carid);
								ps.executeUpdate();
								
								JOptionPane.showMessageDialog(null, "삭제완료!");
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}finally {
								String showcar = "SELECT * FROM campingcar";
								result.setText("");
								result.setText("CARID	NAME	CARNUM	CAPACITY	MADIN	MADEYEAR	DISTANCE	RENTCHARGE	COMID	DATE\n");
								try {
									rs = stmt.executeQuery(showcar);
									while (rs.next()) {
										String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getInt(7) + "\t" + rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getString(10)
												+ "\n";
										result.append(str);
									}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								cdbs.setVisible(true);
								delcar.setVisible(false);
							}
						}
					});
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
						cdbs.setVisible(true);
						delcar.setVisible(false);
						}
					});
				}else if(table == "company") {
					cdbs.setVisible(false);
					JPanel delcom = new JPanel();
					JLabel dele = new JLabel("삭제할 회사의 id를 입력해주세요 ");
					JTextField tf = new JTextField("");
					JButton okdel = new JButton("삭제");
					JButton cancel = new JButton("돌아가기");
					
					add("East",delcom);
					delcom.setPreferredSize(new Dimension(250, 340));
					delcom.setVisible(true);
					delcom.setLayout(new GridLayout(4,1));
					
					delcom.add(dele);
					delcom.add(tf);
					delcom.add(okdel);
					delcom.add(cancel);
					
					okdel.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							int comid = 0;
							try {
							comid = Integer.parseInt(tf.getText());
						}catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("오류발생");
						}
						String delSQL = "DELETE FROM company WHERE comid = ?";
						
							try {
								ps = con.prepareStatement(delSQL);
								ps.setInt(1, comid);
								ps.executeUpdate();
								
								JOptionPane.showMessageDialog(null, "삭제완료!");
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}finally {
								String showcom = "SELECT * FROM company";
								result.setText("");
								result.setText("COMPANY ID	NAME	ADDRESS	PHONE	MANAGERNAME	MANAGERMAIL\n");
								
								try {
									rs = stmt.executeQuery(showcom);
									while (rs.next()) {
										String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t"+ "\t" + rs.getString(6) 
												+ "\n";
										result.append(str);
									}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								cdbs.setVisible(true);
								delcom.setVisible(false);
							}
						}
					});
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
						cdbs.setVisible(true);
						delcom.setVisible(false);
						}
					});
				}else if(table == "user") {
					cdbs.setVisible(false);
					JPanel delus = new JPanel();
					JLabel dele = new JLabel("삭제할 이용자의 면허번호를 입력해주세요 ");
					JTextField tf = new JTextField("");
					JButton okdel = new JButton("삭제");
					JButton cancel = new JButton("돌아가기");
					
					add("East",delus);
					delus.setPreferredSize(new Dimension(250, 340));
					delus.setVisible(true);
					delus.setLayout(new GridLayout(4,1));
					
					delus.add(dele);
					delus.add(tf);
					delus.add(okdel);
					delus.add(cancel);
					
					okdel.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String licenum = null;
							try {
							licenum = tf.getText();
						}catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("오류발생");
						}
						String delSQL = "DELETE FROM company WHERE licenum = ?";
						
							try {
								ps = con.prepareStatement(delSQL);
								ps.setString(1, licenum);
								ps.executeUpdate();
								
								
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}finally {
								JOptionPane.showMessageDialog(null, "삭제완료!");
								String showusr = "SELECT * FROM user";
								result.setText("");
								result.setText("LICENUM	NAME	ADDRESS	PHONE	MAIL\n");
								try {
									rs = stmt.executeQuery(showusr);
									while (rs.next()) {
										String str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5)
												+ "\n";
										result.append(str);
									}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								cdbs.setVisible(true);
								delus.setVisible(false);
							}
						}
					});
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
						cdbs.setVisible(true);
						delus.setVisible(false);
						}
					});
				}else if (table == "repairshop") {
					cdbs.setVisible(false);
					JPanel delrs = new JPanel();
					JLabel dele = new JLabel("삭제할 정비소 id를 입력해주세요 ");
					JTextField tf = new JTextField("");
					JButton okdel = new JButton("삭제");
					JButton cancel = new JButton("돌아가기");
					
					add("East",delrs);
					delrs.setPreferredSize(new Dimension(250, 340));
					delrs.setVisible(true);
					delrs.setLayout(new GridLayout(4,1));
					
					delrs.add(dele);
					delrs.add(tf);
					delrs.add(okdel);
					delrs.add(cancel);
					
					okdel.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							int rsid = 0;
							try {
							rsid = Integer.parseInt(tf.getText());
						}catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							System.out.println("오류발생");
						}
						String delSQL = "DELETE FROM repairshop WHERE rsid = ?";
						
							try {
								ps = con.prepareStatement(delSQL);
								ps.setInt(1, rsid);
								ps.executeUpdate();
								JOptionPane.showMessageDialog(null, "삭제완료!");
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}finally {
								
								String showrs = "SELECT * FROM repairshop";
								result.setText("");
								result.setText("SHOPID	NAME	ADDRESS	PHONE	MANAGER	MANAGERMAIL\n");
								
								try {
									rs = stmt.executeQuery(showrs);
									while (rs.next()) {
										String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6)
												+ "\n";
										result.append(str);
									}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								cdbs.setVisible(true);
								delrs.setVisible(false);
							}
						}
					});
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
						cdbs.setVisible(true);
						delrs.setVisible(false);
						}
					});
				}
			}
		});
		
		cdb2.add(goback);
		
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String table = combo.getSelectedItem().toString();
				if(table == "") {
					JOptionPane.showMessageDialog(null, "입력할 데이터베이스를 골라주세요!", "오류", JOptionPane.ERROR_MESSAGE);
				}else if(table == "campingcar") {
					cdbs.setVisible(false);
					JPanel upcar = new JPanel();
					JLabel dele = new JLabel("수정할 캠핑카의 id를 입력해주세요 ");
					JTextField tf = new JTextField("");
					JButton okup = new JButton("수정");
					JButton cancel = new JButton("돌아가기");
					
					add("East",upcar);
					upcar.setPreferredSize(new Dimension(250, 340));
					upcar.setVisible(true);
					upcar.setLayout(new GridLayout(4,1));
					
					upcar.add(dele);
					upcar.add(tf);
					upcar.add(okup);
					upcar.add(cancel);
					
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
						cdbs.setVisible(true);
						upcar.setVisible(false);
						}
					});
					
				
					
				}
			}
			
		});
		
		goback.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cdb0.setVisible(false);
				cdb2.setVisible(false);
				cdbs.setVisible(false);
				cdb1.setVisible(false);
				pmadmin1.setVisible(true);
				pmadmin2.setVisible(true);
			}
			
		});
	
	}
	
	public void selectUsr() {
		searchcar = new JButton("차량 검색");
		rentcar = new JButton("렌탈 신청");
		JButton goback = new JButton("돌아가기");
		pmusr1 = new JPanel();
		pmusr2 = new JPanel();
		
		
		
		add(pmusr1);
		add("South", pmusr2);
		
		pmusr1.setVisible(true);
		pmusr1.setLayout(new GridLayout(1,2));
		
		pmusr1.add(searchcar);
		pmusr1.add(rentcar);
		
		searchcar.addActionListener(this);
		rentcar.addActionListener(this);
		
		pmusr2.setVisible(true);
		pmusr2.add(goback);
		
		goback.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pmusr1.setVisible(false);
				pmusr2.setVisible(false);
				mtitle.setVisible(true);
			}
			
		});
	}
	
	public void searchUsr() {
		srhusr1 = new JPanel();
		srhusr2 = new JPanel();
		JLabel start = new JLabel("시작일 (YYYY-MM-DD)", JLabel.CENTER);
		JLabel period = new JLabel("대여기간(일)", JLabel.CENTER);
		
		JTextField tf2 = new JTextField("");
		JTextField tf3 = new JTextField("");
		
		JButton oksearch = new JButton("검색");
		JButton goback = new JButton("돌아가기");
		
		JTextArea result = new JTextArea();
		add("North", srhusr1);
		srhusr1.setVisible(true);
		add("South", srhusr2);
		srhusr2.setVisible(true);
		
		srhusr1.setLayout(new GridLayout(2,7));
		srhusr1.add(start);
		srhusr1.add(tf2);
		srhusr1.add(oksearch);
		srhusr1.add(period);
		srhusr1.add(tf3);
		
		srhusr2.add(goback);
		
		result.setEditable(false);
		scrollPane = new JScrollPane(result);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add("Center", scrollPane);
		scrollPane.setVisible(true);
		
		oksearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String startdate = null;
				int interval = 0;
					try {
						stmt = con.createStatement();
						startdate = tf2.getText();
						interval = Integer.parseInt(tf3.getText());
						
						
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
		}

	});
		
		goback.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				srhusr1.setVisible(false);
				srhusr2.setVisible(false);
				scrollPane.setVisible(false);
				pmusr1.setVisible(true);
				pmusr2.setVisible(true);
			}
			
		});
	}
	public void initData() {
		try {
			String setdrop1 = "SET foreign_key_checks = 0";
			String dropcar = "DROP TABLE IF EXISTS campingcar";
			String dropcomp = "DROP TABLE IF EXISTS company";
			String dropusr = "DROP TABLE IF EXISTS user";
			String droprent = "DROP TABLE IF EXISTS rentlist";
			String dropcheck = "DROP TABLE IF EXISTS checklist";
			String droprepair = "DROP TABLE IF EXISTS repairlist";
			String droprepairshop = "DROP TABLE IF EXISTS repairshop";
			String setdrop2 = "SET foreign_key_checks = 1";
			
			stmt = con.createStatement();
			
			stmt.execute(setdrop1);
			stmt.execute(dropcar);
			stmt.execute(dropcomp);
			stmt.execute(droprent);
			stmt.execute(dropusr);
			stmt.execute(dropcheck);
			stmt.execute(droprepair);
			stmt.execute(droprepairshop);
			stmt.execute(setdrop2);
			System.out.println("테이블 삭제 완료");
			
			String createcomp = "create table company("
					+"comid integer PRIMARY KEY,"
					+"name varchar(40) NOT NULL,"
					+"address varchar(20),"
					+"phone varchar(20),"
					+"managername varchar(20),"
					+"managermail varchar(50))";
			stmt.execute(createcomp);
			
			String createcar = "create table campingcar("
					+"carid integer PRIMARY KEY,"
					+"carname varchar(20) NOT NULL,"
					+"carnum integer,"
					+"capacity integer,"
					+"madein varchar(20),"
					+"madeyear integer,"
					+"distance integer,"
					+"rentcharge integer,"
					+"comid integer,"
					+"date DATE,"
					+"FOREIGN KEY (comid) REFERENCES company(comid) ON DELETE SET NULL ON UPDATE CASCADE)";
			stmt.execute(createcar);
			
			String createusr = "create table user("
					+"licenum varchar(20) PRIMARY KEY,"
					+"custname varchar(20) NOT NULL,"
					+"custaddress varchar(20),"
					+"custphone varchar(20),"
					+"custmail varchar(50))";
			stmt.execute(createusr);
			
			String creatrent = "create table rentlist("
					+"rentid integer PRIMARY KEY,"
					+"carid integer,"
					+"licenum varchar(20),"
					+"comid integer,"
					+"startdate DATE,"
					+"rentperiod integer,"
					+ "charge integer,"
					+ "paydeadline DATE,"
					+"other varchar(40),"
					+ "othercharge integer,"
					+"FOREIGN KEY (carid) REFERENCES campingcar(carid) ON DELETE SET NULL ON UPDATE CASCADE,"
					+"FOREIGN KEY (licenum) REFERENCES user(licenum) ON DELETE SET NULL ON UPDATE CASCADE,"
					+"FOREIGN KEY (comid) REFERENCES company(comid) ON DELETE SET NULL ON UPDATE CASCADE)";
			stmt.execute(creatrent);
			
			String creatcheck = "create table checklist("
					+"rentid integer,"
					+"carid integer,"
					+ "frontexp varchar(50),"
					+ "leftexp varchar(50),"
					+ "rightexp varchar(50),"
					+ "backexp varchar(50),"
					+ "repairneed varchar(20),"
					+ "FOREIGN KEY (rentid) REFERENCES rentlist(rentid) ON DELETE SET NULL ON UPDATE CASCADE,"
					+ "FOREIGN KEY (carid) REFERENCES campingcar(carid) ON DELETE SET NULL ON UPDATE CASCADE)";
			stmt.execute(creatcheck);
			
			String createws = "create table repairshop("
					+"rsid integer PRIMARY KEY,"
					+"rsname varchar(40),"
					+"rsaddress varchar(40),"
					+"rsphone varchar(20),"
					+"rsmanager varchar(40),"
					+"rsmanagermail varchar(60))";
			stmt.execute(createws);
			
			String createrepair  = "create table repairlist("
					+"repairid integer PRIMARY KEY,"
					+"carid integer,"
					+"rsid integer,"
					+"comid integer,"
					+"licenum varchar(20),"
					+ "repairhistory varchar(60),"
					+ "repairdate DATE,"
					+ "repaircharge integer,"
					+ "repairdeadline DATE,"
					+ "otherepair varchar(60),"
					+ "FOREIGN KEY (carid) REFERENCES campingcar(carid) ON DELETE SET NULL ON UPDATE CASCADE,"
					+ "FOREIGN KEY (rsid) REFERENCES repairshop(rsid) ON DELETE SET NULL ON UPDATE CASCADE,"
					+ "FOREIGN KEY (comid) REFERENCES company(comid) ON DELETE SET NULL ON UPDATE CASCADE,"
					+ "FOREIGN KEY (licenum) REFERENCES user(licenum) ON DELETE SET NULL ON UPDATE CASCADE)";
			stmt.execute(createrepair);
			
			String addcompany1 = "INSERT INTO company VALUES (1, '안전제일', '서울', '02-1111-2222', '김철수', 'abc@abc.abc')";
			String addcompany2 = "INSERT INTO company VALUES (2, '좋은제일', '대전', '042-3333-4444', '박명수', 'def@def.def')";
			String addcompany3 = "INSERT INTO company VALUES (3, '안심카', '충북', '043-5555-6666', '윤희수', 'ghi@ghi.ghi')";
			String addcompany4 = "INSERT INTO company VALUES (4, '캠핑캠핑', '전남', '061-7777-8888', '김갑수', 'jkl@jkl.jkl')";
			String addcompany5 = "INSERT INTO company VALUES (5, '렛츠고', '경북', '054-9999-0000', '나응수', 'mno@mno.mno')";
			String addcompany6 = "INSERT INTO company VALUES (6, '렌트안심', '부산', '051-0000-1111', '최정수', 'pqr@pqr.pqr')";
			String addcompany7 = "INSERT INTO company VALUES (7, '캠프랜드', '대구', '053-2222-3333', '김록수', 'stu@stu.stu')";
			String addcompany8 = "INSERT INTO company VALUES (8, '캠프프렌드', '서울', '02-4444-5555', '장한수', 'vwx@vxw.vwx')";
			String addcompany9 = "INSERT INTO company VALUES (9, '펀카', '경기', '031-6666-7777', '이현수', 'yza@yza.yza')";
			String addcompany10 = "INSERT INTO company VALUES (10, '캠핑타워', '제주', '064-8888-9999', '민재수', 'bcd@bcd.bcd')";
			String addcompany11 = "INSERT INTO company VALUES (11, '패밀리프렌드', '울산', '052-0000-3333', '한민수', 'efg@efg.efg')";
			String addcompany12 = "INSERT INTO company VALUES (12, '해피패밀리', '서울', '02-4444-6666', '김이수', 'hij@hij.hij')";
			String addcompany13 = "INSERT INTO company VALUES (13, '캠프안전', '제주', '064-8888-3333', '박만수', 'klm@klm.klm')";
			String addcompany14 = "INSERT INTO company VALUES (14, '해피해피', '부산', '051-2222-9999', '권강수', 'nop@nop.nop')";
			String addcompany15 = "INSERT INTO company VALUES (15, '가족사랑', '광주', '06-26666-1111', '명반수', 'qrs@qrs.qrs')";
		
			stmt.executeUpdate(addcompany1);
			stmt.executeUpdate(addcompany2);
			stmt.executeUpdate(addcompany3);
			stmt.executeUpdate(addcompany4);
			stmt.executeUpdate(addcompany5);
			stmt.executeUpdate(addcompany6);
			stmt.executeUpdate(addcompany7);
			stmt.executeUpdate(addcompany8);
			stmt.executeUpdate(addcompany9);
			stmt.executeUpdate(addcompany10);
			stmt.executeUpdate(addcompany11);
			stmt.executeUpdate(addcompany12);
			stmt.executeUpdate(addcompany13);
			stmt.executeUpdate(addcompany14);
			stmt.executeUpdate(addcompany15);
			
			String addcar1 = "INSERT INTO campingcar VALUES(1, '쏘나타', 6767, 4, '현대', 2005, 1200000, 50000, 1, '2018-10-20')";
			String addcar2 = "INSERT INTO campingcar VALUES(2, '티볼리', 8932, 8, '쌍용', 2018, 500000, 180000, 2, '2019-11-12')";
			String addcar3 = "INSERT INTO campingcar VALUES(3, '비싸요', 9999, 6, '기아', 2020, 120000, 320000, 3, '2020-02-17')";
			String addcar4 = "INSERT INTO campingcar VALUES(4, '가나다', 4939, 4, '삼성', 2010, 650000, 60000, 4, '2018-04-20')";
			String addcar5 = "INSERT INTO campingcar VALUES(5, '라마바', 2098, 8, '기아', 2011, 320000, 80000, 6, '2016-10-24')";
			String addcar6 = "INSERT INTO campingcar VALUES(6, '사아자', 1423, 12, '현대', 2018, 870000, 90000, 9, '2020-09-20')";
			String addcar7 = "INSERT INTO campingcar VALUES(7, '차타카', 8383, 10, '쌍용', 2020, 200000, 110000, 10, '2020-03-14')";
			String addcar8 = "INSERT INTO campingcar VALUES(8, '파하가', 0202, 6, '포드', 2014, 900000, 80000, 11, '2016-08-19')";
			String addcar9 = "INSERT INTO campingcar VALUES(9, '꽥꽥꽥', 2727, 8, '삼성', 2015, 400000, 30000, 14, '2017-02-13')";
			String addcar10 = "INSERT INTO campingcar VALUES(10, '짹짹짹', 5555, 10, '토요타', 2009, 300000, 90000, 15, '2010-02-16')";
			String addcar11 = "INSERT INTO campingcar VALUES(11, '왜애옹', 1111, 4, '쌍용', 2007, 1000000, 120000, 3, '2010-10-10')";
			String addcar12 = "INSERT INTO campingcar VALUES(12, '야아호', 2222, 12, '현대', 2012, 700000, 150000, 7, '2015-10-19')";
			String addcar13 = "INSERT INTO campingcar VALUES(13, '마우스', 3333, 8, 'bmw', 2018, 110000, 230000, 14, '2019-11-28')";
			String addcar14 = "INSERT INTO campingcar VALUES(14, '키보드', 4444, 10, '스바루', 2010, 300000, 190000, 8, '2016-05-04')";
			String addcar15 = "INSERT INTO campingcar VALUES(15, '모니터', 5555, 14, '스즈키', 2019, 1500000, 80000, 3, '2019-12-20')";
			String addcar16 = "INSERT INTO campingcar VALUES(16, '랄랄라', 6666, 8, '혼다', 2012, 2000000, 50000, 2, '2015-08-02')";
			String addcar17 = "INSERT INTO campingcar VALUES(17, '다다다', 7777, 6, '기아', 2004, 100000, 220000, 1, '2011-11-03')";
			String addcar18 = "INSERT INTO campingcar VALUES(18, '멍멍멍', 8888, 4, '삼성', 2002, 2000000, 90000, 9, '2018-12-19')";
			String addcar19 = "INSERT INTO campingcar VALUES(19, '냥냥냥', 9999, 2, '볼보', 2009, 800000, 150000, 11, '2016-04-15')";
			String addcar20 = "INSERT INTO campingcar VALUES(20, '왈왈왈', 7979, 10, '기아', 2011, 2100000, 40000, 12, '2013-03-21')";

			stmt.executeUpdate(addcar1);
			stmt.executeUpdate(addcar2);
			stmt.executeUpdate(addcar3);
			stmt.executeUpdate(addcar4);
			stmt.executeUpdate(addcar5);
			stmt.executeUpdate(addcar6);
			stmt.executeUpdate(addcar7);
			stmt.executeUpdate(addcar8);
			stmt.executeUpdate(addcar9);
			stmt.executeUpdate(addcar10);
			stmt.executeUpdate(addcar11);
			stmt.executeUpdate(addcar12);
			stmt.executeUpdate(addcar13);
			stmt.executeUpdate(addcar14);
			stmt.executeUpdate(addcar15);
			stmt.executeUpdate(addcar16);
			stmt.executeUpdate(addcar17);
			stmt.executeUpdate(addcar18);
			stmt.executeUpdate(addcar19);
			stmt.executeUpdate(addcar20);
			
			String addusr1 = "INSERT INTO user VALUES('11-000000-00', '김민지', '서울', '02-123-123', 'aaa@aaa.aaa')";
			String addusr2 = "INSERT INTO user VALUES('25-111111-11', '윤희지', '대전', '042-234-234', 'bbb@bbb.bbb')";
			String addusr3 = "INSERT INTO user VALUES('15-222222-22', '최예지', '충북', '043-345-345', 'ccc@ccc.ccc')";
			String addusr4 = "INSERT INTO user VALUES('13-444444-44', '정경지', '경기', '031-456-456', 'ddd@ddd.ddd')";
			String addusr5 = "INSERT INTO user VALUES('26-555555-55', '남유지', '울산', '052-567-567', 'eee@eee.eee')";
			String addusr6 = "INSERT INTO user VALUES('12-666666-66', '강반지', '부산', '051-678-678', 'fff@fff.fff')";
			String addusr7 = "INSERT INTO user VALUES('18-777777-77', '전강지', '전남', '061-789-789', 'ggg@ggg.ggg')";
			String addusr8 = "INSERT INTO user VALUES('25-888888-88', '최서지', '대전', '042-890-890', 'hhh@hhh.hhh')";
			String addusr9 = "INSERT INTO user VALUES('22-999999-99', '박현지', '대구', '053-141-414', 'iii@iii.iii')";
			String addusr10 = "INSERT INTO user VALUES('11-010101-01', '이태지', '서울', '02-252-525', 'jjj@jjj.jjj')";
			String addusr11 = "INSERT INTO user VALUES('13-232323-23', '최명지', '경기', '031-363-636', 'kkk@kkk.kkk')";
			String addusr12 = "INSERT INTO user VALUES('15-454545-45', '박명지', '충북', '043-474-747', 'lll@lll.lll')";
			String addusr13 = "INSERT INTO user VALUES('13-676767-67', '양예지', '경기', '031-585-858', 'mmm@mmm.mmm')";
			String addusr14 = "INSERT INTO user VALUES('12-898989-89', '이우지', '부산', '051-696-969', 'nnn@nnn.nnn')";
			String addusr15 = "INSERT INTO user VALUES('11-123123-43', '김영지', '서울', '02-030-303', 'ooo@ooo.ooo')";
			
			stmt.executeUpdate(addusr1);
			stmt.executeUpdate(addusr2);
			stmt.executeUpdate(addusr3);
			stmt.executeUpdate(addusr4);
			stmt.executeUpdate(addusr5);
			stmt.executeUpdate(addusr6);
			stmt.executeUpdate(addusr7);
			stmt.executeUpdate(addusr8);
			stmt.executeUpdate(addusr9);
			stmt.executeUpdate(addusr10);
			stmt.executeUpdate(addusr11);
			stmt.executeUpdate(addusr12);
			stmt.executeUpdate(addusr13);
			stmt.executeUpdate(addusr14);
			stmt.executeUpdate(addusr15);
			
			String addrent1 = "INSERT INTO rentlist VALUES(1, 3, '11-000000-00', 3, '2019-01-01', 3, 960000, '2018-12-31', '난로', 20000)";
			String addrent2 = "INSERT INTO rentlist VALUES(2, 10, '15-222222-22', 15, '2019-03-24', 5, 450000, '2019-03-23', 'x', 0)";
			String addrent3 = "INSERT INTO rentlist VALUES(3, 2, '12-898989-89', 2, '2019-04-13', 3, 540000, '2019-04-12', 'x', 0)";
			String addrent4 = "INSERT INTO rentlist VALUES(4, 7, '11-010101-01', 10, '2019-04-29', 5, 550000, '2019-04-28', 'x', 0)";
			String addrent5 = "INSERT INTO rentlist VALUES(5, 9, '26-555555-55', 14, '2019-05-01', 7, 210000, '2019-04-30', 'x', 0)";
			String addrent6 = "INSERT INTO rentlist VALUES(6, 11, '11-123123-43', 3, '2019-05-06', 1, 120000, '2019-05-05', 'x', 0)";
			String addrent7 = "INSERT INTO rentlist VALUES(7, 8, '25-111111-11', 11, '2019-08-21', 4, 790000, '2019-08-20', '이불', 30000)";
			String addrent8 = "INSERT INTO rentlist VALUES(8, 3, '11-000000-00', 3, '2019-03-18', 10, 3200000, '2019-03-17', 'x', 0)";
			String addrent9 = "INSERT INTO rentlist VALUES(9, 1, '18-777777-77', 1, '2019-03-30', 2, 100000, '2019-03-19', 'x', 0)";
			String addrent10 = "INSERT INTO rentlist VALUES(10, 6, '26-555555-55', 9, '2019-04-10', 5, 465000, '2019-04-09', '조리도구', 15000)";
			String addrent11 = "INSERT INTO rentlist VALUES(11, 8, '15-454545-45', 11, '2019-08-31', 14, 1120000, '2019-08-30', 'x', 0)";
			String addrent12 = "INSERT INTO rentlist VALUES(12, 10, '15-222222-22', 15, '2019-09-24', 10, 900000, '2019-09-23', 'x', 0)";
			String addrent13 = "INSERT INTO rentlist VALUES(13, 19, '13-676767-67', 11, '2019-11-13', 20, 3000000, '2019-11-12', 'x', 0)";
			String addrent14 = "INSERT INTO rentlist VALUES(14, 20, '12-898989-89', 12, '2019-12-24', 1, 40000, '2019-12-23', 'x', 0)";
			String addrent15 = "INSERT INTO rentlist VALUES(15, 15, '15-222222-22', 3, '2020-03-24', 5, 400000, '2020-03-23', 'x', 0)";
			String addrent16 = "INSERT INTO rentlist VALUES(16, 10, '25-888888-88', 15, '2020-03-28', 3, 270000, '2020-03-27', 'x', 0)";
			String addrent17 = "INSERT INTO rentlist VALUES(17, 1, '11-010101-01', 1, '2020-04-01', 10, 500000, '2020-03-31', '마스크', 50000)";
			String addrent18 = "INSERT INTO rentlist VALUES(18, 11, '11-123123-43', 3, '2020-04-13', 4, 480000, '2020-04-12', '마스크', 50000)";
			String addrent19 = "INSERT INTO rentlist VALUES(19, 20, '13-444444-44', 12, '2020-04-28', 2, 80000, '2020-04-27', '마스크', 50000)";
			String addrent20 = "INSERT INTO rentlist VALUES(20, 11, '12-666666-66', 3, '2020-05-03', 5, 600000, '2020-05-02', 'x', 0)";
			
			stmt.executeUpdate(addrent1);
			stmt.executeUpdate(addrent2);
			stmt.executeUpdate(addrent3);
			stmt.executeUpdate(addrent4);
			stmt.executeUpdate(addrent5);
			stmt.executeUpdate(addrent6);
			stmt.executeUpdate(addrent7);
			stmt.executeUpdate(addrent8);
			stmt.executeUpdate(addrent9);
			stmt.executeUpdate(addrent10);
			stmt.executeUpdate(addrent11);
			stmt.executeUpdate(addrent12);
			stmt.executeUpdate(addrent13);
			stmt.executeUpdate(addrent14);
			stmt.executeUpdate(addrent15);
			stmt.executeUpdate(addrent16);
			stmt.executeUpdate(addrent17);
			stmt.executeUpdate(addrent18);
			stmt.executeUpdate(addrent19);
			stmt.executeUpdate(addrent20);
			
			String addcheck1 = "INSERT INTO checklist VALUES(1, 3, '-', '-', '-', '문 고장', 'O')";
			String addcheck2 = "INSERT INTO checklist VALUES(2, 10, '-', '-', '-', '사다리 고장', 'O')";
			String addcheck3 = "INSERT INTO checklist VALUES(3, 2, '범퍼 손상', '-', '문 고장', '-', 'O')";
			String addcheck4 = "INSERT INTO checklist VALUES(4, 7, '-', '문 고장', '-', '도색 벗겨짐', 'O')";
			String addcheck5 = "INSERT INTO checklist VALUES(5, 9, '-', '유리창 파손', '-', '-', 'O')";
			String addcheck6 = "INSERT INTO checklist VALUES(6, 11, '와이퍼 고장', '-', '-', '-', 'O')";
			String addcheck7 = "INSERT INTO checklist VALUES(7, 8, '-', '-', '유리창 파손', '-', 'O')";
			String addcheck8 = "INSERT INTO checklist VALUES(8, 3, '-', '문 고장', '-', '-', 'O')";
			String addcheck9 = "INSERT INTO checklist VALUES(9, 1, '헤드라이트고장', '-', '-', '-', 'O')";
			String addcheck10 = "INSERT INTO checklist VALUES(10, 6, '-', '-', '-', '범퍼손상', 'O')";
			String addcheck11 = "INSERT INTO checklist VALUES(11, 8, '-', '-', '-', '경고등 고장', 'O')";
			String addcheck12 = "INSERT INTO checklist VALUES(12, 10, '헤드라이트고장', '-', '-', '-', 'O')";
			String addcheck13 = "INSERT INTO checklist VALUES(13, 19, '-', '-', '-', '후미등 고장', 'O')";
			String addcheck14 = "INSERT INTO checklist VALUES(14, 20, '범퍼손상', '-', '-', '범퍼손상', 'O')";
			String addcheck15 = "INSERT INTO checklist VALUES(15, 15, '-', '-', '유리창파손', '-', 'O')";
			String addcheck16 = "INSERT INTO checklist VALUES(16, 10, '-', '-', '-', '-', 'X')";
			String addcheck17 = "INSERT INTO checklist VALUES(17, 1, '-', '-', '-', '-', 'X')";
			String addcheck18 = "INSERT INTO checklist VALUES(18, 11, '-', '-', '-', '-', 'X')";
			String addcheck19 = "INSERT INTO checklist VALUES(19, 20, '-', '-', '-', '-', 'X')";
			String addcheck20 = "INSERT INTO checklist VALUES(20, 11, '-', '-', '-', '-', 'X')";
			
			stmt.executeUpdate(addcheck1);
			stmt.executeUpdate(addcheck2);
			stmt.executeUpdate(addcheck3);
			stmt.executeUpdate(addcheck4);
			stmt.executeUpdate(addcheck5);
			stmt.executeUpdate(addcheck6);
			stmt.executeUpdate(addcheck7);
			stmt.executeUpdate(addcheck8);
			stmt.executeUpdate(addcheck9);
			stmt.executeUpdate(addcheck10);
			stmt.executeUpdate(addcheck11);
			stmt.executeUpdate(addcheck12);
			stmt.executeUpdate(addcheck13);
			stmt.executeUpdate(addcheck14);
			stmt.executeUpdate(addcheck15);
			stmt.executeUpdate(addcheck16);
			stmt.executeUpdate(addcheck17);
			stmt.executeUpdate(addcheck18);
			stmt.executeUpdate(addcheck19);
			stmt.executeUpdate(addcheck20);
			
			
			
			String addrs1 = "INSERT INTO repairshop VALUES(1, '가가', '서울', '02-923-923', '강현민', 'ppp@ppp.ppp')";
			String addrs2 = "INSERT INTO repairshop VALUES(2, '나나', '대전', '042-894-894', '지장민', 'qqq@qqq.qqq')";
			String addrs3 = "INSERT INTO repairshop VALUES(3, '다다', '충북', '043-761-761', '윤동민', 'rrr@rrr.rrr')";
			String addrs4 = "INSERT INTO repairshop VALUES(4, '라라', '전남', '061-953-953', '김지민', 'sss@sss.sss')";
			String addrs5 = "INSERT INTO repairshop VALUES(5, '마마', '경북', '054-904-904', '박재민', 'ttt@ttt.ttt')";
			String addrs6 = "INSERT INTO repairshop VALUES(6, '바바', '대구', '053-289-289', '이용민', 'uuu@uuu.uuu')";
			String addrs7 = "INSERT INTO repairshop VALUES(7, '사사', '경기', '031-739-379', '최재민', 'vvv@vvv.vvv')";
			String addrs8 = "INSERT INTO repairshop VALUES(8, '아아', '제주', '064-831-821', '육상민', 'www@www.www')";
			String addrs9 = "INSERT INTO repairshop VALUES(9, '자자', '울산', '052-994-994', '오대민', 'xxx@xxx.xxx')";
			String addrs10 = "INSERT INTO repairshop VALUES(10, '차차', '부산', '051-772-772', '장나민', 'yyy@yyy.yyy')";
			String addrs11 = "INSERT INTO repairshop VALUES(11, '카카', '광주', '062-449-449', '한국민', 'zzz@zzz.zzz')";
			String addrs12 = "INSERT INTO repairshop VALUES(12, '타타', '서울', '02-881-881', '김해민', 'aabb@aabb.ab')";
			String addrs13 = "INSERT INTO repairshop VALUES(13, '파파', '경기', '031-043-043', '박정민', 'bbcc@bbcc.bc')";
			String addrs14 = "INSERT INTO repairshop VALUES(14, '하하', '제주', '064-886-886', '나수민', 'ccdd@ccdd.cd')";
			String addrs15 = "INSERT INTO repairshop VALUES(15, '쿠키', '대구', '053-775-775', '설유민', 'ddee@ddee.de')";
			
			stmt.executeUpdate(addrs1);
			stmt.executeUpdate(addrs2);
			stmt.executeUpdate(addrs3);
			stmt.executeUpdate(addrs4);
			stmt.executeUpdate(addrs5);
			stmt.executeUpdate(addrs6);
			stmt.executeUpdate(addrs7);
			stmt.executeUpdate(addrs8);
			stmt.executeUpdate(addrs9);
			stmt.executeUpdate(addrs10);
			stmt.executeUpdate(addrs11);
			stmt.executeUpdate(addrs12);
			stmt.executeUpdate(addrs13);
			stmt.executeUpdate(addrs14);
			stmt.executeUpdate(addrs15);
			
			
			String addrepair1 = "INSERT INTO repairlist VALUES(1, 3, 12, 3, '11-000000-00', '문 수리', '2019-01-07', 80000, '2019-02-07', 'X')";
			String addrepair2 = "INSERT INTO repairlist VALUES(2, 10, 11, 15, '15-222222-22', '사다리 수리', '2019-04-01', 40000, '2019-04-08', 'X')";
			String addrepair3 = "INSERT INTO repairlist VALUES(3, 2, 2, 2, '12-898989-89', '범퍼교체/문 수리', '2019-04-19', 280000, '2019-04-26', 'X')";
			String addrepair4 = "INSERT INTO repairlist VALUES(4, 7, 14, 10, '11-010101-01', '문 수리/도색', '2019-05-05', 130000, '2019-05-12', 'X')";
			String addrepair5 = "INSERT INTO repairlist VALUES(5, 9, 10, 14, '26-555555-55', '유리교체', '2019-05-10', 100000, '2019-05-17', 'X')";
			String addrepair6 = "INSERT INTO repairlist VALUES(6, 11, 1, 3, '11-123123-43', '와이퍼수리', '2019-05-08', 70000, '2019-05-15', 'X')";
			String addrepair7 = "INSERT INTO repairlist VALUES(7, 8, 9, 11, '25-111111-11', '유리교체', '2019-08-31', 100000, '2019-09-07', 'X')";
			String addrepair8 = "INSERT INTO repairlist VALUES(8, 3, 1,3, '11-000000-00', '문 수리', '2019-04-02', 80000, '2019-04-09', 'X')";
			String addrepair9 = "INSERT INTO repairlist VALUES(9, 1, 12,1, '18-777777-77', '헤드라이트 수리', '2019-04-03', 120000, '2019-04-10', 'X')";
			String addrepair10 = "INSERT INTO repairlist VALUES(10, 6, 7, 9, '26-555555-55', '범퍼 교체', '2019-04-20', 200000, '2019-04-27', 'X')";
			String addrepair11 = "INSERT INTO repairlist VALUES(11, 8, 9, 11, '15-454545-45', '경고등 수리', '2019-09-20', 120000, '2019-09-27', 'X')";
			String addrepair12 = "INSERT INTO repairlist VALUES(12, 10, 11, 15, '15-222222-22', '헤드라이트 수리', '2019-10-07', 120000, '2019-10-14', 'X')";
			String addrepair13 = "INSERT INTO repairlist VALUES(13, 19, 9, 11, '13-676767-67', '후미등 수리', '2019-12-08', 120000, '2019-12-15', 'X')";
			String addrepair14 = "INSERT INTO repairlist VALUES(14, 20, 1, 12, '12-898989-89', '범퍼교체/범퍼교체', '2019-12-28', 400000, '2020-01-05', 'X')";
			String addrepair15 = "INSERT INTO repairlist VALUES(15, 15, 1, 3, '15-222222-22', '유리창교체', '2020-04-02', 100000, '2020-04-09', 'X')";
			
			stmt.executeUpdate(addrepair1);
			stmt.executeUpdate(addrepair2);
			stmt.executeUpdate(addrepair3);
			stmt.executeUpdate(addrepair4);
			stmt.executeUpdate(addrepair5);
			stmt.executeUpdate(addrepair6);
			stmt.executeUpdate(addrepair7);
			stmt.executeUpdate(addrepair8);
			stmt.executeUpdate(addrepair9);
			stmt.executeUpdate(addrepair10);
			stmt.executeUpdate(addrepair11);
			stmt.executeUpdate(addrepair12);
			stmt.executeUpdate(addrepair13);
			stmt.executeUpdate(addrepair14);
			stmt.executeUpdate(addrepair15);
			
			JOptionPane.showMessageDialog(null, "초기화 완료!");
			
		} catch (SQLException e1) {
			System.out.println("쿼리 읽기 실패 :" + e1);
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAdmin) {
				mtitle.setVisible(false);
				selectAdmin();
			}else if(e.getSource()==btnUsr) {
				mtitle.setVisible(false);
				selectUsr();
			}else if(e.getSource()==InitDB) {
				initData();
			}else if(e.getSource()==searchcar) {
				pmusr1.setVisible(false);
				pmusr2.setVisible(false);
				searchUsr();
			}else if(e.getSource()==changedb) {
				pmadmin1.setVisible(false);
				pmadmin2.setVisible(false);
				changedb();
			}
	}
	
	public static void main(String[] args) {
		campingcar BLS = new campingcar();

		BLS.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					con.close();
				} catch (Exception e4) {
				}
				System.out.println("프로그램 완전 종료!");
				System.exit(0);
			}
		});
	}
	
	
}
