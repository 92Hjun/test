package co.kr.test.member;

public class Member {
	
	private Integer id, page, size;
	private String name,belong;
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if (page <= 0) {
			page = 1;
		}
		this.page = (
				
				
				page-1) * size;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
		return "Member [id=" + id + ", name=" + name + ", belong=" + belong + "]";
	}
	
}
