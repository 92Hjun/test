package co.kr.test.member;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO{
	
	@Autowired
	// 실제 DB와 연동을하고 sql문을 실행하는 session 이다.
	// SQL Session Template
	private SqlSession sql;
	
	@SuppressWarnings("unused")
	@Override
	public List<Member> getAll(Member member) {
		System.out.println("xxxx : MemberDAOImpl member / " + member);
		
		return sql.selectList("getAll",member);
	}
	
	@Override
	public Member getById(int id) {
		return sql.selectOne("getById",id);
	}

	@Override
	public void deleteById(int id) {
		sql.delete("deleteById",id);
	}

	@Override
	public void create(Member member) {
		sql.insert("create", member);
	}

	@Override
	public Member getByName(String name) {
		
		return sql.selectOne("getByName", name);
	}

	@Override
	public void update(Member currentMember) {
		sql.update("update", currentMember);
		
	}

	@Override
	public int count() {
		return sql.selectOne("count");
	}

	@Override
	public List<Member> search(SearchType search) {
		return sql.selectList("search",search);
	}

}
