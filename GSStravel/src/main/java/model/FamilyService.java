package model;

import java.sql.Date;
import java.util.List;

public class FamilyService {
	private IFamilyDAO familyDAO=new FamilyDAO();
	public List<FamilyVO> selectFam(String emp_No,long tra_No){
		return familyDAO.selectFam(emp_No,tra_No);
	}

	public void insert(FamilyVO famvo){
		familyDAO.insert(famvo);
	}
	public void update(FamilyVO famvo){
		familyDAO.update(famvo);
	}
	public void delete(String famid){
		familyDAO.delete(famid);
	}
	public List<String> selectid(Integer empno){
		return familyDAO.selectid(empno);
	} 

	public int select_byname(int emp_No ,String fam_Name){
		return familyDAO.select_byname(emp_No,fam_Name);
	}
	public Integer selectfam_No(String fam_Name){
		return familyDAO.selectfam_No(fam_Name);
	}
	public boolean insert_fam(FamilyVO famvo){
		return familyDAO.insert_fam(famvo);
	}
	public Integer selectfam_byid(String famid){
		return familyDAO.selectfam_byid(famid);
	}
	public List<Date> selectfam_Nodelete(int famno){
		return familyDAO.selectfam_Nodelete(famno);
	}
}
