package co.kr.test.member;

public class SearchType {
	
	private String type,word;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public SearchType(String type, String word) {
		this.type = type;
		this.word = word;
	}
	
	public SearchType() {}

	@Override
	public String toString() {
		return "SearchType [type=" + type + ", word=" + word + "]";
	}
}
