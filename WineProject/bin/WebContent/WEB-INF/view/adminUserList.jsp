<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	function User() {
		location.href="adminUserList.do";
	}
	function Product(){
		location.href="productList.do";
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>Insert title here</title>
</head>
<body>
<button onclick="User();">유저 관리</button>
<button onclick="Product();">상품 관리</button>
<button onclick="Review();">리뷰 목록</button>
<button onclick="Payment();">결제 목록</button>
	<table>
		<tr>
			<td>가입 아이디</td>
			<td>가입 비밀번호</td>
			<td>성명</td>
			<td>이메일</td>
			<td>주소</td>
			<td>전화번호</td>
			<td>생년월일</td>
			<td>등급</td>
			<td>총 구입액</td>
			<td>가입일</td>
		</tr>
		<c:forEach items="${userList}" var="user">
			<tr>
			<td><a href="adminUserView.do?userId=${user.userId}">${user.loginId}</a></td>
			<td>${user.password}</td>
			<td>${user.userName}</td>
			<td>${user.email}</td>
			<td>${user.address}</td>
			<td>${user.phone}</td>
			<td>${user.birth}</td>
			<td>${user.rating}</td>
			<td>${user.totalAmount}</td>
			<td>${user.wdate}</td>	
			</tr>
		</c:forEach>


	</table>

</body>
</html>