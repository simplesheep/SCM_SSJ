package simple.page;

import java.util.List;

import simple.entity.User;

public class UserPage {
	
	
	private Integer currentIndex;//��ǰҳ
	
	private Integer totalIndex;//��ҳ��
	
	private Integer totalCount; //�����ݼ�¼��
	
	private Integer pageSize = 5;//ÿҳ����������
	
	private List<User> users;//ÿҳ��ʾ������
	
	//��ʼҳ
	private Integer start;
	//��ֹλ
	private Integer end;
	public Integer getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(Integer currentIndex) {
		this.currentIndex = currentIndex;
	}
	public Integer getTotalIndex() {
		return totalIndex;
	}
	public void setTotalIndex(Integer totalIndex) {
		this.totalIndex = totalIndex;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	
	
	
	
}
