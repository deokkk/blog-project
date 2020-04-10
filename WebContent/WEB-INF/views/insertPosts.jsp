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
<title>insertPosts</title>
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
    	<div class="col-5">
	        <h1 class="display-4 paddingTop">게시글 작성</h1>
	        <div>
	            <form method="post" action="${pageContext.request.contextPath}/AddPosts">
	            	<input type="hidden" name="memberId" value="${loginMember.memberId}">
	            	<div class="form-group">
	            		<label for="subjectName">SUBJECT NAME</label>
	            		<select class="form-control" name="subjectName" id="subjectName">
	            			<option value="">Subject 선택</option>
	            			<c:forEach var="subject" items="${subjectListAll}">
	            				<option value="${subject.subjectName}">${subject.subjectName}</option>
	            			</c:forEach>
	            		</select>
	            	</div>
	            	<div class="form-group">
	            		<label for="postsTitle">제목</label>
	            		<textarea class="form-control" rows="1" cols="100" name="postsTitle" id="postsTitle"></textarea>
	            	</div>
	            	<div class="form-group">
	            		<label for="postsContent">내용</label>
	            		<textarea class="form-control" rows="20" cols="100" name="postsContent" id="postsContent"></textarea>
	            	</div>
	            	<div class="alignRight">
	            		<button class="btn" type="submit">작성</button>
	            	</div>
	            </form>
	        </div>
	    </div>
	    <div class="col-6"></div>
    </div>
</body>
</html>