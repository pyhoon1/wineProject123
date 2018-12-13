<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	
</script>
<script type="text/javascript">
	function User() {
		location.href = "adminUserList.do";
	}
	function Product() {
		location.href = "adminProductList.do";
	}
	function MakeMatchFood() {
		location.href = "adminMakeMatchFoodForm.do";
	}
	function MakeWine(){
		location.href ="adminMakeWineForm.do";
	}
</script>
<body>
	<button onclick="User();">유저 관리</button>
	<button onclick="Product();">상품 관리</button>
	<button onclick="Review();">리뷰 목록</button>
	<button onclick="Payment();">결제 목록</button>
	<table>
		<caption>와인 목록</caption>
		<tr>
			<td>생산자</td>
			<td>포도 품종</td>
			<td>와인 종류</td>
			<td>상품 명</td>
			<td>와인 설명</td>
			<td>브랜드 설명</td>
			<td>가격</td>
			<td>상품 원산지</td>
			<td>상품 년도</td>
			<td>궁합 좋은 음식</td>
			<td>용량</td>
			<td>온도</td>
		</tr>
		<tr>
			<c:forEach items="${productList}" var="product">
				<td>${product.producer}</td>
				<td>${product.variety}</td>
				<td>${product.wineKinds}</td>
				<td>${product.productName}</td>
				<td>${product.wineEx}</td>
				<td>${product.brandEx}</td>
				<td>${product.price}</td>
				<td>${product.nation}</td>
				<td>${product.year}</td>
				<td>${product.matchFoodName}</td>
				<td>${product.weight}</td>
				<td>${product.temperature}</td>
			</c:forEach>
		</tr>
	</table>
	<input type="button" value="와인 등록" onclick="MakeWine()">
	<table>
		<caption>부가 상품 목록</caption>
		<tr>
			<td>음식명</td>
			<td>가격</td>
			<td>원산지</td>
			<td>용량</td>
			<td>설명</td>
		</tr>
		<tr>
			<c:forEach items="" varStatus="matchFood">
				<td>${matchFood.matchFoodName}</td>
				<td>${matchFood.price}</td>
				<td>${matchFood.nation}</td>
				<td>${matchFood.weight}</td>
				<td>${matchFood.ex}</td>		
			</c:forEach>
		</tr>
	</table>
	<input type="button" value="부가 상품 등록" onclick="MakeMatchFood()">
</body>
</html>