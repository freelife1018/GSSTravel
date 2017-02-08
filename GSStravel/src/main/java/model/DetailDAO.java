package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	private static final String detail_Count = "select count(f.fam_Name) as count from Detail d join Family f ON f.fam_No=d.fam_No where d.emp_No=? and d.tra_No=?";
	private static final String detail_Emp_No = "select distinct emp_No,det_Date from Detail where tra_No=? and det_CanDate is null order by det_Date";
	private static final String detail_Enter = "select det_Date from Detail where emp_No=? and tra_No=? and det_CanDate is null order by det_Date";	                                            
	private static final String updateDet_CanDate = "update Detail set det_CanDate=? where emp_No=? and tra_No=?";
	private static final String SELECT_BY_TRA_NO = "select d.tra_No , tra_Name , dept_No , e.emp_No , f.fam_No , emp_Name , emp_sub , fam_Name , det_money , det_note ,det_noteMoney from Detail d join Employee e on d.emp_No=e.emp_No left join Family f on d.fam_No = f.fam_No left join Travel t on t.tra_No=d.tra_No where d.tra_No=? and det_CanDate is null" ;
	private static final String UPDATE_DETAIL_FOR_EMP_NO = "update Detail set det_note=? , det_noteMoney=? where emp_No=? and fam_No is null and tra_No=?";
	private static final String UPDATE_DETAIL_FOR_FAM_NO = "update Detail set det_note=? , det_noteMoney=? where fam_No=? and tra_No=?";
	
	@Override
	public int detail_Enter(String emp_No, String tra_No) {
		int a=0 ;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(detail_Enter);) {
			stmt.setString(1, emp_No);
			stmt.setString(2, tra_No);
			ResultSet rset = stmt.executeQuery();			
			while (rset.next()) {	
				a=1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public List<String> detail_Emp_No(long tra_No) {
		List<String> result = null;
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(detail_Emp_No);) {
			result = new ArrayList<>();
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
	public void updateDet_CanDate(String emp_No,String tra_No) {
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateDet_CanDate);) {
			String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());//現在系統時間
			stmt.setString(1, date);
			stmt.setString(2, emp_No);
			stmt.setString(3, tra_No);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static final String SELECT = "SELECT det_No, Detail.emp_No, ISNULL(fam_Rel,'員工') as Rel, ISNULL(fam_Name, emp_Name) as Name, ISNULL(fam_Sex,emp_Sex) as Sex, ISNULL(fam_ID, emp_ID) as ID,ISNULL(fam_Bdate,emp_Bdate) as Bdate, ISNULL(fam_eat,emp_Eat) as Eat, ISNULL(fam_Car,1) as Car, fam_Bady, fam_kid, fam_Dis, fam_Mom,ISNULL(fam_Ben,emp_Ben) as Ben, ISNULL(fam_BenRel,emp_BenRel) as BenRel, ISNULL(fam_Emg,emp_Emg) as Emg, ISNULL(fam_EmgPhone,emp_EmgPhone) as EmgPhone, det_Date, det_CanDate as CanDate, ISNULL(fam_Note,emp_Note) as Note FROM Detail full outer join family on  Detail.fam_No = family.fam_No full outer join Employee on Detail.emp_No = Employee.emp_No WHERE Tra_No = ?";
	@Override
	public List<DetailBean> select(String Tra_No) {
		List<DetailBean> result = new ArrayList<>();
		try{
			Connection conn  = ds.getConnection();
			PreparedStatement  stmt = conn.prepareStatement(SELECT);
			stmt.setString(1, Tra_No);
			ResultSet rset = stmt.executeQuery();
			result = new ArrayList<DetailBean>();
			while(rset.next()) {
				DetailBean bean = new DetailBean();
				bean.setDet_No(rset.getInt("det_No"));
				bean.setEmp_No(rset.getInt("emp_No"));
				bean.setRel(rset.getString("Rel"));
				bean.setName(rset.getString("Name"));
				bean.setSex(rset.getString("Sex"));
				bean.setID(rset.getString("ID"));
				bean.setBdate(rset.getDate("Bdate"));
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
				
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static final String SELECT_emp_Name = "SELECT emp_No = '員工' from Employee where emp_No=? and emp_Name=?";
	@Override
	public String select_emp_Name(int Emp_No, String Emp_Name){
		String result = null;
		try(
			Connection conn = ds.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_emp_Name);){
			stmt.setInt(1, Emp_No);
			stmt.setString(2, Emp_Name);
			ResultSet set  = stmt.executeQuery();
			while(set.next()){
				result = set.getString("emp_No");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
	private static final String SELECT_fam_Name = "SELECT fam_No from Family where emp_No=? and fam_Name=?";
	@Override
	public String select_fam_Name(int Emp_No, String Fam_Name){
		String result = null;
		try(
			Connection conn = ds.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_fam_Name);){
			stmt.setInt(1, Emp_No);
			stmt.setString(2, Fam_Name);
			ResultSet set  = stmt.executeQuery();
			while(set.next()){
				result = set.getString("fam_No");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static final String INSERT_Detail = "insert into Detail(emp_No,fam_No,tra_No,det_Date,det_money) values(?,?,?,GETDATE(),?)"; 
	@Override
	public DetailVO insert(DetailVO bean) {
		DetailVO result = null;
		try(
		Connection conn = ds.getConnection();
		PreparedStatement stmt = conn.prepareStatement(INSERT_Detail);) {
			if(bean!=null) {
				stmt.setInt(1, bean.getEmp_No());
				stmt.setInt(2, bean.getFam_No());
				stmt.setString(3, bean.getTra_No());
				stmt.setDouble(4, bean.getDet_money());
				int i = stmt.executeUpdate();
				if(i==1) {
					result = bean;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


	private static final String UPDATE_CanDate = "update Detail set det_CanDate=GETDATE() where det_No=? and Tra_No=?";
	@Override
	public List<DetailBean>update(int det_No, String Tra_No) {
		List<DetailBean> result = null;
		try(
			Connection conn = ds.getConnection();
			PreparedStatement stmt = conn.prepareStatement(UPDATE_CanDate);) 
		{
			stmt.setInt(1, det_No);
			stmt.setString(2, Tra_No);
			int i = stmt.executeUpdate();
			if(i==1) {
				result = this.select(Tra_No);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public List<TotalAmountFormBean> selectBean(String tra_No) {
		List<TotalAmountFormBean> result = null;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT_BY_TRA_NO);
				){
			stmt.setString(1, tra_No);
			ResultSet rset = stmt.executeQuery();
			result = new ArrayList<TotalAmountFormBean>();
			while (rset.next()) {
				TotalAmountFormBean bean = new TotalAmountFormBean();
				bean.setTra_Name(rset.getString("tra_Name"));
				bean.setDept_No(rset.getString("dept_No"));
				bean.setEmp_No(rset.getInt("emp_No"));
				bean.setFam_No(rset.getInt("fam_No"));
				bean.setDet_money(rset.getFloat("det_money"));
				bean.setEmp_Name(rset.getString("emp_Name"));
				bean.setFam_Name(rset.getString("fam_Name"));
				bean.setDet_note(rset.getString("det_note"));
				bean.setDet_noteMoney(rset.getFloat("det_noteMoney"));
				bean.setEmp_sub(rset.getBoolean("emp_sub"));
				result.add(bean);
				
			}
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean update_empNo( String det_note ,float det_noteMoney, String tra_No, int emp_No){
		boolean b=true;
		try(Connection conn=ds.getConnection()){
			PreparedStatement stmt = conn.prepareStatement(UPDATE_DETAIL_FOR_EMP_NO);
			stmt.setString(1, det_note);
			stmt.setFloat(2, det_noteMoney);
			stmt.setInt(3, emp_No);
			stmt.setString(4, tra_No);
			stmt.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			b=false;
		}
		return b;
	}
	@Override
	public boolean update_famNo(String det_note,float det_noteMoney, String tra_No , int fam_No){
		boolean b=true;
		try(Connection conn=ds.getConnection()){
			PreparedStatement stmt = conn.prepareStatement(UPDATE_DETAIL_FOR_FAM_NO);
			stmt.setString(1, det_note);
			stmt.setFloat(2, det_noteMoney);
			stmt.setInt(3, fam_No);
			stmt.setString(4, tra_No);
			stmt.executeUpdate();
			b=true;
		}catch (SQLException e){
			b=false;
		}
		return b;
	}
}
