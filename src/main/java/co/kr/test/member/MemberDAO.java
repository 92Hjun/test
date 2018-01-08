package co.kr.test.member;

import java.util.List;

public interface MemberDAO {

	Member getById(int id);

	void deleteById(int id);

	void create(Member member);

	Member getByName(String name);

	void update(Member currentMember);

	List<Member> getAll(Member member);

	int count();

	List<Member> search(SearchType search);

}
