package model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TravelService {
	private ITravelDAO travelDAO = new TravelDAO();
	private IDetailDAO detailDAO = new DetailDAO();
	
	public void updateraFile(String tra_File,String tra_No){
		travelDAO.updateraFile(tra_File, tra_No);
	}

	public List<TravelVO> select(Integer emp_No) throws SQLException {
		List<TravelVO> vos = travelDAO.endTravel();//活動已結束行程
		List<TravelVO> AllVOs = travelDAO.getAll();//所有行程
		List<TravelVO> TravelVOs = travelDAO.entrtTravel(emp_No);//員工有報名的行程
		List<TravelVO> VOs = travelDAO.overTravel();//登記已結束行程(包含活動已結束)
		if(TravelVOs.size()==0){
			if (VOs != null) {
				for(int i=0;i<VOs.size();i++){
					for(int j=0;j<AllVOs.size();j++){
						if(AllVOs.get(j).getTra_NO().equals(VOs.get(i).getTra_NO())){
							AllVOs.remove(j);
						}
					}
				}
				for (TravelVO vo : AllVOs) {
					TravelVOs.add(vo);
				}
				for(int i=0;i<vos.size();i++){
					for(int j=0;j<VOs.size();j++){
						if(VOs.get(j).getTra_NO().equals(vos.get(i).getTra_NO())){
							VOs.remove(j);
						}
					}
				}
				for (TravelVO vo : VOs) {
					TravelVOs.add(vo);
				}
				for (TravelVO vo : vos) {
					TravelVOs.add(vo);
				}
				
			}
		}else{
			if(AllVOs != null){
				for(int i=0;i<TravelVOs.size();i++){
					for(int j=0;j<AllVOs.size();j++){
						if(TravelVOs.get(i).getTra_NO().equals(AllVOs.get(j).getTra_NO())){
							AllVOs.remove(j);
						}
					}
				}			
				if (VOs != null) {
					for(int i=0;i<VOs.size();i++){
						for(int j=0;j<AllVOs.size();j++){
							if(AllVOs.get(j).getTra_NO().equals(VOs.get(i).getTra_NO())){
								AllVOs.remove(j);
							}
						}
					}
				}
				
				for (TravelVO vo : AllVOs) {
					TravelVOs.add(vo);
				}
				for(int i=0;i<vos.size();i++){
					for(int j=0;j<VOs.size();j++){
						if(VOs.get(j).getTra_NO().equals(vos.get(i).getTra_NO())){
							VOs.remove(j);
						}
					}
				}
				for (TravelVO vo : VOs) {
					TravelVOs.add(vo);
				}	
				for (TravelVO vo : vos) {
					TravelVOs.add(vo);
				}
				
			}
		}	
		
		return TravelVOs;
	}
	
	public List<TravelVO> select_forSearch() throws SQLException {
		return travelDAO.getAll_forSearch();
	}

	public TravelVO select(long tra_NO) {
		return travelDAO.getAll(tra_NO);
	}

	public List<TravelVO> get_by_no_name(String no, String name) {
		return new TravelDAO().search(no, name);
	}

	public List<TravelVO> AfterOn(List<TravelVO> travelVO, Date start) {
		List<TravelVO> bean = new ArrayList<TravelVO>();
		for (TravelVO v : travelVO) {
			if (v.getTra_On().after(start) || v.getTra_On().equals(start)) {
				bean.add(v);
			}
		}
		return bean;
	}

	public List<TravelVO> BeforeOff(List<TravelVO> travelVO, Date end) {
		List<TravelVO> bean = new ArrayList<TravelVO>();
		for (TravelVO v : travelVO) {
			if (v.getTra_Off().before(end) || v.getTra_Off().equals(end)) {
				bean.add(v);
			}
		}
		return bean;
	}

	public List<TravelVO> where(List<TravelVO> travelVO, String[] location) {
		List<TravelVO> bean = new ArrayList<TravelVO>();
		for (TravelVO v : travelVO) {
			for (String loca : location) {
				if (v.getTra_Loc().equals(loca)) {
					bean.add(v);
				}
			}
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	public JSONArray to_Json(List<TravelVO> travelVO) {
		// 需要的參數設定
		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat();
		simple.applyPattern("yyyy-MM-dd");
		JSONArray array = new JSONArray();
		for (TravelVO v : travelVO) {
			JSONObject obj = new JSONObject();
			obj.put("id", v.getTra_NO());
			obj.put("name", v.getTra_Name());
			obj.put("onDate", simple.format((v.getTra_On())));
			obj.put("offDate", simple.format((v.getTra_Off())));
			obj.put("bDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((v.getTra_Beg())));
			obj.put("eDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((v.getTra_End())));
			obj.put("people", v.getTra_Total());
			obj.put("location", v.getTra_Loc());
			obj.put("peopleNow", detailDAO.tra_count((Long.valueOf(v.getTra_NO()))));
			array.add(obj);
		}
		return array;
	}

	public Map<String, String> selectTra_No(String emp_No) throws SQLException {
		Map<String, String> mp = new HashMap<String, String>();
		Map<String, String> selectTra_NoTra_End = travelDAO.selectTra_NoTra_End();// (活動編號,活動登記結束時間)
		Map<String, String> selectTra_NoTra_Beg = travelDAO.selectTra_NoTra_Beg();// (活動編號,活動登記開始時間)
		Map<String, Integer> selectTra_Count = detailDAO.tra_count();// (活動編號,目前參加人數)
		List<String> selectTra_No = travelDAO.selectTra_No();// 活動編號
		String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());// 現在系統時間
		String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());// 現在系統時間
		for (int i = 0; i < selectTra_No.size(); i++) {
			String Tra_End = selectTra_NoTra_End.get(selectTra_No.get(i));
			int a = java.sql.Timestamp.valueOf(Tra_End).compareTo(java.sql.Timestamp.valueOf(date));
			// a 回傳-1 登記已截止,回傳1尚可報名
			if (a == 1) {
				String Tra_Beg = selectTra_NoTra_Beg.get(selectTra_No.get(i));
				int c = java.sql.Timestamp.valueOf(Tra_Beg).compareTo(java.sql.Timestamp.valueOf(date));
				if (c == 1) {
					mp.put(selectTra_No.get(i), "5");
				} else if (c == -1) {
					int b = detailDAO.detail_Enter(emp_No, selectTra_No.get(i));
					if (b == 1) {
						mp.put(selectTra_No.get(i), "3");
					} else {
						TravelVO travelVO = travelDAO.getAll(Long.parseLong(selectTra_No.get(i)));
						if (selectTra_Count.get(selectTra_No.get(i)) < travelVO.getTra_Total()) {
							mp.put(selectTra_No.get(i), "0");

						} else {
							mp.put(selectTra_No.get(i), "2");
						}
					}
				}
			}
			if (a == -1) {
				TravelVO travelVo = travelDAO.getAll(Long.parseLong(selectTra_No.get(i)));
				String tra_Off = travelVo.getTra_Off().toString();
				int b = java.sql.Date.valueOf(tra_Off).compareTo(java.sql.Date.valueOf(date2));
				if (b == 1) {
					int c = detailDAO.detail_Enter(emp_No, selectTra_No.get(i));
					if (c == 1) {
						mp.put(selectTra_No.get(i), "3");
					}else{
						mp.put(selectTra_No.get(i), "1");
					}					
				} else if (b == -1) {
					mp.put(selectTra_No.get(i), "4");
				}

			}

		}
		return mp;// 0代表可報名1代表登記已截止2代表已額滿3代表已經報名4代表活動結束5代表活動尚未開始登記
	}

	// 柯
	public List<TravelVO> select(TravelVO bean) {
		List<TravelVO> result = travelDAO.getAll();
		return result;
	}

	public List<TravelVO> selectOne(String traNo) {
		List<TravelVO> result = null;
		TravelVO temp = travelDAO.getAll(Long.parseLong(traNo));
		result = new ArrayList<TravelVO>();
		result.add(temp);
		return result;
	}

	public List<TravelVO> selectExcel(TravelVO bean) {
		return travelDAO.selectExcel();
	}

	// 信智
	public TravelVO select(String tra_NO) {
		TravelVO result = null;
		if (tra_NO != null && tra_NO != "") {
			result = travelDAO.getAll(Long.parseLong(tra_NO));
		}
		// else {
		// result = travelDAO.select("");
		// }
		return result;
	}

	public void insert(TravelVO bean) {
		travelDAO.insert(bean);
	}

	public TravelVO update(TravelVO Travelupdate) {
		TravelVO result = null;
		if (Travelupdate != null) {
			result = travelDAO.update(Travelupdate);
		}
		return result;
	}

	public boolean delete(TravelVO bean) {
		boolean result = false;
		if (bean != null) {
			result = travelDAO.delete(bean.getTra_NO());
		}
		return result;
	}

	public TravelVO Count(String tra_No) {
		return travelDAO.Count(tra_No);
	}

	public String getTra_No() {
		List<String> tra_Nos = travelDAO.selectTra_No();
		String tra_No = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).replaceAll("-", "");
		String cklastFour = null;
		for (String nos : tra_Nos) {
			String no = nos.substring(0, 8);
			if (no.equals(tra_No)) {
				String x = nos.substring(8);
				Long lastFour = Long.valueOf(x) + 1;
				int lengths = lastFour.toString().length();
				if (lengths == 1) {
					cklastFour = "000" + lastFour.toString();
				} else if (lengths == 2) {
					cklastFour = "00" + lastFour.toString();
				} else if (lengths == 3) {
					cklastFour = "0" + lastFour.toString();
				} else {
					cklastFour = lastFour.toString();
				}
			} else {
				cklastFour = "0001";
			}
		}
		tra_No = tra_No + cklastFour;
		return tra_No;
	}
}
