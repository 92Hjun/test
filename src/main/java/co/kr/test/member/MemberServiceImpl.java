package co.kr.test.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	// SQL Session���� ssql�� �����Ͽ� ���� �����͸� 
	// �������ִ� DAO�� autowired ������̼��� �������ν�
	// spring�� ��ü�� �����Ҽ� �ִ�.
	private MemberDAO memberDAO;

	@Override
	public List<Member> getAll(Member member) {
		System.out.println("xxxx : MemberServiceImpl member /" + member);
		return memberDAO.getAll(member);
	}

	@Override
	public Member getById(int id) {
		return memberDAO.getById(id);
	}

	@Override
	public void deleteById(int id) {
		memberDAO.deleteById(id);
		
	}
	
	@Override
	public void create(Member member) {
		/* ���� */
		System.out.println(member);
		memberDAO.create(member);

		/* ���� */
		memberDAO.update(null);
	}
	
	@Override
	public boolean isExist(Member member) {
		//
		return memberDAO.getByName(member.getName()) != null;
	}
	@Override
	public Member getByName(String name) {
		return memberDAO.getByName(name);
	}

	@Override
	public void update(Member currentMember) {
		memberDAO.update(currentMember);
		
	}

	@Override
	public List<Member> search(SearchType search) {
		return memberDAO.search(search);
	}

}
