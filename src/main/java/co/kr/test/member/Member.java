package co.kr.test.member;

public class Member {
	
	private int id,page, size;
	private String name,belong;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		
		if (page == 0) {
			page = 1;
		}
		this.page = (page - 1) * size;
	}

	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBelong() {
		return belong;
	}
	
	public void setBelong(String belong) {
		this.belong = belong;
	}
	
	public Member() {}

	@Override
	public String toString() {
		return "Member [id=" + id + ", page=" + page + ", size=" + size + ", name=" + name + ", belong=" + belong + "]";
	}
}
