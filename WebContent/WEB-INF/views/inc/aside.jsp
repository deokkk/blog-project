<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- subject (나라 입력) list 출력 -->

<div id="mySidenav" class="sidenav">
	<a href="${pageContext.request.contextPath}/Home">Home</a>
	<c:if test="${loginMember.memberLevel < 5}">
		<a href="${pageContext.request.contextPath}/Admin">관리자 페이지</a>
	</c:if>
	<c:forEach var="subject" items="${subjectListAll}">
		<div>
			<a href="${pageContext.request.contextPath}/PostsList?subjectName=${subject.subjectName}">${subject.subjectName} <span class="badge badge-pill badge-light">${subject.subjectCount}</span></a>
		</div>
	</c:forEach>
</div>
