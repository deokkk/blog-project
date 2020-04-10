package blogProject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import blogProject.commons.DBUtil;
import blogProject.vo.Member;
import blogProject.vo.Posts;
import blogProject.vo.Subject;
import blogProject.vo.postsAndMember;

public class PostsDao {
	
	public ArrayList<Posts> selectPostsNoList(Connection conn, String subjectName) throws SQLException {
		String sql = "SELECT posts_no FROM posts WHERE subject_name=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Posts> list = new ArrayList<Posts>();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, subjectName);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Posts posts = new Posts();
				posts.setPostsNo(rs.getInt("posts_no"));
				list.add(posts);
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return list;
	}
	
	public int deletePostsBySubject(Connection conn, String subjectName) throws SQLException {
		String sql = "DELETE FROM posts WHERE subject_name=?";
		PreparedStatement stmt = null;
		int row = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, subjectName);
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
		return row;
	}
	
	public int deletePosts(Connection conn, int postsNo) throws SQLException {
		String sql = "DELETE FROM posts WHERE posts_no=?";
		PreparedStatement stmt = null;
		int row=0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postsNo);
			row=stmt.executeUpdate();
		} finally {
			stmt.close();
		}
		return row;
	}
	
	public void updatePosts(Connection conn, Posts posts) throws SQLException {
		String sql = "UPDATE posts SET subject_name=?, posts_title=?, posts_content=?, posts_date=now() WHERE posts_no=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, posts.getSubjectName());
			stmt.setString(2, posts.getPostsTitle());
			stmt.setString(3, posts.getPostsContent());
			stmt.setInt(4, posts.getPostsNo());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	public int deletePosts(Connection conn, String memberId) throws SQLException {
		int row = 0;
		String sql = "DELETE FROM posts WHERE member_id=?";
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
	
	public int countPostsBySubject(Connection conn, String subjectName) throws SQLException {
		String sql = "SELECT COUNT(*) cnt FROM posts WHERE subject_name=?";
		int count = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, subjectName);
			rs = stmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("cnt");
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return count;
	}
	
	public ArrayList<Subject> countPostsBySubject(Connection conn) throws SQLException {
		ArrayList<Subject> list = new ArrayList<Subject>();
		String sql = "SELECT subject_name, COUNT(subject_name) cnt FROM posts GROUP BY subject_name";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Subject subject = new Subject();
				subject.setSubjectName(rs.getString("subject_name"));
				subject.setSubjectCount(rs.getInt("cnt"));
				list.add(subject);
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return list;
	}
	
	public void insertPosts(Connection conn, Posts posts) throws SQLException {
		String sql = "INSERT INTO posts(subject_name, member_id, posts_title, posts_content, posts_date) VALUES(?,?,?,?,now())";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, posts.getSubjectName());
			stmt.setString(2, posts.getMemberId());
			stmt.setString(3, posts.getPostsTitle());
			stmt.setString(4, posts.getPostsContent());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	public int countPosts(Connection conn) throws SQLException {
		String sql = "SELECT COUNT(*) FROM posts";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int totalRow = 0;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				totalRow = rs.getInt(1);
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return totalRow;
	}
	
	public postsAndMember selectPostsOne(Connection conn, int postsNo) throws SQLException {
		String sql = "SELECT p.posts_no, p.subject_name, p.member_id, p.posts_title, p.posts_content, p.posts_date, m.member_date, m.member_id, m.member_level, m.member_no, m.member_pw FROM posts p INNER JOIN member m ON p.member_id=m.member_id WHERE p.posts_no=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		postsAndMember pnm = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postsNo);
			rs = stmt.executeQuery();
			pnm = new postsAndMember();
			if(rs.next()) {
				Posts posts = new Posts();
				posts.setMemberId(rs.getString("p.member_id"));
				posts.setPostsContent(rs.getString("p.posts_content"));
				posts.setPostsDate(rs.getString("p.posts_date"));
				posts.setPostsNo(rs.getInt("p.posts_no"));
				posts.setPostsTitle(rs.getString("p.posts_title"));
				posts.setSubjectName(rs.getString("p.subject_name"));
				pnm.setPosts(posts);
				
				Member member = new Member();
				member.setMemberDate(rs.getString("m.member_date"));
				member.setMemberId(rs.getString("m.member_id"));
				member.setMemberLevel(rs.getInt("m.member_level"));
				member.setMemberNo(rs.getInt("m.member_no"));
				member.setMemberPw(rs.getString("m.member_pw"));
				pnm.setMember(member);
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return pnm;
	}
	
	public ArrayList<Posts> selectPostsList(Connection conn, String subjectName, int beginRow, int rowPerPage) throws SQLException {
		String sql = "SELECT posts_no, subject_name, member_id, posts_title, posts_content, posts_date FROM posts WHERE subject_name=? LIMIT ?,?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Posts> postsList = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, subjectName);
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
			System.out.println(stmt + " <--selectPostsList stmt");
			rs = stmt.executeQuery();
			postsList = new ArrayList<Posts>();
			while(rs.next()) {
				Posts posts = new Posts();
				posts.setMemberId(rs.getString("member_id"));
				posts.setPostsContent(rs.getString("posts_content"));
				posts.setPostsDate(rs.getString("posts_date"));
				posts.setPostsNo(rs.getInt("posts_no"));
				posts.setPostsTitle(rs.getString("posts_title"));
				posts.setSubjectName(rs.getString("subject_name"));
				postsList.add(posts);
			}
			System.out.println(postsList.size() + "posts size");
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return postsList;
	}
	
	public ArrayList<Posts> selectPostsListAll(Connection conn, int beginRow, int rowPerPage) throws SQLException {
		String sql = "SELECT posts_no, subject_name, member_id, posts_title, posts_date FROM posts ORDER BY posts_no LIMIT ?,?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Posts> postList = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			postList = new ArrayList<Posts>();
			while(rs.next()) {
				Posts posts = new Posts();
				posts.setMemberId(rs.getString("member_id"));
				posts.setPostsDate(rs.getString("posts_date"));
				posts.setPostsNo(rs.getInt("posts_no"));
				posts.setPostsTitle(rs.getString("posts_title"));
				posts.setSubjectName(rs.getString("subject_name"));
				postList.add(posts);
			}
			System.out.println(postList.size() + "post size");
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return postList;
	}
}
