package blogProject.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.CommentService;
import blogProject.service.LikeyService;
import blogProject.service.PostsService;
import blogProject.vo.Comment;
import blogProject.vo.Member;
import blogProject.vo.postsAndMember;

@WebServlet("/UpdateComment")
public class UpdateComment extends HttpServlet {
	private CommentService commentService;
	private PostsService postsService;
	private LikeyService likeyService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int commentNo = Integer.parseInt(request.getParameter("commentNo"));
		int postsNo = Integer.parseInt(request.getParameter("postsNo"));
		String memberId = request.getParameter("memberId");
		String commentContent = request.getParameter("commentContent");
		if(((Member)request.getSession().getAttribute("loginMember")).getMemberId().equals(memberId)) {
			System.out.println("작성자 == 로그인ID");
			Comment comment = new Comment();
			comment.setCommentContent(commentContent);
			comment.setCommentNo(commentNo);
			comment.setPostsNo(postsNo);
			request.setAttribute("commentOne", comment);
			
			int currentPage = 1;
			if(request.getParameter("currentPage")!=null) {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
			postsService = new PostsService();
			postsAndMember pnm = postsService.getPostsOne(postsNo);
			
			commentService = new CommentService();
			Map<String, Object> map = commentService.getCommentListAll(postsNo, currentPage);
			request.setAttribute("pnm", pnm);
			request.setAttribute("commentList", map.get("list"));
			request.setAttribute("page", map.get("page"));
			
			likeyService = new LikeyService();
			Map<String, Object> likeyMap = likeyService.countLikey(postsNo);
			request.setAttribute("likeyMap", likeyMap);
			
			request.setAttribute("update", "y");
			request.getRequestDispatcher("/WEB-INF/views/postsDetail.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int postsNo = Integer.parseInt(request.getParameter("postsNo"));
		int commentNo = Integer.parseInt(request.getParameter("commentNo"));
		String commentContent = request.getParameter("commentContent");
		Comment comment = new Comment();
		comment.setCommentContent(commentContent);
		comment.setCommentNo(commentNo);
		commentService.updateComment(comment);
		response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
	}

}
