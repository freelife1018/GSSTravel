package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DetailDAO implements IDetailDAO {
	private DataSource ds;

	public DetailDAO() {
		super();
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/testDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String tra_Count = "SELECT COUNT(*) AS count FROM Detail where tra_No =? and det_CanDate IS  NULL";
	private static final String insertDetail = "insert into Detail (emp_No,fam_No,tra_No,det_Date,det_CanDate,det_money) values(?,?,?,?,null,?)";
	private static final String detail_Count = "select count(f.fam_Name) as count from Detail d join Family f ON f.fam_No=d.fam_No where d.emp_No=? and d.tra_No=? and d.det_CanDate is null";
	private static final String detail_Emp_No = "select distinct emp_No,det_Date from Detail where tra_No=? and det_CanDate is null order by det_Date";
	private static final String detail_Enter = "select det_Date from Detail where emp_No=? and tra_No=? and det_CanDate is null order by det_Date";
	private static final String updateDet_CanDate = "update Detail set det_CanDate=? where emp_No=? and tra_No=? and det_CanDate is null";
	private static final String SELECT_BY_TRA_NO = "select d.tra_No , tra_Name ,t.tra_No, dept_No , e.emp_No , f.fam_No , emp_Name , emp_sub , fam_Name , det_money ,emp_subTra, det_note ,det_noteMoney from Detail d join Employee e on d.emp_No=e.emp_No left join Family f on d.fam_No = f.fam_No left join Travel t on t.tra_No=d.tra_No where d.tra_No=? and det_CanDate is null order by e.emp_No ,d.det_No";
	private static final String UPDATE_DETAIL_FOR_EMP_NO = "update Detail set det_note=? , det_noteMoney=? where emp_No=? and fam_No is null and tra_No=?";
	private static final String UPDATE_DETAIL_FOR_FAM_NO = "update Detail set det_note=? , det_noteMoney=? where fam_No=? and tra_No=?";
	private static final String selectFam_No = "select fam_No  from Detail where fam_No=? and tra_No=? and det_CanDate is null ";
	private static final String selectFam_Rel = "select f.fam_Rel as fam_Rel from Detail d join Family f on d.fam_No=f.fam_No where d.det_CanDate is null and tra_No=? and d.emp_No=?";
	private static final String SELECT_EXCEL = "SELECT det_No, Detail.emp_No, ISNULL(Detail.fam_No,Detail.emp_No) as number, ISNULL(fam_Rel,'員工') as Rel, ISNULL(fam_Name, emp_Name) as Name, ISNULL(fam_Sex,emp_Sex) as Sex, ISNULL(fam_ID, emp_ID) as ID,ISNULL(fam_Bdate,emp_Bdate) as Bdate, ISNULL(fam_Phone,emp_Phone) as Phone,ISNULL(fam_eat,emp_Eat) as Eat, ISNULL(fam_Car,1) as Car, fam_Bady, fam_kid, fam_Dis, fam_Mom,ISNULL(fam_Ben,emp_Ben) as Ben, ISNULL(fam_BenRel,emp_BenRel) as BenRel, ISNULL(fam_Emg,emp_Emg) as Emg, ISNULL(fam_EmgPhone,emp_EmgPhone) as EmgPhone, det_Date, det_CanDate as CanDate, ISNULL(fam_Note,emp_Note) as Note, det_canNote FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No WHERE Tra_No = ? order by CanDate";
	private static final String SELECT_EXCEL2 = "SELECT det_No, Detail.emp_No, ISNULL(Detail.fam_No,Detail.emp_No) as number, ISNULL(fam_Rel,'員工') as Rel, ISNULL(fam_Name, emp_Name) as Name, ISNULL(fam_Sex,emp_Sex) as Sex, ISNULL(fam_ID, emp_ID) as ID,ISNULL(fam_Bdate,emp_Bdate) as Bdate, ISNULL(fam_Phone,emp_Phone) as Phone,ISNULL(fam_eat,emp_Eat) as Eat, ISNULL(fam_Car,1) as Car, fam_Bady, fam_kid, fam_Dis, fam_Mom,ISNULL(fam_Ben,emp_Ben) as Ben, ISNULL(fam_BenRel,emp_BenRel) as BenRel, ISNULL(fam_Emg,emp_Emg) as Emg, ISNULL(fam_EmgPhone,emp_EmgPhone) as EmgPhone, det_Date, det_CanDate as CanDate, ISNULL(fam_Note,emp_Note) as Note, det_canNote FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No WHERE Tra_No = ? and det_CanDate is null order by CanDate";
	private static final String SELECT_EXCEL3 = "SELECT det_No, Detail.emp_No, ISNULL(Detail.fam_No,Detail.emp_No) as number, ISNULL(fam_Rel,'員工') as Rel, ISNULL(fam_Name, emp_Name) as Name, ISNULL(fam_Sex,emp_Sex) as Sex, ISNULL(fam_ID, emp_ID) as ID,ISNULL(fam_Bdate,emp_Bdate) as Bdate, ISNULL(fam_Phone,emp_Phone) as Phone,ISNULL(fam_eat,emp_Eat) as Eat, ISNULL(fam_Car,1) as Car, fam_Bady, fam_kid, fam_Dis, fam_Mom,ISNULL(fam_Ben,emp_Ben) as Ben, ISNULL(fam_BenRel,emp_BenRel) as BenRel, ISNULL(fam_Emg,emp_Emg) as Emg, ISNULL(fam_EmgPhone,emp_EmgPhone) as EmgPhone, det_Date, det_CanDate as CanDate, ISNULL(fam_Note,emp_Note) as Note, det_canNote FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No WHERE Tra_No = ? and det_CanDate is not null order by CanDate";
	private static final String selectALL = "select * from Detail where emp_No=? and fam_No is null order by det_Date desc";
	private static final String ckselect="select * from Detail where tra_No=?";
	
	public Boolean ckselect(String tra_No){
		Boolean bl = false;
		try(
			Connection conn=ds.getConnection();
			PreparedStatement stem=conn.prepareStatement(ckselect);
				){
			stem.setString(1, tra_No);
			ResultSet rest = stem.executeQuery();
			while(rest.next()){
				bl=true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bl;
	}
	
	public List<DetailVO> selectALL(String emp_No) {
		List<DetailVO> DetailVOs=new ArrayList<>();
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(selectALL);) {
			stmt.setString(1, emp_No);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				DetailVO vo=new DetailVO();
				vo.setTra_No(rset.getString("tra_NO"));
				vo.setDet_Date(rset.getTimestamp("det_Date"));
				vo.setDet_CanDate(rset.getTimestamp("det_CanDate"));
				vo.setDet_money(rset.getFloat("det_money"));
				DetailVOs.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DetailVOs;
	}
	
	public String detail(String emp_No, String tra_No) {
		String det_Date=null;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(detail_Enter);) {
			stmt.setString(1, emp_No);
			stmt.setString(2, tra_No);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				det_Date=rset.getString("det_Date");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return det_Date;
	}
	
	public List<DetailBean> selectExcel(String Tra_No) {
		List<DetailBean> result = new ArrayList<>();
		try {
			Connection conn = ds.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_EXCEL);
			stmt.setString(1, Tra_No);
			ResultSet rset = stmt.executeQuery();
			result = new ArrayList<DetailBean>();
			while (rset.next()) {
				DetailBean bean = new DetailBean();
				bean.setDet_No(rset.getInt("det_No"));
				bean.setEmp_No(rset.getInt("emp_No"));
				bean.setFam_No(rset.getInt("number"));
				bean.setRel(rset.getString("Rel"));
				bean.setName(rset.getString("Name"));
				bean.setSex(rset.getString("Sex"));
				bean.setID(rset.getString("ID"));
				bean.setBdate(rset.getDate("Bdate"));
				bean.setPhone(rset.getString("Phone"));
				bean.setEat(rset.getString("Eat"));
				bean.setCar(rset.getBoolean("Car"));
				bean.setFam_Bady(rset.getBoolean("fam_Bady"));
				bean.setFam_kid(rset.getBoolean("fam_kid"));
				bean.setFam_Dis(rset.getBoolean("fam_Dis"));
				bean.setFam_Mom(rset.getBoolean("fam_Mom"));
				bean.setBen(rset.getString("Ben"));
				bean.setBenRel(rset.getString("BenRel"));
				bean.setEmg(rset.getString("Emg"));
				bean.setEmgPhone(rset.getString("EmgPhone"));
				bean.setDet_Date(rset.getString("det_Date"));
				bean.setDet_CanDate(rset.getString("CanDate"));
				bean.setNote(rset.getString("Note"));
				bean.setDet_canNote(rset.getString("det_canNote"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<DetailBean> selectExcel2(String Tra_No) {
		List<DetailBean> result = new ArrayList<>();
		try {
			Connection conn = ds.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_EXCEL2);
			stmt.setString(1, Tra_No);
			ResultSet rset = stmt.executeQuery();
			result = new ArrayList<DetailBean>();
			while (rset.next()) {
				DetailBean bean = new DetailBean();
				bean.setDet_No(rset.getInt("det_No"));
				bean.setEmp_No(rset.getInt("emp_No"));
				bean.setFam_No(rset.getInt("number"));
				bean.setRel(rset.getString("Rel"));
				bean.setName(rset.getString("Name"));
				bean.setSex(rset.getString("Sex"));
				bean.setID(rset.getString("ID"));
				bean.setBdate(rset.getDate("Bdate"));
				bean.setPhone(rset.getString("Phone"));
				bean.setEat(rset.getString("Eat"));
				bean.setCar(rset.getBoolean("Car"));
				bean.setFam_Bady(rset.getBoolean("fam_Bady"));
				bean.setFam_kid(rset.getBoolean("fam_kid"));
				bean.setFam_Dis(rset.getBoolean("fam_Dis"));
				bean.setFam_Mom(rset.getBoolean("fam_Mom"));
				bean.setBen(rset.getString("Ben"));
				bean.setBenRel(rset.getString("BenRel"));
				bean.setEmg(rset.getString("Emg"));
				bean.setEmgPhone(rset.getString("EmgPhone"));
				bean.setDet_Date(rset.getString("det_Date"));
				bean.setDet_CanDate(rset.getString("CanDate"));
				bean.setNote(rset.getString("Note"));
				bean.setDet_canNote(rset.getString("det_canNote"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<DetailBean> selectExcel3(String Tra_No) {
		List<DetailBean> result = new ArrayList<>();
		try {
			Connection conn = ds.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_EXCEL3);
			stmt.setString(1, Tra_No);
			ResultSet rset = stmt.executeQuery();
			result = new ArrayList<DetailBean>();
			while (rset.next()) {
				DetailBean bean = new DetailBean();
				bean.setDet_No(rset.getInt("det_No"));
				bean.setEmp_No(rset.getInt("emp_No"));
				bean.setFam_No(rset.getInt("number"));
				bean.setRel(rset.getString("Rel"));
				bean.setName(rset.getString("Name"));
				bean.setSex(rset.getString("Sex"));
				bean.setID(rset.getString("ID"));
				bean.setBdate(rset.getDate("Bdate"));
				bean.setPhone(rset.getString("Phone"));
				bean.setEat(rset.getString("Eat"));
				bean.setCar(rset.getBoolean("Car"));
				bean.setFam_Bady(rset.getBoolean("fam_Bady"));
				bean.setFam_kid(rset.getBoolean("fam_kid"));
				bean.setFam_Dis(rset.getBoolean("fam_Dis"));
				bean.setFam_Mom(rset.getBoolean("fam_Mom"));
				bean.setBen(rset.getString("Ben"));
				bean.setBenRel(rset.getString("BenRel"));
				bean.setEmg(rset.getString("Emg"));
				bean.setEmgPhone(rset.getString("EmgPhone"));
				bean.setDet_Date(rset.getString("det_Date"));
				bean.setDet_CanDate(rset.getString("CanDate"));
				bean.setNote(rset.getString("Note"));
				bean.setDet_canNote(rset.getString("det_canNote"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<String> selectFam_Rel(int emp_No, long tra_No) {
		List<String> fam_Rels = new ArrayList<>();
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(selectFam_Rel);) {
			stmt.setLong(1, tra_No);
			stmt.setInt(2, emp_No);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				fam_Rels.add(rset.getString("fam_Rel"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fam_Rels;
	}

	@Override
	public boolean selectFam_No(int fam_No, long tra_No) {
		boolean bl = false;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(selectFam_No);) {
			stmt.setInt(1, fam_No);
			stmt.setLong(2, tra_No);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				bl = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bl;
	}

	@Override
	public int detail_Enter(String emp_No, String tra_No) {
		int a = 0;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(detail_Enter);) {
			stmt.setString(1, emp_No);
			stmt.setString(2, tra_No);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				a = 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public LinkedHashSet<String> detail_Emp_No(long tra_No) {
		LinkedHashSet<String> result = null;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(detail_Emp_No);) {
			result = new LinkedHashSet<>();
			stmt.setLong(1, tra_No);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				result.add(rset.getString("emp_No"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int detail_Count(String emp_No, long tra_No) {
		int count = 0;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(detail_Count);) {
			stmt.setString(1, emp_No);
			stmt.setLong(2, tra_No);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				count = rset.getInt("count");// 活動參加人數
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int tra_count(long tra_No) {
		int count = 0;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(tra_Count);) {
			stmt.setLong(1, tra_No);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				count = rset.getInt("count");// 活動參加人數
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Map<String, Integer> tra_count() {
		ITravelDAO travelDAO = new TravelDAO();
		List<String> allTra_No = travelDAO.selectTra_No();// 所有活動編號
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (String tra_No : allTra_No) {
			try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(tra_Count);) {
				stmt.setString(1, tra_No);
				ResultSet rset = stmt.executeQuery();
				while (rset.next()) {
					int count = rset.getInt("count");// 活動參加人數
					result.put(tra_No, count);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public void tra_Enter(int emp_No, String fam_No, String tra_No, String det_Date, float det_money) {
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(insertDetail);) {
			stmt.setInt(1, emp_No);
			stmt.setString(2, fam_No);
			stmt.setString(3, tra_No);
			stmt.setString(4, det_Date);
			stmt.setFloat(5, det_money);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateDet_CanDate(String emp_No, String tra_No) {
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateDet_CanDate);) {
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());// 現在系統時間
			stmt.setString(1, date);
			stmt.setString(2, emp_No);
			stmt.setString(3, tra_No);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// SELECT報名維護已取消欄位
	private static final String SELECTCan = "SELECT * FROM(SELECT TOP 100 PERCENT det_No, Detail.emp_No, ISNULL(Detail.fam_No,Detail.emp_No) as number, ISNULL(fam_Rel,'員工') as Rel, ISNULL(fam_Name, emp_Name) as Name, ISNULL(fam_Sex,emp_Sex) as Sex, ISNULL(fam_ID, emp_ID) as ID,ISNULL(fam_Bdate,emp_Bdate) as Bdate, ISNULL(fam_Phone,emp_Phone) as Phone,ISNULL(fam_eat,emp_Eat) as Eat, ISNULL(fam_Car,1) as Car, fam_Bady, fam_kid, fam_Dis, fam_Mom,ISNULL(fam_Ben,emp_Ben) as Ben, ISNULL(fam_BenRel,emp_BenRel) as BenRel, ISNULL(fam_Emg,emp_Emg) as Emg, ISNULL(fam_EmgPhone,emp_EmgPhone) as EmgPhone, det_Date, det_CanDate as CanDate, ISNULL(fam_Note,emp_Note) as Note, det_canNote, ROW_NUMBER() OVER (ORDER BY det_CanDate, det_Date) as row  FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No  where Tra_No = ? and det_CanDate is not null order by CanDate) a WHERE (row >= ? and row <= ?)";
	@Override
	public List<DetailBean> selectCan(String Tra_No, int firstPage, int lastPage) {
		List<DetailBean> result = new ArrayList<>();
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECTCan);){
			stmt.setString(1, Tra_No);
			stmt.setInt(2, firstPage);
			stmt.setInt(3, lastPage);
			ResultSet rset = stmt.executeQuery();
			result = new ArrayList<DetailBean>();
			while (rset.next()) {
				DetailBean bean = new DetailBean();
				bean.setDet_No(rset.getInt("det_No"));
				bean.setEmp_No(rset.getInt("emp_No"));
				bean.setFam_No(rset.getInt("number"));
				bean.setRel(rset.getString("Rel"));
				bean.setName(rset.getString("Name"));
				bean.setSex(rset.getString("Sex"));
				bean.setID(rset.getString("ID"));
				bean.setBdate(rset.getDate("Bdate"));
				bean.setPhone(rset.getString("Phone"));
				bean.setEat(rset.getString("Eat"));
				bean.setCar(rset.getBoolean("Car"));
				bean.setFam_Bady(rset.getBoolean("fam_Bady"));
				bean.setFam_kid(rset.getBoolean("fam_kid"));
				bean.setFam_Dis(rset.getBoolean("fam_Dis"));
				bean.setFam_Mom(rset.getBoolean("fam_Mom"));
				bean.setBen(rset.getString("Ben"));
				bean.setBenRel(rset.getString("BenRel"));
				bean.setEmg(rset.getString("Emg"));
				bean.setEmgPhone(rset.getString("EmgPhone"));
				bean.setDet_Date(rset.getString("det_Date"));
				bean.setDet_CanDate(rset.getString("CanDate"));
				bean.setNote(rset.getString("Note"));
				bean.setDet_canNote(rset.getString("det_canNote"));
				bean.setRow(rset.getInt("row"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// SELECT報名維護未取消欄位
		private static final String SELECTNotCan = "SELECT * FROM(SELECT TOP 100 PERCENT det_No, Detail.emp_No, ISNULL(Detail.fam_No,Detail.emp_No) as number, ISNULL(fam_Rel,'員工') as Rel, ISNULL(fam_Name, emp_Name) as Name, ISNULL(fam_Sex,emp_Sex) as Sex, ISNULL(fam_ID, emp_ID) as ID,ISNULL(fam_Bdate,emp_Bdate) as Bdate, ISNULL(fam_Phone,emp_Phone) as Phone,ISNULL(fam_eat,emp_Eat) as Eat, ISNULL(fam_Car,1) as Car, fam_Bady, fam_kid, fam_Dis, fam_Mom,ISNULL(fam_Ben,emp_Ben) as Ben, ISNULL(fam_BenRel,emp_BenRel) as BenRel, ISNULL(fam_Emg,emp_Emg) as Emg, ISNULL(fam_EmgPhone,emp_EmgPhone) as EmgPhone, det_Date, det_CanDate as CanDate, ISNULL(fam_Note,emp_Note) as Note, det_canNote, ROW_NUMBER() OVER (ORDER BY det_CanDate, det_Date) as row  FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No  where Tra_No = ? and det_CanDate is null order by CanDate) a WHERE (row >= ? and row <= ?)";
		@Override
		public List<DetailBean> selectNotCan(String Tra_No, int firstPage, int lastPage) {
			List<DetailBean> result = new ArrayList<>();
			try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECTNotCan);){
				stmt.setString(1, Tra_No);
				stmt.setInt(2, firstPage);
				stmt.setInt(3, lastPage);
				ResultSet rset = stmt.executeQuery();
				result = new ArrayList<DetailBean>();
				while (rset.next()) {
					DetailBean bean = new DetailBean();
					bean.setDet_No(rset.getInt("det_No"));
					bean.setEmp_No(rset.getInt("emp_No"));
					bean.setFam_No(rset.getInt("number"));
					bean.setRel(rset.getString("Rel"));
					bean.setName(rset.getString("Name"));
					bean.setSex(rset.getString("Sex"));
					bean.setID(rset.getString("ID"));
					bean.setBdate(rset.getDate("Bdate"));
					bean.setPhone(rset.getString("Phone"));
					bean.setEat(rset.getString("Eat"));
					bean.setCar(rset.getBoolean("Car"));
					bean.setFam_Bady(rset.getBoolean("fam_Bady"));
					bean.setFam_kid(rset.getBoolean("fam_kid"));
					bean.setFam_Dis(rset.getBoolean("fam_Dis"));
					bean.setFam_Mom(rset.getBoolean("fam_Mom"));
					bean.setBen(rset.getString("Ben"));
					bean.setBenRel(rset.getString("BenRel"));
					bean.setEmg(rset.getString("Emg"));
					bean.setEmgPhone(rset.getString("EmgPhone"));
					bean.setDet_Date(rset.getString("det_Date"));
					bean.setDet_CanDate(rset.getString("CanDate"));
					bean.setNote(rset.getString("Note"));
					bean.setDet_canNote(rset.getString("det_canNote"));
					bean.setRow(rset.getInt("row"));
					result.add(bean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		// SELECT報名維護所有欄位
		private static final String SELECT = "SELECT * FROM(SELECT TOP 100 PERCENT det_No, Detail.emp_No, ISNULL(Detail.fam_No,Detail.emp_No) as number, ISNULL(fam_Rel,'員工') as Rel, ISNULL(fam_Name, emp_Name) as Name, ISNULL(fam_Sex,emp_Sex) as Sex, ISNULL(fam_ID, emp_ID) as ID,ISNULL(fam_Bdate,emp_Bdate) as Bdate, ISNULL(fam_Phone,emp_Phone) as Phone,ISNULL(fam_eat,emp_Eat) as Eat, ISNULL(fam_Car,1) as Car, fam_Bady, fam_kid, fam_Dis, fam_Mom,ISNULL(fam_Ben,emp_Ben) as Ben, ISNULL(fam_BenRel,emp_BenRel) as BenRel, ISNULL(fam_Emg,emp_Emg) as Emg, ISNULL(fam_EmgPhone,emp_EmgPhone) as EmgPhone, det_Date, det_CanDate as CanDate, ISNULL(fam_Note,emp_Note) as Note, det_canNote, ROW_NUMBER() OVER (ORDER BY det_CanDate, det_Date) as row FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No  where Tra_No = ? order by CanDate) a WHERE (row >= ? and row <= ?)";
		@Override
		public List<DetailBean> select(String Tra_No, int firstPage, int lastPage) {
			List<DetailBean> result = new ArrayList<>();
			try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT);){
				stmt.setString(1, Tra_No);
				stmt.setInt(2, firstPage);
				stmt.setInt(3, lastPage);
				ResultSet rset = stmt.executeQuery();
				result = new ArrayList<DetailBean>();
				while (rset.next()) {
					DetailBean bean = new DetailBean();
					bean.setDet_No(rset.getInt("det_No"));
					bean.setEmp_No(rset.getInt("emp_No"));
					bean.setFam_No(rset.getInt("number"));
					bean.setRel(rset.getString("Rel"));
					bean.setName(rset.getString("Name"));
					bean.setSex(rset.getString("Sex"));
					bean.setID(rset.getString("ID"));
					bean.setBdate(rset.getDate("Bdate"));
					bean.setPhone(rset.getString("Phone"));
					bean.setEat(rset.getString("Eat"));
					bean.setCar(rset.getBoolean("Car"));
					bean.setFam_Bady(rset.getBoolean("fam_Bady"));
					bean.setFam_kid(rset.getBoolean("fam_kid"));
					bean.setFam_Dis(rset.getBoolean("fam_Dis"));
					bean.setFam_Mom(rset.getBoolean("fam_Mom"));
					bean.setBen(rset.getString("Ben"));
					bean.setBenRel(rset.getString("BenRel"));
					bean.setEmg(rset.getString("Emg"));
					bean.setEmgPhone(rset.getString("EmgPhone"));
					bean.setDet_Date(rset.getString("det_Date"));
					bean.setDet_CanDate(rset.getString("CanDate"));
					bean.setNote(rset.getString("Note"));
					bean.setDet_canNote(rset.getString("det_canNote"));
					bean.setRow(rset.getInt("row"));
					result.add(bean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		//計算報名總筆數
	private static final String SELECT_DatailCount="SELECT count(det_No) as count FROM detail where tra_No = ?";
	@Override
	public int selectDatailCount(String tra_No){
		int result = 0;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_DatailCount);) {
			stmt.setString(1, tra_No);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				result = set.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	

	// 由name判斷是否為員工(SELECT_emp_Name)or親屬(SELECT_fam_Name)
	private static final String SELECT_emp_Name = "SELECT emp_No = '員工' from Employee where emp_No=? and emp_Name=?";

	@Override
	public String select_emp_Name(int Emp_No, String Emp_Name) {
		String result = null;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_emp_Name);) {
			stmt.setInt(1, Emp_No);
			stmt.setString(2, Emp_Name);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				result = set.getString("emp_No");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static final String SELECT_fam_Name = "SELECT fam_No from Family where emp_No=? and fam_Name=?";

	@Override
	public String select_fam_Name(int Emp_No, String Fam_Name) {
		String result = null;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_fam_Name);) {
			stmt.setInt(1, Emp_No);
			stmt.setString(2, Fam_Name);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				result = set.getString("fam_No");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 找到員工目前所套用的emp_SubTra
	private static final String SELECT_emp_SubTra = "SELECT emp_SubTra FROM Employee where emp_No=?";

	@Override
	public String SELECT_emp_SubTra(int Emp_No) {
		String result = null;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_emp_SubTra);) {
			stmt.setInt(1, Emp_No);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				result = set.getString("emp_SubTra");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 找到員工所報名的所有旅費中花費最高的Tra_No
	private static final String SELECT_top1_Tra_No = "SELECT  TOP 1 Tra_No FROM (SELECT TOP 1  Detail.Tra_No, Detail.emp_No,SUM(det_money) as totalMoney FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No full outer join Travel on Detail.tra_No = Travel.tra_No WHERE Detail.emp_No=? and ISNULL(fam_Rel,'員工') <> '親友' and det_CanDate is null and tra_End>GETDATE() GROUP BY  Detail.emp_No,Detail.Tra_No ORDER BY totalMoney DESC )temp1";

	@Override
	public String SELECT_top1_Tra_No(int Emp_No) {
		String result = null;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT_top1_Tra_No);) {
			stmt.setInt(1, Emp_No);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				result = set.getString("Tra_No");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 找到員工所報名的所有旅費中花費第二高的Tra_No
	private static final String SELECT_top2_Tra_No = "SELECT  TOP 1 Tra_No FROM (SELECT TOP 2 Detail.Tra_No, Detail.emp_No,SUM(det_money) as totalMoney FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No full outer join Travel on Detail.tra_No = Travel.tra_No WHERE Detail.emp_No=? and ISNULL(fam_Rel,'員工') <> '親友' and det_CanDate is null and tra_End>GETDATE() GROUP BY  Detail.emp_No,Detail.Tra_No order by totalMoney DESC)temp1 ORDER BY totalMoney ";

	@Override
	public String SELECT_top2_Tra_No(int Emp_No) {
		String result = null;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT_top2_Tra_No);) {
			stmt.setInt(1, Emp_No);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				result = set.getString("Tra_No");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 取消員工的detail用，點選取消找到附屬的emp_No
	private static final String SELECT_emp_No = "SELECT emp_No FROM Detail WHERE det_No=?";

	@Override
	public int select_emp_No(int det_No) {
		int result = 0;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_emp_No);) {
			stmt.setInt(1, det_No);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				result = set.getInt("emp_No");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 查詢此det_No的人是員工or親屬
		private static final String SELECT_Rel = "SELECT ISNULL(fam_Rel,'員工') as Rel FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No  WHERE det_No = ?";

		@Override
		public String Select_Rel(int det_No) {
			String result = null;
			try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_Rel);) {
				stmt.setInt(1, det_No);
				ResultSet set = stmt.executeQuery();
				while (set.next()) {
					result = set.getString("Rel");	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		//查詢員工在某一Tra_No花費的總金額
		private static final String select_TotalMoney = "SELECT totalMoney FROM (SELECT Detail.Tra_No, Detail.emp_No,SUM(det_money) as totalMoney FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No full outer join Travel on Detail.tra_No = Travel.tra_No WHERE Detail.emp_No=? and Detail.Tra_No=? and det_CanDate is null GROUP BY  Detail.emp_No,Detail.Tra_No )temp1";
		@Override
		public float select_TotalMoney(int emp_No, String Tra_No) {
			float result = 0;
			try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(select_TotalMoney);) {
				stmt.setInt(1, emp_No);
				stmt.setString(2, Tra_No);
				ResultSet set = stmt.executeQuery();
				while (set.next()) {
					result = set.getFloat("totalMoney");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		//insert時用，查詢身份證字號是否已使用
		private static final String selectSameId = "SELECT 'ID已使用' as sameID FROM Employee join Family on Employee.emp_NO = Family.emp_NO WHERE emp_ID = ? or fam_Id = ? and Employee.emp_NO = ?";
		@Override
		public String selectSameId(String id, int emp_No){
			String result=null;
			try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(selectSameId);) {
				stmt.setString(1, id);
				stmt.setString(2, id);
				stmt.setInt(3, emp_No);
				ResultSet rset = stmt.executeQuery();
				while(rset.next()){
					result = rset.getString("sameID");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		//update親屬資料時用，查詢身份證字號是否已使用
		private static final String selectSameId2 = "SELECT 'ID已重複' as sameID FROM Family where fam_id=? and fam_no <> ?";
		@Override
		public String Select_SamId2(String fam_id, int fam_No){
			String result=null;
			try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(selectSameId2);) {
				stmt.setString(1, fam_id);
				stmt.setInt(2, fam_No);
				ResultSet rset = stmt.executeQuery();
				while(rset.next()){
					result = rset.getString("sameID");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
			
		}
		//由福委會新增親屬
	private static final String INSERT_Detail = "insert into Detail(emp_No,fam_No,tra_No,det_Date,det_money) values(?,?,?,GETDATE(),?)";
	@Override
	public boolean insert(DetailVO bean) {
		boolean b =true;
		try (Connection conn = ds.getConnection(); 
			PreparedStatement stmt = conn.prepareStatement(INSERT_Detail);) {
			if (bean != null) {
				stmt.setInt(1, bean.getEmp_No());
				stmt.setInt(2, bean.getFam_No());
				stmt.setString(3, bean.getTra_No());
				stmt.setDouble(4, bean.getDet_money());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//由福委會新增員工
	private static final String INSERT_DetailEmp = "insert into Detail(emp_No,tra_No,det_Date,det_money) values(?,?,GETDATE(),?)";

	@Override
	public boolean insert_emp(DetailVO bean) {
		boolean b = true;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_DetailEmp);) {
			if (bean != null) {
				stmt.setInt(1, bean.getEmp_No());
				stmt.setString(2, bean.getTra_No());
				stmt.setFloat(3, bean.getDet_money());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	//新增員工時，insert一筆TotalAmount
	private static final String INSERT_TA = "INSERT INTO TotalAmount(tra_No,emp_No,TA_money,thisyear,yearsub) values(?,?,?,?,?)";

	@Override
	public boolean INSERT_TA(String Tra_No, int Emp_No, float TA_money, String thisyear, boolean yearsub) {
		boolean b = true;
		try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(INSERT_TA);) {
			stmt.setString(1, Tra_No);
			stmt.setInt(2, Emp_No);
			stmt.setFloat(3, TA_money);
			stmt.setString(4, thisyear);
			stmt.setBoolean(5, yearsub);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	// 更新取消日期=點選當下的時間，連同親屬一起取消(員工)
	private static final String UPDATE_CanDate = "update Detail set det_CanDate=GETDATE(), det_canNote=? where emp_No=? and tra_No=? and det_CanDate is null";

	@Override
	public List<DetailBean> update(int emp_No, String det_canNote, String tra_No) {
		List<DetailBean> result = null;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_CanDate);) {
			stmt.setString(1, det_canNote);
			stmt.setInt(2, emp_No);
			stmt.setString(3, tra_No);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 更新取消日期=點選當下的時間，單筆取消(親屬)
		private static final String UPDATE_FamCanDate = "update Detail set det_CanDate=GETDATE(), det_canNote=? where det_No=? and det_CanDate is null";
		@Override
		public List<DetailBean> update_FamCanDate(int det_No, String det_canNote) {
			List<DetailBean> result = null;
			try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_FamCanDate);) {
				stmt.setString(1, det_canNote);
				stmt.setInt(2, det_No);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

	// 當員工沒有花費第二高的Tra_No時，將他的輔助金變回尚未使用，emp_SubTra=NULL
	private static final String UPDATE_emp_Sub = "update Employee set emp_Sub=1, emp_SubTra=NULL where emp_No=?";

	@Override
	public boolean UPDATE_emp_Sub(int Emp_No) {
		boolean b = true;
		try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(UPDATE_emp_Sub);) {
			stmt.setInt(1, Emp_No);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	// 當員工有花費第二高的Tra_No時，將他的輔助金套用至該Tra_No
	private static final String UPDATE_emp_SubTra = "update Employee set emp_Sub=0, emp_SubTra=? where emp_No=?";

	@Override
	public boolean UPDATE_emp_SubTra(String Tra_No, int Emp_No) {
		boolean b = true;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_emp_SubTra);) {
			stmt.setString(1, Tra_No);
			stmt.setInt(2, Emp_No);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	//由報名明細update員工table
	private static final String UPDATE_empData = "update Employee set emp_name=?, emp_Phone=?, emp_Sex=?, emp_ID=?, emp_Bdate=?, emp_Eat=?, emp_Ben=?, emp_BenRel=?, emp_Emg=?, emp_EmgPhone=?, emp_Note=?  where emp_No=?";

	@Override
	public boolean UPDATE_empData(EmployeeVO bean) {
		boolean b = true;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_empData);) {
			stmt.setString(1, bean.getEmp_Name());
			stmt.setString(2, bean.getEmp_Phone());
			stmt.setString(3, bean.getEmp_Sex());
			stmt.setString(4, bean.getEmp_ID());
			stmt.setDate(5, bean.getEmp_Bdate());
			stmt.setString(6, bean.getEmp_Eat());
			stmt.setString(7, bean.getEmp_Ben());
			stmt.setString(8, bean.getEmp_BenRel());
			stmt.setString(9, bean.getEmp_Emg());
			stmt.setString(10, bean.getEmp_EmgPhone());
			stmt.setString(11, bean.getEmp_Note());
			stmt.setInt(12, bean.getEmp_No());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	//由報名明細update親屬table
	private static final String UPDATE_famData = "update Family set fam_Rel=?,fam_Name=?,fam_Phone=?,fam_Sex=?,fam_Id=?,fam_Bdate=?,fam_Eat=?,fam_Car=?,fam_Bady=?,fam_kid=?,fam_Dis=?,fam_Mom=?,fam_Ben=?,fam_BenRel=?,fam_Emg=?,fam_EmgPhone=?,fam_Note=? where fam_No=?";

	@Override
	public boolean UPDATE_famData(FamilyVO bean) {
		boolean b = true;
		try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(UPDATE_famData);) {
			stmt.setString(1, bean.getFam_Rel());
			stmt.setString(2, bean.getFam_Name());
			stmt.setString(3, bean.getFam_Phone());
			stmt.setString(4, bean.getFam_Sex());
			stmt.setString(5, bean.getFam_Id());
			stmt.setDate(6, bean.getFam_Bdate());
			stmt.setString(7, bean.getFam_Eat());
			stmt.setBoolean(8, bean.isFam_Car());
			stmt.setBoolean(9, bean.isFam_Bady());
			stmt.setBoolean(10, bean.isFam_kid());
			stmt.setBoolean(11, bean.isFam_Dis());
			stmt.setBoolean(12, bean.isFam_Mom());
			stmt.setString(13, bean.getFam_Ben());
			stmt.setString(14, bean.getFam_BenRel());
			stmt.setString(15, bean.getFam_Emg());
			stmt.setString(16, bean.getFam_EmgPhone());
			stmt.setString(17, bean.getFam_Note());
			stmt.setInt(18, bean.getFam_No());
			stmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	
	//取消or新增家屬時更新TotalAmount
		private static final String UPDATE_TA = "update TotalAmount set TA_money=?, yearsub=? where emp_No=? and tra_No=?";

		@Override
	public boolean Update_TA(float TA_money, Boolean yearsub, int Emp_No, String Tra_No) {
		boolean b = true;
		try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(UPDATE_TA);) {
			stmt.setFloat(1, TA_money);
			stmt.setBoolean(2, yearsub);
			stmt.setInt(3, Emp_No);
			stmt.setString(4, Tra_No);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
		
		//將TotalAmount的輔助金變為未套用
	private static final String UPDATE_TA_SUB = "update TotalAmount set yearsub=0 where yearsub=1 and emp_No=? and thisyear=?";
	@Override
	public boolean Update_TA_SUB(int emp_No, String thisyear) {
		boolean b = true;
		try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(UPDATE_TA_SUB);) {
			stmt.setInt(1, emp_No);
			stmt.setString(2, thisyear);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
		
	//取消員工時，刪除TotalAmount明細
	private static final String DELETE_TA = "delete from TotalAmount where tra_No=? and emp_No=?";
	@Override
	public boolean DELETE_TA(String Tra_No, int Emp_No){
		boolean b = true;
		try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(DELETE_TA);) {
			stmt.setString(1, Tra_No);
			stmt.setInt(2, Emp_No);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
	
	@Override
	public List<TotalAmountFormBean> selectBean(String tra_No) {
		List<TotalAmountFormBean> result = null;
		try (Connection conn = ds.getConnection(); 
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_TRA_NO);) {
			stmt.setString(1, tra_No);
			ResultSet rset = stmt.executeQuery();
			result = new ArrayList<TotalAmountFormBean>();
			while (rset.next()) {
				TotalAmountFormBean bean = new TotalAmountFormBean();
				bean.setTra_Name(rset.getString("tra_Name"));
				bean.setTra_No(rset.getString("tra_No"));
				bean.setDept_No(rset.getString("dept_No"));
				bean.setEmp_No(rset.getInt("emp_No"));
				bean.setFam_No(rset.getInt("fam_No"));
				bean.setDet_money(rset.getFloat("det_money"));
				bean.setEmp_Name(rset.getString("emp_Name"));
				bean.setFam_Name(rset.getString("fam_Name"));
				bean.setDet_note(rset.getString("det_note"));
				bean.setDet_noteMoney(rset.getFloat("det_noteMoney"));
				result.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean update_empNo(String det_note, float det_noteMoney, String tra_No, int emp_No) {
		boolean b = true;
		try (Connection conn = ds.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(UPDATE_DETAIL_FOR_EMP_NO);
			stmt.setString(1, det_note);
			stmt.setFloat(2, det_noteMoney);
			stmt.setInt(3, emp_No);
			stmt.setString(4, tra_No);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	@Override
	public boolean update_famNo(String det_note, float det_noteMoney, String tra_No, int fam_No) {
		boolean b = true;
		try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(UPDATE_DETAIL_FOR_FAM_NO);) {
			stmt.setString(1, det_note);
			stmt.setFloat(2, det_noteMoney);
			stmt.setInt(3, fam_No);
			stmt.setString(4, tra_No);
			stmt.executeUpdate();
			b = true;
		} catch (SQLException e) {
			b = false;
		}
		return b;
	}
	private final String selectDetail_by_Tra_No="select count(det_No) as count from Detail where tra_No=? and det_CanDate is null";
	
	@Override
	public int selectDetail_by_Tra_No(String tra_No){
		int result=0;
		try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(selectDetail_by_Tra_No);) {
			stmt.setString(1, tra_No);
			ResultSet rset = stmt.executeQuery();
			while(rset.next()){
				result = rset.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
private final String selectDetail_by_Tra_No_Can="select count(det_No) as count from Detail where tra_No=? and det_CanDate is Not null";
	
	@Override
	public int selectDetail_by_Tra_No_Can(String tra_No){
		int result=0;
		try (Connection conn = ds.getConnection();PreparedStatement stmt = conn.prepareStatement(selectDetail_by_Tra_No_Can);) {
			stmt.setString(1, tra_No);
			ResultSet rset = stmt.executeQuery();
			while(rset.next()){
				result = rset.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}