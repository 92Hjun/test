package co.kr.test.member;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

// Transactional ������̼��� �����ϸ�
// �������̽��� implements�� Ŭ������ ���൵�� ���������� rollback�� �ȴ�.
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
