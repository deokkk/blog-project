package blogProject.dao;

import java.sql.*;
import java.util.*;

import blogProject.commons.DBUtil;
import blogProject.vo.Member;

public class MemberDao {
	
	public int countMember(Connection conn) throws SQLException {
		String sql = "SELECT COUNT(*) FROM member";
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
	
	public void updateMember(Connection conn, Member member) throws SQLException {
		String sql = "UPDATE member SET member_pw=? WHERE member_id=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberPw());
			stmt.setString(2, member.getMemberId());
			stmt.executeUpdate();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateMemberLevel(Connection conn, Member member) throws SQLException {
		String sql = "UPDATE member SET member_level=? WHERE member_id=?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, member.getMemberLevel());
			stmt.setString(2, member.getMemberId());
			stmt.executeUpdate();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Member> selectMemberListAll(Connection conn, int beginRow, int rowPerPage) throws SQLException {
		String sql = "SELECT member_no, member_id, member_pw, member_level, member_date FROM member ORDER BY member_level LIMIT ?,?";
		ArrayList<Member> memberList = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			memberList = new ArrayList<Member>();
			while(rs.next()) {
				Member member = new Member();
				member.setMemberDate(rs.getString("member_date"));
				member.setMemberId(rs.getString("member_id"));
				member.setMemberLevel(rs.getInt("member_level"));
				member.setMemberNo(rs.getInt("member_no"));
				member.setMemberPw(rs.getString("member_pw"));
				memberList.add(member);
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return memberList;
	}
	
	public boolean searchId(Connection conn, String memberId) throws SQLException {
		String sql = "SELECT mi FROM (SELECT memberid mi FROM memberid UNION SELECT member_id mi FROM member) t WHERE t.mi=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				return false;
			}
		} finally {
			DBUtil.close(rs, stmt, conn);
		}
		return true;
	}
	
	public void insertMember(Connection conn, Member member) throws SQLException {
		String sql = "INSERT INTO member(member_id, member_pw, member_level, member_date) VALUES(?,?,10,now())";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			stmt.executeUpdate();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int deleteMember(Connection conn, Member member) throws SQLException {
		String sql = "DELETE FROM member WHERE member_id=? AND member_pw=?";
		int row = 0;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			System.out.println(stmt + " <-- stmt");
			row = stmt.executeUpdate();
		} finally {
			DBUtil.close(null, stmt, null);
		}
		System.out.println(row + " <-- row");
		return row;
	}
	
	public Member selectMemberOne(Connection conn, String memberId) throws SQLException {
		Member member = null;
		String sql = "SELECT member_no, member_id, member_pw, member_level, member_date FROM member WHERE member_id=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				member = new Member();
				member.setMemberNo(rs.getInt("member_no"));
				member.setMemberId(rs.getString("member_id"));
				member.setMemberPw(rs.getString("member_pw"));
				member.setMemberLevel(rs.getInt("member_level"));
				member.setMemberDate(rs.getString("member_date"));
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return member;
	}
	
	public Member selectMemberOne(Connection conn, Member member) throws SQLException {
		Member returnMember = null;
		String sql = "SELECT member_id, member_level FROM member WHERE member_id=? AND member_pw=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnMember = new Member();
				returnMember.setMemberId(rs.getString("member_id"));
				returnMember.setMemberLevel(rs.getInt("member_level"));
			}
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return returnMember;
	}
}
