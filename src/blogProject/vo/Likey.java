package blogProject.vo;

public class Likey {
	private int likeyNo;
	private int postsNo;
	private String memberId;
	private int likeyCk;
	private String likeyDate;
	public int getLikeyNo() {
		return likeyNo;
	}
	public void setLikeyNo(int likeyNo) {
		this.likeyNo = likeyNo;
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
	public int getLikeyCk() {
		return likeyCk;
	}
	public void setLikeyCk(int likeyCk) {
		this.likeyCk = likeyCk;
	}
	public String getLikeyDate() {
		return likeyDate;
	}
	public void setLikeyDate(String likeyDate) {
		this.likeyDate = likeyDate;
	}
}
