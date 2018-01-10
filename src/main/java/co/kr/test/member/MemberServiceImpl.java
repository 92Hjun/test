package co.kr.test.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	// SQL Session으로 ssql을 실행하여 받은 데이터를 
	// 전달해주는 DAO를 autowired 어노테이션을 해줌으로써
	// spring이 객체를 관리할수 있다.
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
		/* 실행 */
		System.out.println(member);
		memberDAO.create(member);

		/* 오류 */
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
