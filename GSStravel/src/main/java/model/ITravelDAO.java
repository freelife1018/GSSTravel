package model;

import java.util.List;
import java.util.Map;

public interface ITravelDAO {
	public List<TravelVO> getAll(); //所有的活動行程
	public List<TravelVO> getAll_forSearch(); //已tra_No排序的所有行程
	public TravelVO getAll(long tra_NO);//單筆活動行程
	public Map<String,String> selectTra_NoTra_End();//所有活動行程編號跟對應的登記結束日期
	public List<String> selectTra_No();//所有活動行程編號
	public List<TravelVO> search(String no,String name);
	public Map<String,String> selectTra_NoTra_Beg();//所有活動行程編號跟對應的登記開始日期
//	public TravelVO select(String tra_NO) getAll
	public void insert(TravelVO bean);
	public TravelVO update(TravelVO Travelupdate);
	public boolean delete(String tra_NO);
	public List<TravelVO> selectExcel();
	public TravelVO Count(String tra_No);	//此行程的可報名總人數&個人可報名總人數
	public String SELECTsubTra(String tra_No);     //目前套用的輔助金是否已使用 NULL=已使用
	public List<TravelVO> endTravel();
	public List<TravelVO> entrtTravel(Integer emp_No);
	public void deleteTravel(String tra_No) ;
	public void updateraFile(String tra_File,String tra_No);
	public List<TravelVO> overTravel();
}
