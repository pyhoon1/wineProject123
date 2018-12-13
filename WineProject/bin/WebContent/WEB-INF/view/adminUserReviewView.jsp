<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr>
	<td>상품 명</td>
	<td>리뷰 내용</td>
	<td>리뷰 등록일자</td>
	<td>차단 여부</td>
</tr>
<c:set value="Y" var="state"/>
<c:forEach items="${reviewList}" var="review">
	<tr>
		<td>${review.productName}</td>
		<td>${review.content }</td>
		<td>${review.wdate }</td>
		<c:choose>
			<c:when test="${review.state == 'Y'}">		
					<td>X</td>
			</c:when>
			<c:otherwise>
				<td>O</td>
			</c:otherwise>
		</c:choose>	
		<c:choose>
			<c:when test="${review.state == 'Y'}">		 
					<td>
					<a href="warningReview.do?state=N&reviewId=${review.reviewId}&userId=${review.userId}">
						<button>리뷰 차단</button>
					</a></td>
			</c:when>
			<c:otherwise>
					<td>
					<a href="warningReview.do?state=Y&reviewId=${review.reviewId}&userId=${review.userId}">
						<button>차단 해제</button>
					</a></td>
			</c:otherwise>
		</c:choose>	
		
	</tr>
	
</c:forEach>