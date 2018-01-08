<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	$(function(){
		$.ajax({
			type:'get',
			datatype:'json',
			url:'/member',
			success:function(data){
				var htmlContent = '';
				$(data).each(function(index,item){
					htmlContent += '<li><a href="#" id='+item.id+'>이름 : '+item.name+', 소속사 : '+item.belong+'</a></li>'
				});
				$('#memberList').append(htmlContent);
			},
			error:function(){
				$('#id').append('<li>조회된 멤버가 없습니다.</li>');
			}
		});
	});
</script>
</head>
<body>
	<h1>RESTFul Api Test</h1>
	<ul id="memberList">
	</ul>
</body>
</html>