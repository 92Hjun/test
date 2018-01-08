package co.kr.test.member;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private MemberService memberService;
	
	// RequestMapping / value는 경로 uri를 작성 대표하는 리소스이름을 많이 사용,
	// method = RequestMethod.GET / value에 작성된 uri로 요청 메소드의 종류를 보내면 GET / POST / DELETE / PUT 등 종류를 알아내어 알맞게 처리함.
	@RequestMapping(value="/member", method=RequestMethod.GET)
	public ResponseEntity<List<Member>> getAllMembers() {
		System.out.println("xxxx : RestController");
		
		System.out.println("xxxx : Member member = new Member();");
		Member member = new Member();
		
		// 모든 멤버의 데이터를 찾아 memberList에 담는다. 
		List<Member> memberList = memberService.getAll(member);
		
		System.out.println("xxxx : memberService.getAll(member)");
		
		// memberList가 비어있다면 ResponseEntity에 HTTP상태코드를 NO_CONTENT를 담아 응답으로 보낸다.
		if (memberList.isEmpty()) {
			
			// ResponseEntity는 Spring4에 나온 클래스 (리턴타입)로 @ModelAttribute와 같은 기능을하는데
			// 사용하는 이유는 결과 데이터와 HTTP 상태코드를 직접적으로 제어할수있어 편리하다.
			return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		}
		
		// memberList가 비어있지 않다면 ResponseEntity에 memberList와 HTTP상태코드 OK를 응답으로 보낸다.
		return new ResponseEntity<List<Member>>(memberList,HttpStatus.OK);
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.GET)
	
	// @PathVariable uri에 있는 데이터를 가져와 변수에 담는다. 이때 {이름}과 (@pathVariable int 이름) 
	// 이름과 이름끼리 같다면 @PathVariable("이름") 이라고 적어줄 필요없이 변수타입과 변수명만 적어주어도 된다.
	public ResponseEntity<Member> getMemberById(@PathVariable int id) {
		
		// @PathVariable로 받은 id를 사용해 id값이 매칭되는 멤버의 데이터를 가져온다.
		Member member = memberService.getById(id);
		
		// member가 null 이면 ResponseEntity에 HTTP상태코드 NO_CONTENT를 담아 응답으로 보낸다.
		if (member == null) {
			return new ResponseEntity<Member>(HttpStatus.NO_CONTENT);
		}
		
		// member가 null이 아니면 ResponseEntity에 member객체와 HTTP상태코드 OK를 담아 보낸다.
		return new ResponseEntity<Member>(member,HttpStatus.OK);
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Member> deleteMemberById(@PathVariable int id){
		
		// @PathVariable을 사용해 uri에 있는 id값을 가져와 변수에 담은후
		// id 데이터에 매칭되는 member 데이터를 가져온다.
		Member member = memberService.getById(id);
		
		// member가 null이면 HTTP상태코드를 NOT_FOUND로 한 후에 응답으로 보낸다.
		if (member == null) {
			System.out.println(id+"로 등록된 사람이 없습니다.");
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		
		// member가 null이 아니면 id값에 매칭되는 데이터를 지운다. 
		memberService.deleteById(id);
		
		// member가 지워졌기때문에 NO_CONTENT로 상태코드 응답을 보낸다.
		return new ResponseEntity<Member>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="/member", method=RequestMethod.POST)
	
	// @RequestBody는 POST로 요청과 함께 들어온 데이터를 가져온 후 객체로 전환되게 하는 어노테이션이다.
	// UriComponentsBuilder는 객체 내부에 있는 데이터를 Uri로 전환해 응답 uri로 되돌려 보내줄 수 있다.
	public ResponseEntity<Void> addMember(@RequestBody Member member, UriComponentsBuilder ucBulider){
		System.out.println("Creating Member" + member);
		
		// 등록하려는 멤버와 같은 멤버가 있으면 true 아니면 false를 반환한다.
		// 중복 데이터 조회
		if (memberService.isExist(member)) {
			System.out.println("A Member with name " + member.getName() + " already exist");
			
			// 중복되는 데이터가 있으면 HTTP상태코드를 CONFLICT로 변환 후 응답을 보낸다.
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		// 중복되는 데이터가 없으면 member를 생성한다. 
		memberService.create(member);
		
		// HttpHeaders는 Http에 header영역의 상태를 변환시킬수 있는 객체이다.
		HttpHeaders headers = new HttpHeaders();
		
		// header의 Location을 ucBulider를 통해 uri를 /member/{등록된 멤버의 이름}으로 변환하는데.
		// 이렇게 Location을 전환해주면 등록된 사용자가 누구인지 응답으로도 볼 수 있다.
		headers.setLocation(ucBulider.path("/member/{name}").buildAndExpand(member.getName()).toUri());
		
		// 헤더정보를 변환한 후 에 ResponseEntity에 header정보와 HTTP상태를 CREATED로 전환한 후 응답으로 보낸다.
		return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Member> updateMember(@PathVariable int id, @RequestBody Member member) {
		System.out.println("Update Member" + id);
		
		// @PathVariable을 사용에 uri상에 있는 id를 받아 데이터를 변경할 member를 찾는다.
		Member currentMember = memberService.getById(id);
		
		// 데이터를 변경할 member가 null이면
		// HTTP상태코드를 NOT_FOUND로 변경후 응답을 보낸다.
		if (currentMember == null) {
			System.out.println("Member with id" + id +" not fount");
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		
		// @RequestBody를 통해 객체로 전환된 데이터를 변경하려고 찾은 member객체에 데이터를 변경한다.
		currentMember.setName(member.getName());
		currentMember.setBelong(member.getBelong());
		
		// 변경할 데이터가 완성되면 데이터를 변경한다.
		memberService.update(currentMember);
		
		// 데이터를 변경한 후 ResponseEntity를 사용해 변경할 member 객체와 HTTP 상태코드를 OK로 변경한 후 응답으로 보낸다.
		return new ResponseEntity<Member>(currentMember,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/member/{page}/{size}",method=RequestMethod.GET)
	public ResponseEntity<List<Member>> getList(@PathVariable int page, @PathVariable int size){
		System.out.println("xxxx : getList");
		System.out.println("xxxx : page : " +page + ", size : " + size);

		Member member = new Member();
		member.setSize(size);
		member.setPage(page);
		
		List<Member> memberList = memberService.getAll(member);
		
		return new ResponseEntity<List<Member>>(memberList,HttpStatus.OK);
	}
	
	@RequestMapping(value="/member/type/{type}/word/{word}", method=RequestMethod.GET)
	public ResponseEntity<List<Member>> getSearchList(@PathVariable String type, @PathVariable String word) {
		System.out.println("xxxx : getSearchList");
		System.out.println("xxxx : type : " + type + ", word : " + word );
		
		SearchType search = new SearchType();
		
		search.setType(type);
		search.setWord(word);
		
		System.out.println("xxxx : search " + search);
		
		List<Member> memberList = memberService.search(search);
		
		if (memberList.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Member>>(memberList,HttpStatus.OK);
		
	}
	
	
}
