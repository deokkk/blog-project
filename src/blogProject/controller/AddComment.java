package blogProject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blogProject.service.CommentService;
import blogProject.vo.Comment;

@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private CommentService commentService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int postsNo = Integer.parseInt(request.getParameter("postsNo"));
		System.out.println(postsNo + " <---postsNo");
		String memberId = request.getParameter("memberId");
		System.out.println(memberId + " <--memberId");
		String commentContent = request.getParameter("commentContent");
		System.out.println(commentContent + " <--commentContent");
		Comment comment = new Comment();
		comment.setCommentContent(commentContent);
		comment.setMemberId(memberId);
		comment.setPostsNo(postsNo);
		
		commentService = new CommentService();
		commentService.addComment(comment);
		response.sendRedirect(request.getContextPath()+"/PostsDetail?postsNo="+postsNo);
	}

}
