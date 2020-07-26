import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class hospital extends JFrame implements ActionListener {
	JPanel mainP, insrtm1, insrtm2, insrtd, insrtn, insrtp, insrtt, insrtc, slct1;
	JPanel slct2, slct3;
	JButton insertdata, select1, select2, select3;
	PreparedStatement ps = null;
	
	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	
	
	public hospital() {
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
		mainP = new JPanel();
		JButton resetdata = new JButton("초기화");
		insertdata = new JButton("입력");
		select1 = new JButton("검색1");
		select2 = new JButton("검색2");
		select3 = new JButton("검색3");
		
		add(mainP);
		mainP.setVisible(true);
		mainP.setLayout(new GridLayout(1,5));
		mainP.add(resetdata);
		mainP.add(insertdata);
		mainP.add(select1);
		mainP.add(select2);
		mainP.add(select3);
		
		resetdata.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initData();
			}
			
		});
		insertdata.addActionListener(this);
		select1.addActionListener(this);
		select2.addActionListener(this);
		select3.addActionListener(this);
	}
	public void initData() {
		try {
			String setdrop1 = "SET foreign_key_checks = 0";
			String dropdoc = "DROP TABLE IF EXISTS Doctors";
			String dropnurse = "DROP TABLE IF EXISTS Nurses";
			String droppat = "DROP TABLE IF EXISTS Patients";
			String droptreat = "DROP TABLE IF EXISTS Treatments";
			String dropchart = "DROP TABLE IF EXISTS Charts";
			String setdrop2 = "SET foreign_key_checks = 1";
			
			stmt = con.createStatement();
			
			stmt.execute(setdrop1);
			stmt.execute(dropdoc);
			stmt.execute(dropnurse);
			stmt.execute(droppat);
			stmt.execute(droptreat);
			stmt.execute(dropchart);
			stmt.execute(setdrop2);
			System.out.println("테이블 삭제 완료");
			
			
			String createdoc = "create table Doctors("
					+"doc_id INTEGER NOT NULL,"
					+"major_treat VARCHAR(25) NOT NULL,"
					+"doc_name VARCHAR(20) NOT NULL,"
					+"doc_gen char(1) NOT NULL,"
					+"doc_phone VARCHAR(15) NULL,"
					+"doc_email VARCHAR(50) UNIQUE,"
					+"doc_position VARCHAR(20) NOT NULL)";
			stmt.execute(createdoc);
			
			String docpri = "ALTER TABLE Doctors ADD CONSTRAINT doc_id_pk PRIMARY KEY (doc_id)";
			stmt.execute(docpri);
			
			String createnurs = "create table Nurses("
					+"nur_id INTEGER NOT NULL,"
					+"major_job VARCHAR(25) NOT NULL,"
					+"nur_name VARCHAR(20) NOT NULL,"
					+"nur_gen char(1) NOT NULL,"
					+"nur_phone VARCHAR(15) NULL,"
					+"nur_email VARCHAR(50) UNIQUE,"
					+"nur_position VARCHAR(20) NOT NULL)";
			stmt.execute(createnurs);
			
			String nurspri = "ALTER TABLE Nurses ADD CONSTRAINT nur_id_pk PRIMARY KEY (nur_id)";
			stmt.execute(nurspri);
			
			String createpat = "create table Patients("
					+"pat_id INTEGER NOT NULL,"
					+"nur_id INTEGER NOT NULL,"
					+"doc_id INTEGER NOT NULL,"
					+"pat_name VARCHAR(20) NOT NULL,"
					+"pat_gen char(1) NOT NULL,"
					+"pat_jumin VARCHAR(14) NOT NULL,"
					+"pat_addr VARCHAR(100) NOT NULL,"
					+"pat_phone VARCHAR(15) NULL,"
					+"pat_email VARCHAR(50) UNIQUE,"
					+"pat_job VARCHAR(20) NOT NULL)";
			stmt.execute(createpat);
			
			String patpri1 = "ALTER TABLE Patients ADD CONSTRAINT pat_id_pk PRIMARY KEY (pat_id)";
			stmt.execute(patpri1);
			String patpri2 = "ALTER TABLE Patients ADD (CONSTRAINT R_2 FOREIGN KEY (doc_id) REFERENCES Doctors (doc_id))";
			stmt.execute(patpri2);
			String patpri3 = "ALTER TABLE Patients ADD (CONSTRAINT R_3 FOREIGN KEY (nur_id) REFERENCES Nurses (nur_id))";
			stmt.execute(patpri3);
			
			
			String createtreat = "create table Treatments("
					+"treat_id INTEGER NOT NULL,"
					+"pat_id INTEGER NOT NULL,"
					+"doc_id INTEGER NOT NULL,"
					+"treat_contents VARCHAR(1000) NOT NULL,"
					+"treat_date DATE NOT NULL)";
			stmt.execute(createtreat);
			
			String treapri1 = "ALTER TABLE Treatments ADD CONSTRAINT treat_pat_doc_id_pk PRIMARY KEY (treat_id, pat_id, doc_id)";
			stmt.execute(treapri1);
			String treapri2 = "ALTER TABLE Treatments ADD (CONSTRAINT R_5 FOREIGN KEY (pat_id) REFERENCES Patients (pat_id))";
			stmt.execute(treapri2);
			String treapri3 = "ALTER TABLE Treatments ADD (CONSTRAINT R_6 FOREIGN KEY (doc_id) REFERENCES Doctors (doc_id))";
			stmt.execute(treapri3);
			
			String createchar = "CREATE TABLE Charts("
					+"chart_id VARCHAR(30) NOT NULL,"
					+"treat_id INTEGER NOT NULL,"
					+"doc_id INTEGER NOT NULL,"
					+"pat_id INTEGER NOT NULL,"
					+"nur_id INTEGER NOT NULL,"
					+"chart_contents VARCHAR(1000) NOT NULL)";
			stmt.execute(createchar);
			
			String charpri1 = "ALTER TABLE Charts ADD CONSTRAINT chart_treat_doc_pat_id_pk PRIMARY KEY (chart_id, treat_id, doc_id, pat_id)";
			stmt.execute(charpri1);
			String charpri2 = "ALTER TABLE Charts ADD (CONSTRAINT R_4 FOREIGN KEY (nur_id) REFERENCES Nurses (nur_id))";
			stmt.execute(charpri2);
			String charpri3 = "ALTER TABLE Charts ADD (CONSTRAINT R_7 FOREIGN KEY (treat_id, pat_id, doc_id) REFERENCES Treatments (treat_id, pat_id, doc_id))";
			stmt.execute(charpri3);
			
			String adddoc1 = "INSERT INTO Doctors VALUES (980312, '소아과', '이태정', 'M', '010-333-1340', 'ltj@hanbh.com', '과장')";
			String adddoc2 = "INSERT INTO Doctors VALUES (000601, '내과', '안성기', 'M', '011-222-0987', 'ask@hanbh.com', '과장')";
			String adddoc3 = "INSERT INTO Doctors VALUES (001208, '외과', '김민종', 'M', '010-333-8743', 'kmj@hanbh.com', '과장')";
			String adddoc4 = "INSERT INTO Doctors VALUES (020403, '피부과', '이태서', 'M', '019-777-3764', 'its@hanbh.com', '과장')";
			String adddoc5 = "INSERT INTO Doctors VALUES (050900, '소아과', '김연아', 'F', '010-555-3746', 'kya@hanbh.com', '전문의')";
			String adddoc6 = "INSERT INTO Doctors VALUES (050101, '내과', '차태현', 'M', '011-222-7643', 'cth@hanbh.com', '전문의')";
			String adddoc7 = "INSERT INTO Doctors VALUES (062019, '소아과', '전지현', 'F', '010-999-1265', 'jjh@hanbh.com', '전문의')";
			String adddoc8 = "INSERT INTO Doctors VALUES (070576, '피부과', '홍길동', 'M', '016-333-7263', 'hgd@hanbh.com', '전문의')";
			String adddoc9 = "INSERT INTO Doctors VALUES (080543, '방사선과', '유재석', 'M', '010-222-1263', 'yjs@hanbh.com', '과장')";
			String adddoc10 = "INSERT INTO Doctors VALUES (091001, '외과', '김병만', 'M', '010-555-3542', 'kbm@hanbh.com', '전문의')";
			String adddoc11 = "INSERT INTO Doctors VALUES (030429, '신경외과', '채송화', 'F', '010-777-9380', 'csh@hanbh.com', '과장')";
			String adddoc12 = "INSERT INTO Doctors VALUES (001367, '외과', '장도연', 'F', '010-232-8984', 'jdy@hanbh.com', '전문의')";
			String adddoc13 = "INSERT INTO Doctors VALUES (020833, '피부과', '방성훈', 'M', '010-444-9201', 'bsh@hanbh.com', '전문의')";
			String adddoc14 = "INSERT INTO Doctors VALUES (080822, '방사선과', '이효리', 'F', '010-920-1949', 'lhr@hanbh.com', '전문의')";
			String adddoc15 = "INSERT INTO Doctors VALUES (031923, '신경외과', '김세정', 'F', '010-291-9340', 'ksj@hanbh.com', '전문의')";
			String adddoc16 = "INSERT INTO Doctors VALUES (032291, '신경외과', '유시아', 'F', '010-545-1999', 'ysa2@hanbh.com', '전문의')";
			String adddoc17 = "INSERT INTO Doctors VALUES (051965, '내과', '배주현', 'F', '010-184-2947', 'bjh@hanbh.com', '전문의')";
			String adddoc18 = "INSERT INTO Doctors VALUES (052407, '내과', '옹성우', 'M', '010-944-0101', 'osw@hanbh.com', '전문의')";
			String adddoc19 = "INSERT INTO Doctors VALUES (064100, '소아과', '박혜리', 'F', '010-824-2285', 'phr@hanbh.com', '전문의')";
			String adddoc20 = "INSERT INTO Doctors VALUES (080936, '방사선과', '박나래', 'F', '017-223-9949', 'pnr@hanbh.com', '전문의')";
			
			stmt.executeUpdate(adddoc1);
			stmt.executeUpdate(adddoc2);
			stmt.executeUpdate(adddoc3);
			stmt.executeUpdate(adddoc4);
			stmt.executeUpdate(adddoc5);
			stmt.executeUpdate(adddoc6);
			stmt.executeUpdate(adddoc7);
			stmt.executeUpdate(adddoc8);
			stmt.executeUpdate(adddoc9);
			stmt.executeUpdate(adddoc10);
			stmt.executeUpdate(adddoc11);
			stmt.executeUpdate(adddoc12);
			stmt.executeUpdate(adddoc13);
			stmt.executeUpdate(adddoc14);
			stmt.executeUpdate(adddoc15);
			stmt.executeUpdate(adddoc16);
			stmt.executeUpdate(adddoc17);
			stmt.executeUpdate(adddoc18);
			stmt.executeUpdate(adddoc19);
			stmt.executeUpdate(adddoc20);
			
			
			String addnurs1 = "INSERT INTO Nurses VALUES (050302, '소아과', '김은영', 'F', '010-555-8751', 'key@hanbh.com', '수간호사')";
			String addnurs2 = "INSERT INTO Nurses VALUES (050021, '내과', '윤성애', 'F', '016-333-8745', 'ysa@hanbh.com', '수간호사')";
			String addnurs3 = "INSERT INTO Nurses VALUES (040089, '피부과', '신지원', 'M', '010-666-7646', 'sjw@hanbh.com', '주임')";
			String addnurs4 = "INSERT INTO Nurses VALUES (070605, '방사선과', '유정화', 'F', '010-333-4588', 'yjh@hanbh.com', '주임')";
			String addnurs5 = "INSERT INTO Nurses VALUES (070804, '내과', '라하나', 'F', '010-222-1340', 'nhn@hanbh.com', '주임')";
			String addnurs6 = "INSERT INTO Nurses VALUES (071018, '소아과', '김화경', 'F', '019-888-4116', 'khk@hanbh.com', '주임')";
			String addnurs7 = "INSERT INTO Nurses VALUES (100356, '소아과', '이선용', 'M', '010-777-1234', 'lsy@hanbh.com', '간호사')";
			String addnurs8 = "INSERT INTO Nurses VALUES (104145, '외과', '김현', 'M', '010-999-8520', 'kh@hanbh.com', '간호사')";
			String addnurs9 = "INSERT INTO Nurses VALUES (120309, '피부과', '박성완', 'M', '010-777-4996', 'psw@hanbh.com', '간호사')";
			String addnurs10 = "INSERT INTO Nurses VALUES (130211, '외과', '이서연', 'F', '010-222-3214', 'lsy2@hanbh.com', '간호사')";
			String addnurs11 = "INSERT INTO Nurses VALUES (070974, '방사선과', '손창민', 'M', '010-913-0445', 'sch@hanbh.com', '간호사')";
			String addnurs12 = "INSERT INTO Nurses VALUES (120582, '피부과', '정윤호', 'M', '010-583-2990', 'jyh@hanbh.com', '간호사')";
			String addnurs13 = "INSERT INTO Nurses VALUES (031840, '신경외과', '고현정', 'F', '010-884-4848', 'khj@hanbh.com', '수간호사')";
			String addnurs14 = "INSERT INTO Nurses VALUES (081003, '내과', '나영석', 'M', '010-201-5092', 'nys@hanbh.com', '간호사')";
			String addnurs15 = "INSERT INTO Nurses VALUES (085590, '외과', '이순재', 'M', '010-599-2020', 'lsj@hanbh.com', '간호사')";
			String addnurs16 = "INSERT INTO Nurses VALUES (122994, '피부과', '양동근', 'M', '010-686-3982', 'ydg@hanbh.com', '간호사')";
			String addnurs17 = "INSERT INTO Nurses VALUES (031926, '신경외과', '송은이', 'F', '010-121-5837', 'sei@hanbh.com', '주임')";
			String addnurs18 = "INSERT INTO Nurses VALUES (119388, '소아과', '임채무', 'M', '010-845-2948', 'lcm@hanbh.com', '간호사')";
			String addnurs19 = "INSERT INTO Nurses VALUES (079953, '방사선과', '이세영', 'F', '010-917-2898', 'lsy3@hanbh.com', '간호사')";
			String addnurs20 = "INSERT INTO Nurses VALUES (032044, '신경외과', '나응신', 'M', '010-010-2018', 'nes@hanbh.com', '간호사')";
			
			stmt.executeUpdate(addnurs1);
			stmt.executeUpdate(addnurs2);
			stmt.executeUpdate(addnurs3);
			stmt.executeUpdate(addnurs4);
			stmt.executeUpdate(addnurs5);
			stmt.executeUpdate(addnurs6);
			stmt.executeUpdate(addnurs7);
			stmt.executeUpdate(addnurs8);
			stmt.executeUpdate(addnurs9);
			stmt.executeUpdate(addnurs10);
			stmt.executeUpdate(addnurs11);
			stmt.executeUpdate(addnurs12);
			stmt.executeUpdate(addnurs13);
			stmt.executeUpdate(addnurs14);
			stmt.executeUpdate(addnurs15);
			stmt.executeUpdate(addnurs16);
			stmt.executeUpdate(addnurs17);
			stmt.executeUpdate(addnurs18);
			stmt.executeUpdate(addnurs19);
			stmt.executeUpdate(addnurs20);
			
			String addpat1 = "INSERT INTO Patients VALUES(2345, 050302, 980312, '안상건', 'M', 232345, '서울', '010-555-7845', 'ask@ab.com', '회사원')";
			String addpat2 = "INSERT INTO Patients VALUES(3545, 040089, 020403, '김성룡', 'M', 543545, '서울', '010-333-7812', 'ksr@bb.com', '자영업')";
			String addpat3 = "INSERT INTO Patients VALUES(3424, 070605, 080543, '이종진', 'M', 433424, '부산', '019-888-4859', 'ljj@ab.com', '회사원')";
			String addpat4 = "INSERT INTO Patients VALUES(7675, 100356, 050900, '최광석', 'M', 677675, '당진', '010-222-4847', 'cks@cc.com', '회사원')";
			String addpat5 = "INSERT INTO Patients VALUES(4533, 070804, 000601, '정한경', 'M', 744533, '강릉', '010-777-9630', 'jhk@ab.com', '교수')";
			String addpat6 = "INSERT INTO Patients VALUES(5546, 120309, 070576, '유원현', 'M', 765546, '대구', '016-777-0214', 'ywh@cc.com', '자영업')";
			String addpat7 = "INSERT INTO Patients VALUES(4543, 070804, 050101, '최재정', 'M', 454543, '부산', '010-555-4187', 'cjj@bb.com', '회사원')";
			String addpat8 = "INSERT INTO Patients VALUES(9768, 130211, 091001, '이진희', 'F', 119768, '서울', '010-888-3675', 'ljh@ab.com', '교수')";
			String addpat9 = "INSERT INTO Patients VALUES(4234, 130211, 091001, '오나미', 'F', 234234, '속초', '010-999-6541', 'onm@cc.com', '학생')";
			String addpat10 = "INSERT INTO Patients VALUES(7643, 071018, 062019, '송성묵', 'M', 987643, '서울', '010-222-5874', 'ssm@bb.com', '학생')";
			String addpat11 = "INSERT INTO Patients VALUES(8592, 070605, 080543, '박윤철', 'M', 392013, '서울', '010-345-8382', 'pyc@ab.com', '학생')";
			String addpat12 = "INSERT INTO Patients VALUES(8383, 120309, 020833, '김민규', 'M', 884923, '강릉', '010-123-6832', 'kmk@bb.com', '회사원')";
			String addpat13 = "INSERT INTO Patients VALUES(7270, 085590, 001367, '윤장수', 'M', 628421, '서울', '010-234-5905', 'yjs@ab.com', '자영업')";
			String addpat14 = "INSERT INTO Patients VALUES(9178, 119388, 064100, '이수만', 'M', 984032, '부산', '010-346-3968', 'lsm@bb.com', '학생')";
			String addpat15 = "INSERT INTO Patients VALUES(7357, 081003, 051965, '이재민', 'M', 589832, '대구', '010-456-2555', 'ljm@ab.com', '학생')";
			String addpat16 = "INSERT INTO Patients VALUES(6350, 031926, 030429, '강화채', 'M', 489210, '서울', '010-567-2858', 'khc@cc.com', '회사원')";
			String addpat17 = "INSERT INTO Patients VALUES(5656, 081003, 052407, '송지한', 'M', 220981, '대전', '010-678-6999', 'sjh@ab.com', '학생')";
			String addpat18 = "INSERT INTO Patients VALUES(2290, 031926, 032291, '임영무', 'M', 489821, '서울', '010-789-2090', 'yym@cc.com', '자영업')";
			String addpat19 = "INSERT INTO Patients VALUES(6648, 050302, 080822, '김현재', 'M', 258329, '대구', '010-022-6885', 'khj@ab.com', '회사원')";
			String addpat20 = "INSERT INTO Patients VALUES(7593, 031840, 030429, '김백찬', 'M', 874992, '부산', '010-342-2094', 'kbc@bb.com', '자영업')";	
			
			stmt.executeUpdate(addpat1);
			stmt.executeUpdate(addpat2);
			stmt.executeUpdate(addpat3);
			stmt.executeUpdate(addpat4);
			stmt.executeUpdate(addpat5);
			stmt.executeUpdate(addpat6);
			stmt.executeUpdate(addpat7);
			stmt.executeUpdate(addpat8);
			stmt.executeUpdate(addpat9);
			stmt.executeUpdate(addpat10);
			stmt.executeUpdate(addpat11);
			stmt.executeUpdate(addpat12);
			stmt.executeUpdate(addpat13);
			stmt.executeUpdate(addpat14);
			stmt.executeUpdate(addpat15);
			stmt.executeUpdate(addpat16);
			stmt.executeUpdate(addpat17);
			stmt.executeUpdate(addpat18);
			stmt.executeUpdate(addpat19);
			stmt.executeUpdate(addpat20);
			
			
			String addtreat1 = "INSERT INTO Treatments VALUES(130516023, 2345, 980312, '감기, 몸살', '2013-05-16')";
			String addtreat2 = "INSERT INTO Treatments VALUES(130628100, 3545, 020403, '피부 트러블 치료', '2013-06-28')";
			String addtreat3 = "INSERT INTO Treatments VALUES(131205056, 3424, 080543, '목 디스크로 MRI 촬영', '2013-12-05')";
			String addtreat4 = "INSERT INTO Treatments VALUES(131218024, 7675, 050900, '중이염', '2013-12-18')";
			String addtreat5 = "INSERT INTO Treatments VALUES(131224012, 4533, 000601, '장염', '2013-12-24')";
			String addtreat6 = "INSERT INTO Treatments VALUES(140130013, 5546, 070576, '여드름 치료', '2014-01-03')";
			String addtreat7 = "INSERT INTO Treatments VALUES(140109026, 4543, 050101, '위염', '2014-01-09')";
			String addtreat8 = "INSERT INTO Treatments VALUES(140226102, 9768, 091001, '화상치료', '2014-02-26')";
			String addtreat9 = "INSERT INTO Treatments VALUES(140303003, 4234, 091001, '교통사고 외상치료', '2014-03-03')";
			String addtreat10 = "INSERT INTO Treatments VALUES(140308087, 7643, 062019, '장염', '2014-03-08')";
			String addtreat11 = "INSERT INTO Treatments VALUES(140816064, 8592, 080543, '간 종양', '2014-08-16')";
			String addtreat12 = "INSERT INTO Treatments VALUES(141225022, 8383, 020833, '여드름 치료', '2014-12-25')";
			String addtreat13 = "INSERT INTO Treatments VALUES(150416085, 7270, 001367, '외상치료', '2015-04-16')";
			String addtreat14 = "INSERT INTO Treatments VALUES(150922002, 9178, 064100, '중이염', '2015-09-22')";
			String addtreat15 = "INSERT INTO Treatments VALUES(160105048, 7357, 051965, '감기, 몸살', '2016-01-05')";
			String addtreat16 = "INSERT INTO Treatments VALUES(170222059, 6350, 030429, '뇌종양', '2017-02-22')";
			String addtreat17 = "INSERT INTO Treatments VALUES(180312029, 5656, 052407, '위염', '2018-03-12')";
			String addtreat18 = "INSERT INTO Treatments VALUES(190305050, 2290, 032291, '뇌출혈', '2019-03-05')";
			String addtreat19 = "INSERT INTO Treatments VALUES(191111042, 6648, 080822, '대장암', '2019-11-11')";
			String addtreat20 = "INSERT INTO Treatments VALUES(200524011, 7593, 030429, '추간판 탈출', '2020-05-24')";
			
			stmt.executeUpdate(addtreat1);
			stmt.executeUpdate(addtreat2);
			stmt.executeUpdate(addtreat3);
			stmt.executeUpdate(addtreat4);
			stmt.executeUpdate(addtreat5);
			stmt.executeUpdate(addtreat6);
			stmt.executeUpdate(addtreat7);
			stmt.executeUpdate(addtreat8);
			stmt.executeUpdate(addtreat9);
			stmt.executeUpdate(addtreat10);
			stmt.executeUpdate(addtreat11);
			stmt.executeUpdate(addtreat12);
			stmt.executeUpdate(addtreat13);
			stmt.executeUpdate(addtreat14);
			stmt.executeUpdate(addtreat15);
			stmt.executeUpdate(addtreat16);
			stmt.executeUpdate(addtreat17);
			stmt.executeUpdate(addtreat18);
			stmt.executeUpdate(addtreat19);
			stmt.executeUpdate(addtreat20);
			
		
			String addchart1 = "INSERT INTO Charts VALUES('C130516023', 130516023, 980312, 2345, 050302, '링겔 및 감기약 처방')";
			String addchart2 = "INSERT INTO Charts VALUES('C130628100', 130628100, 020403, 3545, 040089, '약 처방')";
			String addchart3 = "INSERT INTO Charts VALUES('C131205056', 131205056, 080543, 3424, 070605, 'MRI 촬영')";
			String addchart4 = "INSERT INTO Charts VALUES('C131218024', 131218024, 050900, 7675, 100356, '중이염약 처방')";
			String addchart5 = "INSERT INTO Charts VALUES('C131224012', 131224012, 000601, 4533, 070804, '장염약 처방')";
			String addchart6 = "INSERT INTO Charts VALUES('C140130013', 140130013, 070576, 5546, 120309, '압출 및 여드름약 처방')";
			String addchart7 = "INSERT INTO Charts VALUES('C140109026', 140109026, 050101, 4543, 070804, '위내시경')";
			String addchart8 = "INSERT INTO Charts VALUES('C140226102', 140226102, 091001, 9768, 130211, '화상연고 처방')";
			String addchart9 = "INSERT INTO Charts VALUES('C140303003', 140303003, 091001, 4234, 130211, '입원치료')";
			String addchart10 = "INSERT INTO Charts VALUES('C140308087', 140308087, 062019, 7643, 071018, '링겔 및 약 처방')";
			String addchart11 = "INSERT INTO Charts VALUES('C140816064', 140816064, 080543, 8592, 070605, '입원 및 조직검사')";
			String addchart12 = "INSERT INTO Charts VALUES('C141225022', 141225022, 020833, 8383, 120309, '압출 및 여드름약 처방')";
			String addchart13 = "INSERT INTO Charts VALUES('C150416085', 150416085, 001367, 7270, 085590, '드레싱 및 치료')";
			String addchart14 = "INSERT INTO Charts VALUES('C150922002', 150922002, 064100, 9178, 119388, '중이염약 처방')";
			String addchart15 = "INSERT INTO Charts VALUES('C160105048', 160105048, 051965, 7357, 081003, '주사 및 감기약 처방')";
			String addchart16 = "INSERT INTO Charts VALUES('C170222059', 170222059, 030429, 6350, 031926, '입원수술')";
			String addchart17 = "INSERT INTO Charts VALUES('C180312029', 180312029, 052407, 5656, 081003, '위염약 처방')";
			String addchart18 = "INSERT INTO Charts VALUES('C190305050', 190305050, 032291, 2290, 031926, '입원수술')";
			String addchart19 = "INSERT INTO Charts VALUES('C191111042', 191111042, 080822, 6648, 050302, '입원수술')";
			String addchart20 = "INSERT INTO Charts VALUES('C200524011', 200524011, 030429, 7593, 031840, '입원수술')";
			
			stmt.executeUpdate(addchart1);
			stmt.executeUpdate(addchart2);
			stmt.executeUpdate(addchart3);
			stmt.executeUpdate(addchart4);
			stmt.executeUpdate(addchart5);
			stmt.executeUpdate(addchart6);
			stmt.executeUpdate(addchart7);
			stmt.executeUpdate(addchart8);
			stmt.executeUpdate(addchart9);
			stmt.executeUpdate(addchart10);
			stmt.executeUpdate(addchart11);
			stmt.executeUpdate(addchart12);
			stmt.executeUpdate(addchart13);
			stmt.executeUpdate(addchart14);
			stmt.executeUpdate(addchart15);
			stmt.executeUpdate(addchart16);
			stmt.executeUpdate(addchart17);
			stmt.executeUpdate(addchart18);
			stmt.executeUpdate(addchart19);
			stmt.executeUpdate(addchart20);
		
			JOptionPane.showMessageDialog(null,"초기화 완료!");
			}catch(SQLException e1) {
				System.out.println("쿼리읽기 실패 : "+e1);
			}
		}
	
	public void slctone() {
		slct1 = new JPanel();
		JButton goback = new JButton("돌아가기");
		JTextArea result = new JTextArea();
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("데이터베이스를 선택해주세요");
		combo.addItem("Doctors");
		combo.addItem("Nurses");
		combo.addItem("Patients");
		combo.addItem("Treatments");
		combo.addItem("Charts");
		
		add(slct1);
		slct1.setLayout(new BorderLayout());
		slct1.setVisible(true);
		slct1.add(combo, BorderLayout.NORTH);
		slct1.add(goback, BorderLayout.SOUTH);
		result.setEditable(false);
		JScrollPane scroll = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		slct1.add(scroll, BorderLayout.CENTER);
		
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String table = combo.getSelectedItem().toString();
				try {
					stmt = con.createStatement();
					if(table == "데이터베이스를 선택해주세요") {
					JOptionPane.showMessageDialog(null, "데이터베이스를 골라주세요!", "오류", JOptionPane.ERROR_MESSAGE);
					}else if(table == "Doctors") {
						String showdoc = "SELECT * FROM Doctors";
						result.setText("");
						result.setText("의사Id	담당진료과목	성명	성별	전화번호	이메일		직급\n");
						rs = stmt.executeQuery(showdoc);
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t"  + rs.getString(7) 
									+ "\n";
							result.append(str);
						}
					}else if(table == "Nurses") {
						String shownur = "SELECT * FROM Nurses";
						result.setText("");
						result.setText("간호사ID	담당업무	성명	성별	전화번호	이메일		직급\n");
						rs = stmt.executeQuery(shownur);
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) +
									"\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) 
									+ "\n";
							result.append(str);
						}
					}else if(table == "Patients") {
						String showpat = "SELECT * FROM Patients";
						result.setText("");
						result.setText("환자ID	간호사ID	의사ID	환자이름	환자성별	주민번호	주소	전화번호	이메일	직업\n");
						rs = stmt.executeQuery(showpat);
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5)
							+ "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8)
							+ "\t" + rs.getString(9) + "\t" + rs.getString(10)
									+ "\n";
							result.append(str);
						}
					}else if(table == "Treatments") {
						String showtreat = "SELECT * FROM Treatments";
						result.setText("");
						result.setText("진료ID	환자ID	의사ID	진료내용		진료날짜\n");
						rs = stmt.executeQuery(showtreat);
						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t" + "\t" + rs.getString(5)
									+ "\n";
							result.append(str);
						}
					}else if(table == "Charts") {
						String showchart = "SELECT * FROM Charts";
						result.setText("");
						result.setText("차트번호	진료ID	의사ID	환자ID	간호사ID	차트내용\n");
						rs = stmt.executeQuery(showchart);
						while (rs.next()) {
							String str = rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getInt(5) + "\t" + rs.getString(6)
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
		goback.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				slct1.setVisible(false);
				mainP.setVisible(true);
			}
		});
	}
	
	public void slctwo() {
		slct2 = new JPanel();
		JButton goback2 = new JButton("돌아가기");
		JTextArea result2 = new JTextArea();
		JLabel infoo = new JLabel("처방을 선택해서 해당 처방을 내린 진료과목과 그 횟수를 검색");
		JComboBox<String> combo2 = new JComboBox<String>();
		combo2.addItem("처방을 선택해주세요");
		combo2.addItem("약");
		combo2.addItem("수술");
		combo2.addItem("입원");
		add(slct2);
		slct2.setLayout(new GridLayout(4,1));
		slct2.setVisible(true);
		slct2.add(infoo);
		slct2.add(combo2);
		result2.setEditable(false);
		JScrollPane scroll2 = new JScrollPane(result2);
		slct2.add(scroll2, BorderLayout.CENTER);
		slct2.add(goback2);
		combo2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String table = combo2.getSelectedItem().toString();
				try {
					stmt = con.createStatement();
					if(table == "처방을 선택해주세요") {
					JOptionPane.showMessageDialog(null, "처방을 골라주세요!", "오류", JOptionPane.ERROR_MESSAGE);
					}else if(table == "약") {
						String slctmq = "select major_treat, count(*) as 횟수 from doctors where doc_id in (select doc_id from charts where chart_contents LIKE '%약%') group by major_treat";
						result2.setText("");
						result2.setText("major_treat	횟수\n");
						rs = stmt.executeQuery(slctmq);
						while (rs.next()) {
							String str = rs.getString(1) + "\t" +rs.getInt(2)
									+ "\n";
							result2.append(str);
						}
					}else if(table == "수술") {
						String slctsq = "select major_treat, count(*) as 횟수 from doctors where doc_id in (select doc_id from charts where chart_contents LIKE '%수술%') group by major_treat";
						result2.setText("");
						result2.setText("major_treat	횟수\n");
						rs = stmt.executeQuery(slctsq);
						while (rs.next()) {
							String str = rs.getString(1) + "\t" +rs.getInt(2)
									+ "\n";
							result2.append(str);
						}
					}else if(table == "입원") {
						String slctiq = "select major_treat, count(*) as 횟수 from doctors where doc_id in (select doc_id from charts where chart_contents LIKE '%입원%') group by major_treat";
						result2.setText("");
						result2.setText("major_treat	횟수\n");
						rs = stmt.executeQuery(slctiq);
						while (rs.next()) {
							String str = rs.getString(1) + "\t" +rs.getInt(2)
									+ "\n";
							result2.append(str);
						}
					}
				}catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				}
			}});
		goback2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				slct2.setVisible(false);
				mainP.setVisible(true);
			}
		});
	}
	public void slctree() {
		slct3 = new JPanel();
		JButton goback3 = new JButton("돌아가기");
		JTextArea result3 = new JTextArea();
		JLabel info3 = new JLabel("김병만 의사와 이서연 간호사가 함께 진료한 횟수는?");
		add(slct3);
		slct3.setLayout(new BorderLayout());
		slct3.setVisible(true);
		result3.setEditable(false);
		slct3.add(info3, BorderLayout.NORTH);
		slct3.add(result3, BorderLayout.CENTER);
		slct3.add(goback3, BorderLayout.SOUTH);
		
		String slctaq = "select doc_name, nur_name, count(*) 횟수\r\n" + 
				"from doctors d, nurses n, charts c\r\n" + 
				"where d.doc_id = c.doc_id and n.nur_id = c.nur_id and c.doc_id in(select doc_id	from doctors where doc_name = '김병만') and c.nur_id in(select nur_id from nurses	where nur_name = '이서연') \r\n" + 
				"group by c.nur_id;";
		result3.setText("");
		result3.setText("의사 이름	간호사 이름	횟수\n");
		try {
			rs = stmt.executeQuery(slctaq);
			while (rs.next()) {
				String str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3)
						+ "\n";
				result3.append(str);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		goback3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				slct3.setVisible(false);
				mainP.setVisible(true);
			}
		});
		
	}
	public void insert() {
		insrtm1 = new JPanel();
		insrtm2 = new JPanel();
		insrtd = new JPanel();
		insrtn = new JPanel();
		insrtp = new JPanel();
		insrtt = new JPanel();
		insrtc = new JPanel();
		
		setLayout(new BorderLayout());
		
		JButton backins = new JButton("돌아가기");
		JLabel addinfo = new JLabel("입력할 데이터베이스를 고르세요");
		JComboBox<String> comboi = new JComboBox<String>();
		comboi.addItem("");
		comboi.addItem("Doctors");
		comboi.addItem("Nurses");
		comboi.addItem("Patients");
		comboi.addItem("Treatments");
		comboi.addItem("Charts");
		
		add(insrtm1, BorderLayout.NORTH);
		insrtm1.setVisible(true);
		add(insrtm2, BorderLayout.CENTER);
		insrtm2.setVisible(true);
		
		insrtm1.add(addinfo);
		insrtm1.add(comboi);
		insrtm1.add(backins);
		
		insrtd.setLayout(new GridLayout(8,2));
		JButton docinsrt = new JButton("입력");
		JTextField dtf1 = new JTextField("");
		JTextField dtf2 = new JTextField("");
		JTextField dtf3 = new JTextField("");
		JTextField dtf4 = new JTextField("");
		JTextField dtf5 = new JTextField("");
		JTextField dtf6 = new JTextField("");
		JTextField dtf7 = new JTextField("");
		JLabel dl1 = new JLabel("의사ID : ", JLabel.CENTER);
		JLabel dl2 = new JLabel("담당진료과목 : ", JLabel.CENTER);
		JLabel dl3 = new JLabel("성명 : ", JLabel.CENTER);
		JLabel dl4 = new JLabel("성별 : ", JLabel.CENTER);
		JLabel dl5 = new JLabel("전화번호 : ", JLabel.CENTER);
		JLabel dl6 = new JLabel("이메일 : ", JLabel.CENTER);
		JLabel dl7 = new JLabel("직급 : ", JLabel.CENTER);
		
			
		insrtd.add(dl1);
		insrtd.add(dtf1);

		insrtd.add(dl2);
		insrtd.add(dtf2);

		insrtd.add(dl3);
		insrtd.add(dtf3);

		insrtd.add(dl4);
		insrtd.add(dtf4);
		
		insrtd.add(dl5);
		insrtd.add(dtf5);
		
		insrtd.add(dl6);
		insrtd.add(dtf6);
		
		insrtd.add(dl7);
		insrtd.add(dtf7);
		
		insrtd.add(docinsrt);
	
		docinsrt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int docid = 0;
				String mtreat = null;
				String dname = null;
				String dgen = null;
				String dphone = null;
				String dmail = null;
				String dpos = null;

				try {
					docid = Integer.parseInt(dtf1.getText());
					mtreat = dtf2.getText();
					dname = dtf3.getText();
					dgen = dtf4.getText();
					dphone = dtf5.getText();
					dmail = dtf6.getText();
					dpos = dtf7.getText();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println("오류발생");
				}
				String isrtdSQL = "INSERT INTO Doctors VALUES (?, ?, ?, ?, ?, ?, ?)";
				try {
					ps = con.prepareStatement(isrtdSQL);
					ps.setInt(1, docid);
					ps.setString(2, mtreat);
					ps.setString(3, dname);
					ps.setString(4,  dgen);
					ps.setString(5, dphone);
					ps.setString(6, dmail);
					ps.setString(7, dpos);
					int count  = ps.executeUpdate();
					
					if(count>0) {
						JOptionPane.showMessageDialog(null, "입력완료!");
						dtf1.setText("");
						dtf2.setText("");
						dtf3.setText("");
						dtf4.setText("");
						dtf5.setText("");
						dtf6.setText("");
						dtf7.setText("");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}//docinsrt performed
		});//docinsrt listener
		
		insrtn.setLayout(new GridLayout(8,2));
		JButton nurinsrt = new JButton("입력");
		JTextField ntf1 = new JTextField("");
		JTextField ntf2 = new JTextField("");
		JTextField ntf3 = new JTextField("");
		JTextField ntf4 = new JTextField("");
		JTextField ntf5 = new JTextField("");
		JTextField ntf6 = new JTextField("");
		JTextField ntf7 = new JTextField("");
		JLabel nl1 = new JLabel("간호사ID : ", JLabel.CENTER);
		JLabel nl2 = new JLabel("담당업무 : ", JLabel.CENTER);
		JLabel nl3 = new JLabel("성명 : ", JLabel.CENTER);
		JLabel nl4 = new JLabel("성별 : ", JLabel.CENTER);
		JLabel nl5 = new JLabel("전화번호 : ", JLabel.CENTER);
		JLabel nl6 = new JLabel("이메일 : ", JLabel.CENTER);
		JLabel nl7 = new JLabel("직급 : ", JLabel.CENTER);
		
		add(insrtn, BorderLayout.CENTER);
		
		insrtn.add(nl1);
		insrtn.add(ntf1);

		insrtn.add(nl2);
		insrtn.add(ntf2);

		insrtn.add(nl3);
		insrtn.add(ntf3);

		insrtn.add(nl4);
		insrtn.add(ntf4);
		
		insrtn.add(nl5);
		insrtn.add(ntf5);
		
		insrtn.add(nl6);
		insrtn.add(ntf6);
		
		insrtn.add(nl7);
		insrtn.add(ntf7);
		
		insrtn.add(nurinsrt);
		
		
		nurinsrt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int nurid = 0;
				String mjob = null;
				String nname = null;
				String ngen = null;
				String nphone = null;
				String nmail = null;
				String npos = null;

				try {
					nurid = Integer.parseInt(ntf1.getText());
					mjob = ntf2.getText();
					nname = ntf3.getText();
					ngen = ntf4.getText();
					nphone = ntf5.getText();
					nmail = ntf6.getText();
					npos = ntf7.getText();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println("오류발생");
				}//catch
				String isrtnSQL = "INSERT INTO Nurses VALUES (?, ?, ?, ?, ?, ?, ?)";
				try {
					ps = con.prepareStatement(isrtnSQL);
					ps.setInt(1, nurid);
					ps.setString(2, mjob);
					ps.setString(3, nname);
					ps.setString(4,  ngen);
					ps.setString(5, nphone);
					ps.setString(6, nmail);
					ps.setString(7, npos);
					int ncount  = ps.executeUpdate();
					
					if(ncount>0) {
						JOptionPane.showMessageDialog(null, "입력완료!");
						ntf1.setText("");
						ntf2.setText("");
						ntf3.setText("");
						ntf4.setText("");
						ntf5.setText("");
						ntf6.setText("");
						ntf7.setText("");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}//nurinsrt performed
		});//nurinsrt actionlistener
		
		insrtp.setLayout(new GridLayout(11,2));
		JButton patinsrt = new JButton("입력");
		JTextField ptf1 = new JTextField("");
		JTextField ptf2 = new JTextField("");
		JTextField ptf3 = new JTextField("");
		JTextField ptf4 = new JTextField("");
		JTextField ptf5 = new JTextField("");
		JTextField ptf6 = new JTextField("");
		JTextField ptf7 = new JTextField("");
		JTextField ptf8 = new JTextField("");
		JTextField ptf9 = new JTextField("");
		JTextField ptf10 = new JTextField("");
		JLabel pl1 = new JLabel("환자ID : ", JLabel.CENTER);
		JLabel pl2 = new JLabel("간호사ID : ", JLabel.CENTER);
		JLabel pl3 = new JLabel("의사ID : ", JLabel.CENTER);
		JLabel pl4 = new JLabel("환자성명 : ", JLabel.CENTER);
		JLabel pl5 = new JLabel("환자성별 : ", JLabel.CENTER);
		JLabel pl6 = new JLabel("주민번호 : ", JLabel.CENTER);
		JLabel pl7 = new JLabel("주소 : ", JLabel.CENTER);
		JLabel pl8 = new JLabel("전화번호 : ", JLabel.CENTER);
		JLabel pl9 = new JLabel("이메일 : ", JLabel.CENTER);
		JLabel pl10 = new JLabel("직업 : ", JLabel.CENTER);
							
		insrtp.add(pl1);
		insrtp.add(ptf1);

		insrtp.add(pl2);
		insrtp.add(ptf2);

		insrtp.add(pl3);
		insrtp.add(ptf3);

		insrtp.add(pl4);
		insrtp.add(ptf4);
		
		insrtp.add(pl5);
		insrtp.add(ptf5);
		
		insrtp.add(pl6);
		insrtp.add(ptf6);
		
		insrtp.add(pl7);
		insrtp.add(ptf7);
		
		insrtp.add(pl8);
		insrtp.add(ptf8);
		
		insrtp.add(pl9);
		insrtp.add(ptf9);
		
		insrtp.add(pl10);
		insrtp.add(ptf10);
		
		insrtp.add(patinsrt);
		
		
		patinsrt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int patid = 0;
				int nurid = 0;
				int docid = 0;
				String pname = null;
				String pgen = null;
				String pjumin = null;
				String paddr = null;
				String pphone = null;
				String pmail = null;
				String pjob = null;
				
				try {
					patid = Integer.parseInt(ptf1.getText());
					nurid = Integer.parseInt(ptf2.getText());
					docid = Integer.parseInt(ptf3.getText());
					pname = ptf4.getText();
					pgen = ptf5.getText();
					pjumin = ptf6.getText();
					paddr = ptf7.getText();
					pphone = ptf8.getText();
					pmail = ptf9.getText();
					pjob = ptf10.getText();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println("오류발생");
				}//catch
				String isrtpSQL = "INSERT INTO Patients VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try {
					ps = con.prepareStatement(isrtpSQL);
					ps.setInt(1, patid);
					ps.setInt(2, nurid);
					ps.setInt(3, docid);
					ps.setString(4, pname);
					ps.setString(5, pgen);
					ps.setString(6, pjumin);
					ps.setString(7, paddr);
					ps.setString(8, pphone);
					ps.setString(9, pmail);
					ps.setString(10, pjob);
					int pcount  = ps.executeUpdate();
					
					if(pcount>0) {
						JOptionPane.showMessageDialog(null, "입력완료!");
						ptf1.setText("");
						ptf2.setText("");
						ptf3.setText("");
						ptf4.setText("");
						ptf5.setText("");
						ptf6.setText("");
						ptf7.setText("");
						ptf8.setText("");
						ptf9.setText("");
						ptf10.setText("");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}//action performed
		});//patinsrt actionlistener
		
		insrtt.setLayout(new GridLayout(6,2));
		JButton treainsrt = new JButton("입력");
		JTextField ttf1 = new JTextField("");
		JTextField ttf2 = new JTextField("");
		JTextField ttf3 = new JTextField("");
		JTextField ttf4 = new JTextField("");
		JTextField ttf5 = new JTextField("");
		JLabel tl1 = new JLabel("진료ID : ", JLabel.CENTER);
		JLabel tl2 = new JLabel("환자ID : ", JLabel.CENTER);
		JLabel tl3 = new JLabel("의사ID : ", JLabel.CENTER);
		JLabel tl4 = new JLabel("진료내용 : ", JLabel.CENTER);
		JLabel tl5 = new JLabel("진료날짜(YYYY-MM-DD) : ", JLabel.CENTER);
		
		insrtt.add(tl1);
		insrtt.add(ttf1);

		insrtt.add(tl2);
		insrtt.add(ttf2);

		insrtt.add(tl3);
		insrtt.add(ttf3);

		insrtt.add(tl4);
		insrtt.add(ttf4);
		
		insrtt.add(tl5);
		insrtt.add(ttf5);
		
		insrtt.add(treainsrt);
		
		treainsrt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int treaid = 0;
				int patid = 0;
				int docid = 0;
				String tcontents = null;
				String tdate = null;
				
				try {
					treaid = Integer.parseInt(ttf1.getText());
					patid = Integer.parseInt(ttf2.getText());
					docid = Integer.parseInt(ttf3.getText());
					tcontents = ttf4.getText();
					tdate = ttf5.getText();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println("오류발생");
				}//catch
				String isrttSQL = "INSERT INTO Treatments VALUES (?, ?, ?, ?, ?)";
				try {
					ps = con.prepareStatement(isrttSQL);
					ps.setInt(1, treaid);
					ps.setInt(2, patid);
					ps.setInt(3, docid);
					ps.setString(4, tcontents);
					ps.setString(5, tdate);
					int tcount  = ps.executeUpdate();
					
					if(tcount>0) {
						JOptionPane.showMessageDialog(null, "입력완료!");
						ttf1.setText("");
						ttf2.setText("");
						ttf3.setText("");
						ttf4.setText("");
						ttf5.setText("");
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
				
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}//action performed
		});//treatinsrt actionlistener
		
		insrtc.setLayout(new GridLayout(7,2));
		JButton charinsrt = new JButton("입력");
		JTextField ctf1 = new JTextField("");
		JTextField ctf2 = new JTextField("");
		JTextField ctf3 = new JTextField("");
		JTextField ctf4 = new JTextField("");
		JTextField ctf5 = new JTextField("");
		JTextField ctf6 = new JTextField("");
		JLabel cl1 = new JLabel("차트번호 : ", JLabel.CENTER);
		JLabel cl2 = new JLabel("진료ID : ", JLabel.CENTER);
		JLabel cl3 = new JLabel("의사ID : ", JLabel.CENTER);
		JLabel cl4 = new JLabel("환자ID : ", JLabel.CENTER);
		JLabel cl5 = new JLabel("간호사ID : ", JLabel.CENTER);
		JLabel cl6 = new JLabel("차트내용 : ", JLabel.CENTER);
		
		insrtc.add(cl1);
		insrtc.add(ctf1);

		insrtc.add(cl2);
		insrtc.add(ctf2);

		insrtc.add(cl3);
		insrtc.add(ctf3);

		insrtc.add(cl4);
		insrtc.add(ctf4);
		
		insrtc.add(cl5);
		insrtc.add(ctf5);
		
		insrtc.add(cl6);
		insrtc.add(ctf6);
		
		insrtc.add(charinsrt);
		
		charinsrt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String charid = null;
				int treaid = 0;
				int docid = 0;
				int patid = 0;
				int nurid = 0;
				String ccontents = null;
				
				try {
					charid = ctf1.getText();
					treaid = Integer.parseInt(ctf2.getText());
					docid = Integer.parseInt(ctf3.getText());
					patid = Integer.parseInt(ctf4.getText());
					nurid = Integer.parseInt(ctf5.getText());
					ccontents = ctf6.getText();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					System.out.println("오류발생");
				}//catch
				String isrtcSQL = "INSERT INTO Charts VALUES (?, ?, ?, ?, ?, ?)";
				try {
					ps = con.prepareStatement(isrtcSQL);
					ps.setString(1, charid);
					ps.setInt(2, treaid);
					ps.setInt(3, docid);
					ps.setInt(4, patid);
					ps.setInt(5,  nurid);
					ps.setString(6, ccontents);
					int pcount  = ps.executeUpdate();
					
					if(pcount>0) {
						JOptionPane.showMessageDialog(null, "입력완료!");
						ctf1.setText("");
						ctf2.setText("");
						ctf3.setText("");
						ctf4.setText("");
						ctf5.setText("");
						ctf6.setText("");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}//action performed
		});//chartinsrt actionlistener
		
		comboi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String tabled = comboi.getSelectedItem().toString();
				try {
					stmt = con.createStatement();
					if(tabled == "") {
					JOptionPane.showMessageDialog(null, "데이터베이스를 골라주세요!", "오류", JOptionPane.ERROR_MESSAGE);
					}else if(tabled == "Doctors") {
						insrtm2.setVisible(false);
						insrtn.setVisible(false);
						insrtp.setVisible(false);
						insrtt.setVisible(false);
						insrtc.setVisible(false);
						
						add(insrtd, BorderLayout.CENTER);
						insrtd.setVisible(true);
						
					}//if doctor
					else if(tabled == "Nurses") {
						insrtm2.setVisible(false);
						insrtd.setVisible(false);
						insrtp.setVisible(false);
						insrtt.setVisible(false);
						insrtc.setVisible(false);
						
						add(insrtn, BorderLayout.CENTER);
						insrtn.setVisible(true);
						
					}//if nurses
					else if(tabled=="Patients") {
						insrtm2.setVisible(false);
						insrtd.setVisible(false);
						insrtn.setVisible(false);
						insrtt.setVisible(false);
						insrtc.setVisible(false);
						
						add(insrtp, BorderLayout.CENTER);
						insrtp.setVisible(true);
						
					}//if patients
					else if(tabled == "Treatments") {
						insrtm2.setVisible(false);
						insrtd.setVisible(false);
						insrtn.setVisible(false);
						insrtp.setVisible(false);
						insrtc.setVisible(false);
						
						add(insrtt, BorderLayout.CENTER);
						insrtt.setVisible(true);
						
					}//if treatments
					else if(tabled == "Charts") {
						insrtm2.setVisible(false);
						insrtd.setVisible(false);
						insrtn.setVisible(false);
						insrtp.setVisible(false);
						insrtt.setVisible(false);
						
						add(insrtc, BorderLayout.CENTER);
						insrtc.setVisible(true);
					}
				}catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//catch
			}//action performed
			});//comboi actionlistener		
		
		backins.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				insrtm1.setVisible(false);
				insrtm2.setVisible(false);
				insrtd.setVisible(false);
				insrtn.setVisible(false);
				insrtp.setVisible(false);
				insrtt.setVisible(false);
				insrtc.setVisible(false);
				mainP.setVisible(true);
			}
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == insertdata) {
			mainP.setVisible(false);
			insert();
			insrtm1.setVisible(true);
			insrtm2.setVisible(true);
			insrtd.setVisible(false);
			insrtn.setVisible(false);
			insrtp.setVisible(false);
			insrtt.setVisible(false);
			insrtc.setVisible(false);
		}else if(e.getSource()==select1) {
			mainP.setVisible(false);
			slctone();
		}else if(e.getSource()==select2) {
			mainP.setVisible(false);
			slctwo();
		}else if(e.getSource()==select3) {
			mainP.setVisible(false);
			slctree();
		}
	}
	
	public static void main(String[] args) {
		hospital BLS = new hospital();

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