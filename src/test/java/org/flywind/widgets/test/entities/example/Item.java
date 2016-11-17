package org.flywind.widgets.test.entities.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.flywind.widgets.test.entities.base.FBase;

@Entity
@Table(name="td_item")
public class Item extends FBase{

	private static final long serialVersionUID = -1036802063838419424L;
	
	@Column(name="user_name",length=20)
	private String userName;
	
	@Column(name="parentId")
	private Long parentId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
