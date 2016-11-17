package org.flywind.widgets.test.entities.example;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.flywind.widgets.test.entities.base.FBase;

@Entity
@Table(name="td_example")
public class Example extends FBase {
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="javaClassName")
	private static final long serialVersionUID = -1036802063838419423L;
	
	private int companyId;
	
	private String companyName;
	
	@Column(name="user_name",length=20)
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="company_name",length=20)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name="company_id",length=3)
	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

}
