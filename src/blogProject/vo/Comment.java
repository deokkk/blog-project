package blogProject.vo;

public class Comment {
	private int commentNo;
	private int postsNo;
	private String memberId;
	private String commentContent;
	private String commentDate;
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public int getPostsNo() {
		return postsNo;
	}
	public void setPostsNo(int postsNo) {
		this.postsNo = postsNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
}
