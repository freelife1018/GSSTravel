package model;

public class QandAVO {
	private int question_Category;
	private int question_No;
	private int qa_No;
	private String question_Time;
	private String question_Text;
	private String question_Title;
	private int answer_No;
	private String answer_Text;
	private String answer_Time;
	private boolean question_secret;
	private boolean newimg;
	
	public boolean getNewimg() {
		return newimg;
	}
	public void setNewimg(boolean newimg) {
		this.newimg = newimg;
	}
	public boolean getQuestion_secret() {
		return question_secret;
	}
	public void setQuestion_secret(boolean question_secret) {
		this.question_secret = question_secret;
	}
	
	public int getQuestion_Category() {
		return question_Category;
	}
	public void setQuestion_Category(int question_Category) {
		this.question_Category = question_Category;
	}
	public int getQuestion_No() {
		return question_No;
	}
	public void setQuestion_No(int question_No) {
		this.question_No = question_No;
	}
	public int getQa_No() {
		return qa_No;
	}
	public void setQa_No(int qa_No) {
		this.qa_No = qa_No;
	}
	public String getQuestion_Time() {
		return question_Time;
	}
	public void setQuestion_Time(String question_Time) {
		this.question_Time = question_Time;
	}
	public String getQuestion_Text() {
		return question_Text;
	}
	public void setQuestion_Text(String question_Text) {
		this.question_Text = question_Text;
	}
	public String getQuestion_Title() {
		return question_Title;
	}
	public void setQuestion_Title(String question_Title) {
		this.question_Title = question_Title;
	}
	public int getAnswer_No() {
		return answer_No;
	}
	public void setAnswer_No(int answer_No) {
		this.answer_No = answer_No;
	}
	public String getAnswer_Text() {
		return answer_Text;
	}
	public void setAnswer_Text(String answer_Text) {
		this.answer_Text = answer_Text;
	}
	public String getAnswer_Time() {
		return answer_Time;
	}
	public void setAnswer_Time(String answer_Time) {
		this.answer_Time = answer_Time;
	}
}
