package blogProject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import blogProject.commons.DBUtil;
import blogProject.vo.Likey;
import blogProject.vo.Posts;

public class LikeyDao {
	
	public int deleteLikey(Connection conn, int postsNo) throws SQLException {
		String sql = "DELETE FROM likey WHERE posts_no=?";
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
	
	public void updateLikey(Connection conn, Likey likey) throws SQLException {
		String sql = "UPDATE likey SET likey_ck=? WHERE posts_no=? AND member_id=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, likey.getLikeyCk());
			stmt.setInt(2, likey.getPostsNo());
			stmt.setString(3, likey.getMemberId());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	public int deleteLikey(Connection conn, String memberId) throws SQLException {
		String sql = "DELETE FROM likey WHERE member_id=?";
		PreparedStatement stmt = null;
		int row = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			row = stmt.executeUpdate();
		} finally {
			stmt.close();
		}
		return row;
	}
	
	public void deleteLikey(Connection conn, Likey likey) throws SQLException {
		String sql = "DELETE FROM likey WHERE posts_no=? AND member_id=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, likey.getPostsNo());
			stmt.setString(2, likey.getMemberId());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	public int isLikey(Connection conn, Likey likey) throws SQLException {
		String sql = "SELECT likey_ck FROM likey WHERE posts_no=? AND member_id=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int likeCk = 3;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, likey.getPostsNo());
			stmt.setString(2, likey.getMemberId());
			rs = stmt.executeQuery();
			if(rs.next()) {
				likeCk = rs.getInt("likey_ck");
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return likeCk;
	}
	
	public void insertLikey(Connection conn, Likey likey) throws SQLException {
		String sql = "INSERT INTO likey(posts_no, member_id, likey_ck, likey_date) VALUES(?,?,?,now())";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, likey.getPostsNo());
			stmt.setString(2, likey.getMemberId());
			stmt.setInt(3, likey.getLikeyCk());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
	}
	
	public Map<String, Object> countLikey(Connection conn, int postsNo) throws SQLException {
		//String sql = "SELECT COUNT(*) cnt FROM likey WHERE posts_no=? AND likey_ck=1";
		String sql = "SELECT likey_ck, COUNT(*) cnt FROM likey WHERE posts_no=? GROUP BY likey_ck ORDER BY likey_ck";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		int goodCount = 0;
		int badCount = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, postsNo);
			rs = stmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt("likey_ck")==0) {
					badCount = rs.getInt("cnt");
				}
				if(rs.getInt("likey_ck")==1) {
					goodCount = rs.getInt("cnt");
				}
			}
			map.put("goodCount", goodCount);
			map.put("badCount", badCount);
			
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return map;
	}
}
