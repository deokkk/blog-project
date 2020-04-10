package blogProject.dao;

import java.sql.*;
import java.util.*;

import blogProject.commons.DBUtil;
import blogProject.vo.Subject;

public class SubjectDao {
	public int deleteSubject(Connection conn, String subjectName) throws SQLException {
		String sql = "DELETE FROM subject WHERE subject_name=?";
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
	
	public int countSubject(Connection conn) throws SQLException {
		String sql = "SELECT COUNT(*) FROM subject";
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
	
	public void insertSubject(Connection conn, String subjectName) throws SQLException {
		String sql = "INSERT INTO subject(subject_name, subject_date) VALUES(?,now())";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, subjectName);
			stmt.executeUpdate();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public ArrayList<Subject> selectSubjectListAll(Connection conn) throws SQLException {
		ArrayList<Subject> subjectList = new ArrayList<Subject>();
		String sql = "SELECT subject_name FROM subject ORDER BY subject_name";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			System.out.println(stmt + " <--stmt");
			rs = stmt.executeQuery();
			while(rs.next()) {
				Subject subject = new Subject();
				subject.setSubjectName(rs.getString("subject_name"));
				subjectList.add(subject);
			}
			System.out.println(subjectList.size() + "subjectdao subject size");
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return subjectList;
	}
	
	public ArrayList<Subject> selectSubjectListAll(Connection conn, int beginRow, int rowPerPage) throws SQLException {
		ArrayList<Subject> subjectList = new ArrayList<Subject>();
		String sql = "SELECT subject_no, subject_name, subject_date FROM subject ORDER BY subject_no LIMIT ?,?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			System.out.println(stmt + " <--stmt");
			rs = stmt.executeQuery();
			while(rs.next()) {
				Subject subject = new Subject();
				subject.setSubjectNo(rs.getInt("subject_no"));
				subject.setSubjectName(rs.getString("subject_name"));
				subject.setSubjectDate(rs.getString("subject_date"));
				subjectList.add(subject);
			}
			System.out.println(subjectList.size() + "subjectdao subject size");
		} finally {
			DBUtil.close(rs, stmt, null);
		}
		return subjectList;
	}
}
