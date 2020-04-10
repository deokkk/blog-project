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
</head>
<title>postDetail</title>
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
	
	.page-item.active .page-link {
		background-color: #ECE0F8;
		border-color: #ECE0F8;
		color: black;
	}
	.page-link {
		color: black;
	}
	
	.paddingTop {
		padding-top: 30px;
	}
	
	.paddingLeft {
		padding-left: 30px;
	}
	
	.marginTop {
		margin-top: 30px;
	}
	
	.alignRight {
		text-align: right;
	}
	
	.postsContent {
		height: 400px; 
		margin-top: 30px; 
		border: 1px solid #ced4da; 
		border-radius: 10px;"
	}
	
	.commentBtn {
		 height: 84px; 
		 width: 85px;
	}
	
	.commentUpdateBtn {
		height: 65px;
		width: 65px;
	}
	.comment {
		border: 1px solid #ced4da; 
		width: 1085px; 
		height: 90px; 
		border-radius: 10px;
	}
	
	.likeyBtn {
		color: blue;
		font-size: 24px;
	}
	
	.like {
		font-size: 24px;
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
	        <h1 class="display-4 paddingTop">게시글 상세보기</h1>
            <div>
            	<div class="row marginTop">
            		<div class="col-6"><h3>${pnm.posts.postsTitle}</h3></div>
            		<div class="col-4" style="padding-top: 10px;"><h5>분류 : ${pnm.posts.subjectName}</h5></div>
            		<div class="col-2">
            			<div>
	            			<a href="${pageContext.request.contextPath}/AddLikey?postsNo=${pnm.posts.postsNo}&likeyCk=1"><i class="far fa-thumbs-up likeyBtn"></i></a><span class="like"> ${likeyMap.goodCount}</span>
	            			<a href="${pageContext.request.contextPath}/AddLikey?postsNo=${pnm.posts.postsNo}&likeyCk=0"><i class="far fa-thumbs-down likeyBtn"></i></a><span class="like"> ${likeyMap.badCount}</span>
            			</div>
            			<div style="font-size: 20px;">
            				<c:if test="${loginMember.memberLevel<5}">
            				<a href="${pageContext.request.contextPath}/UpdatePosts?postsNo=${pnm.posts.postsNo}&memberId=${pnm.posts.memberId}">수정&nbsp;</a>
            				<a href="${pageContext.request.contextPath}/RemovePosts?postsNo=${pnm.posts.postsNo}&memberId=${pnm.posts.memberId}">삭제</a>
            				</c:if>
            			</div>
            		</div>
            	</div>
            	<div class="row">
            		<div class="col-6">작성자 : ${pnm.posts.memberId}  <c:if test="${pnm.member.memberLevel>5}">일반멤버</c:if><c:if test="${pnm.member.memberLevel<=5}">관리자</c:if></div>
            		<div class="col-6">작성시간 : ${pnm.posts.postsDate}</div>
            	</div>
            	<div class="postsContent">
            		${pnm.posts.postsContent}
            	</div>
            </div>
            <div>
            	<div class="marginTop">
            		<form method="post" action="${pageContext.request.contextPath}/AddComment">
            			<input type="hidden" name="postsNo" value="${pnm.posts.postsNo}">
            			<input type="hidden" name="memberId" value="${loginMember.memberId}">
            			<div class="row">
	            			<div class="form-group col-11">
	            				<label for="commentContent">COMMENT</label>
	            				<c:if test="${loginMember==null}">
	            					<textarea class="form-control" rows="3" cols="100" name="commentContent" id="commentContent" readonly>로그인 후 작성하세요</textarea>
	            				</c:if>
	            				<c:if test="${loginMember!=null}">
	            					<textarea class="form-control" rows="3" cols="100" name="commentContent" id="commentContent"></textarea>
	            				</c:if>
	            			</div>
	            			<div class="col=1 marginTop">
	            				<c:if test="${loginMember!=null}">
	            					<button class="btn commentBtn" type="submit">작성</button>
	            				</c:if>
	            			</div>
	            		</div>
            		</form>
            	</div>
            	<div>
	            	<c:forEach var="comment" items="${commentList}">
		            	<div class="comment" style="margin-bottom: 5px;">
			            	<div class="row">
			            		<div class="col-5">작성자 : ${comment.memberId}</div>
			            		<div class="col-5">작성시간 : ${comment.commentDate}</div>
			            		<div class="col-2" style="padding-left: 80px;">
			            			<c:if test="${loginMember!=null && update!='y'}">
				            			<a href="${pageContext.request.contextPath}/UpdateComment?postsNo=${comment.postsNo}&commentNo=${comment.commentNo}&memberId=${comment.memberId}&commentContent=${comment.commentContent}">수정</a>
				            			<a href="${pageContext.request.contextPath}/RemoveComment?postsNo=${comment.postsNo}&commentNo=${comment.commentNo}&memberId=${comment.memberId}">삭제</a>
			            			</c:if>
			            		</div>
			            	</div>
			            	<div>
			            		<c:if test="${update=='y' && commentOne.commentNo==comment.commentNo}">
			            			<form method="post" action="${pageContext.request.contextPath}/UpdateComment">
			            				<div class="row">
				            				<input type="hidden" name="commentNo" value="${commentOne.commentNo}">
				            				<input type="hidden" name="postsNo" value="${commentOne.postsNo}">
				            				<div class="col-11">
				            					<textarea class="form-control" rows="2" cols="100" name="commentContent">${commentOne.commentContent}</textarea>
				            				</div>
				            				<div class="col-1">
				            					<button class="btn commentUpdateBtn" type="submit">수정</button>
				            				</div>
				            			</div>
			            			</form>
			            		</c:if>
			            		<c:if test="${commentOne.commentNo!=comment.commentNo}">
			            			내용 : ${comment.commentContent}
			            		</c:if>
			            	</div>
		            	</div>
	            	</c:forEach>
            	</div>
            	<!-- 페이징 -->
		        <div class="marginTop">
		        	<ul class="pagination"  style="justify-content: center;">
						<c:if test="${page.currentPageGroup>1 }">
							<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/PostsDetail?postsNo=${pnm.posts.postsNo}&currentPage=${page.currentPageGroup-page.pagePerGroup}"><i class="fas fa-chevron-left"></i></a></li>
						</c:if>
						<c:set var="doneLoop" value="false"/>
						<c:forEach begin="${page.currentPageGroup}" end="${page.currentPageGroup+page.pagePerGroup-1}" step="1" varStatus="stats">
							<c:if test="${not doneLoop}">
								<c:if test="${(stats.index)==page.currentPage}">
									<li class="page-item active"><a class="page-link">${page.currentPage}</a></li>
								</c:if>
								<c:if test="${(stats.index)!=page.currentPage}">
									<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/PostsDetail?postsNo=${pnm.posts.postsNo}&currentPage=${(stats.index)}">${(stats.index)}</a></li>
								</c:if>
								<c:if test="${stats.index==page.lastPage}">
									<c:set var="doneLoop" value="true"/>
								</c:if>
							</c:if>
						</c:forEach>
						<c:if test="${page.currentPageGroup<page.lastPageGroup }">
							<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/PostsDetail?postsNo=${pnm.posts.postsNo}&currentPage=${page.currentPageGroup+page.pagePerGroup}"><i class="fas fa-chevron-right"></i></a></li>
						</c:if>
					</ul>
		        </div>
            </div>
		</div>
		<div class="col-4"></div>
    </div>
</body>
</html>