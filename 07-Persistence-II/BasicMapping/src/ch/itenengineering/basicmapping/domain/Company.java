package ch.itenengineering.basicmapping.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "BASIC_MAPPING_COMPANY")
@SecondaryTable(name = "BASIC_MAPPING_COMPANY_LOB")
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COMPANY_ID")
	protected int id;

	public Company() {
	}

	public Company(int id) {
		this();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();

		buf.append("\nCompany Id = ");
		buf.append(id);

		return buf.toString();
	}

} // end of class
