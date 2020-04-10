package blogProject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import blogProject.commons.DBUtil;
import blogProject.vo.Comment;

public class CommentDao {
	
	public void updateComment(Connection conn, Comment comment) throws SQLException {
		String sql = "UPDATE comment SET comment_content=?, comment_date=now() WHERE comment_no=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, comment.getCommentContent());
			stmt.setInt(2, comment.getCommentNo());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	public int deleteCommentByPosts (Connection conn, int postsNo) throws SQLException {
		String sql = "DELETE FROM comment WHERE posts_no=?";
		PreparedStatement stmt = null;
		int row = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postsNo);
			row = stmt.executeUpdate();
		} finally {
			stmt.close();
		}
		return row;
	}
	
	public int deleteComment(Connection conn, String memberId) throws SQLException {
		String sql = "DELETE FROM comment WHERE member_id=?";
		int row=0;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			row = stmt.executeUpdate();
		} finally {
			stmt.close();
		}
		return row;
	}
	
	public void deleteComment(Connection conn, int commentNo) throws SQLException {
		String sql = "DELETE FROM comment where comment_no=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, commentNo);
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	public int countComment(Connection conn, int postsNo) throws SQLException {
		String sql = "SELECT COUNT(*) FROM comment WHERE posts_no=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postsNo);
			rs = stmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return count;
	}
	
	public void insertComment(Connection conn, Comment comment) throws SQLException {
		String sql = "INSERT INTO comment(posts_no, member_id, comment_content, comment_date) VALUES(?,?,?,now())";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, comment.getPostsNo());
			stmt.setString(2, comment.getMemberId());
			stmt.setString(3, comment.getCommentContent());
			stmt.executeUpdate();
		} finally {
			DBUtil.close(rs, stmt, null);
		}
	}
	
	public ArrayList<Comment> selectCommentListAll(Connection conn, int postsNo, int beginRow, int rowPerPage) throws SQLException {
		String sql = "SELECT comment_no, posts_no, member_id, comment_content, comment_date FROM comment WHERE posts_no=? LIMIT ?,?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Comment> list = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postsNo);
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
			rs = stmt.executeQuery();
			list = new ArrayList<Comment>();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setCommentContent(rs.getString("comment_content"));
				comment.setCommentDate(rs.getString("comment_date"));
				comment.setCommentNo(rs.getInt("comment_no"));
				comment.setMemberId(rs.getString("member_id"));
				comment.setPostsNo(rs.getInt("posts_no"));
				list.add(comment);
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return list;
	}
}
