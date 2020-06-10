<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<title>postsList</title>
<meta charset="UTF-8">
<style>
    body {
        padding: 0;
        margin: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
        background-position: 0 0;
        background-repeat: no-repeat;
        background-attachment: fixed;
        background-size: cover;
        position: relative;
        overflow-y: auto;
        font-family: "Lato", sans-serif;
    }
    
    #aside {
    	padding-top: 30px;
        width: 280px;
        height: 3000px;
        position: fixed;
        background: #ECE0F8;
        overflow: hidden;
        float: left;
    }
    
    #section {
        margin-left: 300px;
        background: white;
    }

	.sidenav a {
		padding: 8px 8px 8px 32px;
		text-decoration: none;
		font-size: 25px;
		color: #818181;
		display: block;
	}
	
	.sidenav a:hover {
		color: #f1f1f1;
	}
	
	.btn {
		background-color: #ECE0F8;
		border-color: #ECE0F8;
		color: #818181;
	}
	
	.btn:hover {
		background-color: #E3CEF6;
		color: white;
	}
	
	td, th {
		text-align: center;
	}
	
	.page-item.active .page-link {
		background-color: #ECE0F8;
		border-color: #ECE0F8;
		color: black;
	}
	
	.page-link {
		color: black;
	}
	
	a {
		color: #dd9bff;
	}
	
	a:hover {
		color: #E3CEF6;
	}
	
	.paddingTop {
		padding-top: 30px;
	}
	
	.marginTop {
		margin-top: 30px;
	}
	
	.alignRight {
		text-align: right;
	}
</style>
</head>
<body>
    <div class="container-fluid" id="aside">
         <jsp:include page="/WEB-INF/views/inc/aside.jsp"></jsp:include>
    </div>
    <div class="container-fluid row" id="section">
    	<div class="col-1"></div>
    	<div class="col-7">
	        <h1 class="display-4 paddingTop">${subjectName} 게시판</h1>
        	<div>
	            <table class="table table-hover marginTop">
	            	<thead class="thead-light">
	            		<tr>
		            		<th>POSTS NO</th>
		            		<th>MEMBER ID</th>
		            		<th>SUBJECT NAME</th>
		            		<th>POSTS TITLE</th>
		            		<th>POSTS DATE</th>
		            	</tr>
		            </thead>
		            <tbody>
		            	<c:forEach var="posts" items="${postsList}">
		            		<tr>
		            			<td>${posts.postsNo}</td>
		            			<td>${posts.memberId}</td>
		            			<td>${posts.subjectName}</td>
		            			<td><a href="${pageContext.request.contextPath}/PostsDetail?postsNo=${posts.postsNo}">${posts.postsTitle}</a></td>
		            			<td>${posts.postsDate}</td>
		            		</tr>
		            	</c:forEach>
	            	</tbody>
	            </table>
	        </div>
	        <!-- 페이징 -->
	        <c:if test="${page.totalRow > 0}">
		        <div>
		        	<ul class="pagination"  style="justify-content: center;">
						<c:if test="${page.currentPageGroup>1 }">
							<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/PostsList?subjectName=${subjectName}&currentPage=${page.currentPageGroup-page.pagePerGroup}"><i class="fas fa-chevron-left"></i></a></li>
						</c:if>
						<c:set var="doneLoop" value="false"/>
						<c:forEach begin="${page.currentPageGroup}" end="${page.currentPageGroup+page.pagePerGroup-1}" step="1" varStatus="stats">
							<c:if test="${not doneLoop}">
								<c:if test="${(stats.index)==page.currentPage}">
									<li class="page-item active"><a class="page-link">${page.currentPage}</a></li>
								</c:if>
								<c:if test="${(stats.index)!=page.currentPage}">
									<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/PostsList?subjectName=${subjectName}&currentPage=${(stats.index)}">${(stats.index)}</a></li>
								</c:if>
								<c:if test="${stats.index==page.lastPage}">
									<c:set var="doneLoop" value="true"/>
								</c:if>
							</c:if>
						</c:forEach>
						<c:if test="${page.currentPageGroup<page.lastPageGroup }">
							<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/PostsList?subjectName=${subjectName}&currentPage=${page.currentPageGroup+page.pagePerGroup}"><i class="fas fa-chevron-right"></i></a></li>
						</c:if>
					</ul>
		        </div>
	        </c:if>
	    </div>
	<div class="col-4"></div>
    </div>
</body>
</html>