package co.kr.test.member;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import co.kr.test.util.Constants;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private MemberService memberService;
	
	// RequestMapping / value�� ��� uri�� �ۼ� ��ǥ�ϴ� ���ҽ��̸��� ���� ���,
	// method = RequestMethod.GET / value�� �ۼ��� uri�� ��û �޼ҵ��� ������ ������ GET / POST / DELETE / PUT �� ������ �˾Ƴ��� �˸°� ó����.
	@RequestMapping(value="/member", method=RequestMethod.GET)
	public ResponseEntity<List<Member>> getAllMembers() {
		System.out.println("xxxx : getAllMembers-------------------------------------------------------");
		
		System.out.println("xxxx : Member member = new Member();");
		Member member = new Member();
		
		// ��� ����� �����͸� ã�� memberList�� ��´�. 
		List<Member> memberList = memberService.getAll(member);
		
		System.out.println("xxxx : memberService.getAll(member)");
		
		// memberList�� ����ִٸ� ResponseEntity�� HTTP�����ڵ带 NO_CONTENT�� ��� �������� ������.
		if (memberList.isEmpty()) {
			
			// ResponseEntity�� Spring4�� ���� Ŭ���� (����Ÿ��)�� @ModelAttribute�� ���� ������ϴµ�
			// ����ϴ� ������ ��� �����Ϳ� HTTP �����ڵ带 ���������� �����Ҽ��־� ���ϴ�.
			return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		}
		
		// memberList�� ������� �ʴٸ� ResponseEntity�� memberList�� HTTP�����ڵ� OK�� �������� ������.
		return new ResponseEntity<List<Member>>(memberList,HttpStatus.OK);
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.GET)
	
	// @PathVariable uri�� �ִ� �����͸� ������ ������ ��´�. �̶� {�̸�}�� (@pathVariable int �̸�) 
	// �̸��� �̸����� ���ٸ� @PathVariable("�̸�") �̶�� ������ �ʿ���� ����Ÿ�԰� ������ �����־ �ȴ�.
	public ResponseEntity<Member> getMemberById(@PathVariable int id) {
		System.out.println("xxxx : getMemberById-------------------------------------------------------");
		// @PathVariable�� ���� id�� ����� id���� ��Ī�Ǵ� ����� �����͸� �����´�.
		Member member = memberService.getById(id);
		
		// member�� null �̸� ResponseEntity�� HTTP�����ڵ� NO_CONTENT�� ��� �������� ������.
		if (member == null) {
			return new ResponseEntity<Member>(HttpStatus.NO_CONTENT);
		}
		
		// member�� null�� �ƴϸ� ResponseEntity�� member��ü�� HTTP�����ڵ� OK�� ��� ������.
		return new ResponseEntity<Member>(member,HttpStatus.OK);
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Member> deleteMemberById(@PathVariable int id){
		System.out.println("xxxx : deleteMemberById-------------------------------------------------------");
		// @PathVariable�� ����� uri�� �ִ� id���� ������ ������ ������
		// id �����Ϳ� ��Ī�Ǵ� member �����͸� �����´�.
		Member member = memberService.getById(id);
		
		// member�� null�̸� HTTP�����ڵ带 NOT_FOUND�� �� �Ŀ� �������� ������.
		if (member == null) {
			System.out.println(id+"�� ��ϵ� ����� �����ϴ�.");
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		
		// member�� null�� �ƴϸ� id���� ��Ī�Ǵ� �����͸� �����. 
		memberService.deleteById(id);
		
		// member�� �������⶧���� NO_CONTENT�� �����ڵ� ������ ������.
		return new ResponseEntity<Member>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="/member", method=RequestMethod.POST)
	
	// @RequestBody�� POST�� ��û�� �Բ� ���� �����͸� ������ �� ��ü�� ��ȯ�ǰ� �ϴ� ������̼��̴�.
	// UriComponentsBuilder�� ��ü ���ο� �ִ� �����͸� Uri�� ��ȯ�� ���� uri�� �ǵ��� ������ �� �ִ�.
	public ResponseEntity<Void> addMember(@RequestBody Member member, UriComponentsBuilder ucBulider){
		System.out.println("xxxx : addMember-------------------------------------------------------");
		
		System.out.println("Creating Member" + member);
		
		// ����Ϸ��� ����� ���� ����� ������ true �ƴϸ� false�� ��ȯ�Ѵ�.
		// �ߺ� ������ ��ȸ
		if (memberService.isExist(member)) {
			System.out.println("A Member with name " + member.getName() + " already exist");
			
			// �ߺ��Ǵ� �����Ͱ� ������ HTTP�����ڵ带 CONFLICT�� ��ȯ �� ������ ������.
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		// �ߺ��Ǵ� �����Ͱ� ������ member�� �����Ѵ�. 
		memberService.create(member);
		
		// HttpHeaders�� Http�� header������ ���¸� ��ȯ��ų�� �ִ� ��ü�̴�.
		HttpHeaders headers = new HttpHeaders();
		
		// header�� Location�� ucBulider�� ���� uri�� /member/{��ϵ� ����� �̸�}���� ��ȯ�ϴµ�.
		// �̷��� Location�� ��ȯ���ָ� ��ϵ� ����ڰ� �������� �������ε� �� �� �ִ�.
		headers.setLocation(ucBulider.path("/member/{name}").buildAndExpand(member.getName()).toUri());
		
		// ��������� ��ȯ�� �� �� ResponseEntity�� header������ HTTP���¸� CREATED�� ��ȯ�� �� �������� ������.
		return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Member> updateMember(@PathVariable int id, @RequestBody Member member) {
		
		System.out.println("xxxx : updateMember-------------------------------------------------------");
		System.out.println("Update Member" + id);
		
		// @PathVariable�� ��뿡 uri�� �ִ� id�� �޾� �����͸� ������ member�� ã�´�.
		Member currentMember = memberService.getById(id);
		
		// �����͸� ������ member�� null�̸�
		// HTTP�����ڵ带 NOT_FOUND�� ������ ������ ������.
		if (currentMember == null) {
			System.out.println("Member with id" + id +" not fount");
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		
		// @RequestBody�� ���� ��ü�� ��ȯ�� �����͸� �����Ϸ��� ã�� member��ü�� �����͸� �����Ѵ�.
		currentMember.setName(member.getName());
		currentMember.setBelong(member.getBelong());
		
		// ������ �����Ͱ� �ϼ��Ǹ� �����͸� �����Ѵ�.
		memberService.update(currentMember);
		
		// �����͸� ������ �� ResponseEntity�� ����� ������ member ��ü�� HTTP �����ڵ带 OK�� ������ �� �������� ������.
		return new ResponseEntity<Member>(currentMember,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/member/{page}/{size}",method=RequestMethod.GET)
	// @PathVariable null�̳� ������ ó������, �ٸ�Ÿ���� ���� ������ Bad Request ������ ������� ������������ Ȱ���Ѵ�.
	// @RequestParam null�̳� ������ defaultValue�� ó��������, ��ſ� ������Ʈ�� ex) /member?page=��&size=�� ����� ����ؾ���.
	public ResponseEntity<List<Member>> getList(@PathVariable("page") int page, @PathVariable("size") int size){
		System.out.println("xxxx : getList start -------------------------------------------------------");
		System.out.println("xxxx : page : " +page + ", size : " + size);
		
		// member ��ü�� �����.
		Member member = new Member();
		
		// @PathVariable�� uri�� page�� size�� �޾Ƽ� member�� size�� page�� set ��Ų��.
		
		member.setSize(size);
		member.setPage(page);
		
		// memberService�� getAll�� limit�� Ȱ���� ����¡ ó���� ����
		// ���� ��������� list�� �޴´�.
		List<Member> memberList = memberService.getAll(member);
		
		// memberList�� ��������ÿ� ResponseEntity�� HttpStatus.NOT_FOUND ���¸� ���
		// ������� �����ڵ�� �����Ѵ�.
		if (memberList.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NOT_FOUND);
		}
		
		// memberList�� ������� �ʴٸ� ResponseEntity�� memberList�� HttpStatus.OK �����ڵ带 ��� �����Ѵ�.
		return new ResponseEntity<List<Member>>(memberList,HttpStatus.OK);
	}
	
	@RequestMapping(value="/member/type/{type}/word/{word}", method=RequestMethod.GET)
	public ResponseEntity<List<Member>> getSearchList(@PathVariable String type, @PathVariable String word) {
		System.out.println("xxxx : getSearchList-------------------------------------------------------");
		System.out.println("xxxx : type : " + type + ", word : " + word );
		
		SearchType search = new SearchType();
		
		search.setType((type.trim().equals("") || type.trim().equals(null) ? Constants.DEFAULT_TYPE : type));
		
		System.out.println("xxxx : search " + search);
		
		List<Member> memberList = memberService.search(search);
		
		if (memberList.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Member>>(memberList,HttpStatus.OK);
		
	}
	
	
}
