package co.kr.test.member;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

// Transactional 어노테이션을 선언하면
// 인터페이스를 implements한 클래스가 실행도중 오류가나면 rollback이 된다.
@Transactional
public interface MemberService {

	List<Member> getAll(Member member);

	Member getById(int id);

	void deleteById(int id);

	boolean isExist(Member member);

	void create(Member member);

	Member getByName(String name);

	void update(Member currentMember);

	List<Member> search(SearchType search);

}
