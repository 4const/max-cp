package ru.nstu.max.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Recruit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private Date dateOfBirth;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private MaritalStatus maritalStatus;

	public Recruit() {
	}

	public Recruit(Integer id, String firstName, String lastName, Date dateOfBirth, String address, MaritalStatus maritalStatus) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.maritalStatus = maritalStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Recruit)) {
			return false;
		}

		Recruit recruit = (Recruit)o;

		if (address != null ? !address.equals(recruit.address) : recruit.address != null) {
			return false;
		}
		if (dateOfBirth != null ? !dateOfBirth.equals(recruit.dateOfBirth) : recruit.dateOfBirth != null) {
			return false;
		}
		if (firstName != null ? !firstName.equals(recruit.firstName) : recruit.firstName != null) {
			return false;
		}
		if (id != null ? !id.equals(recruit.id) : recruit.id != null) {
			return false;
		}
		if (lastName != null ? !lastName.equals(recruit.lastName) : recruit.lastName != null) {
			return false;
		}
		if (maritalStatus != recruit.maritalStatus) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (maritalStatus != null ? maritalStatus.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Recruit{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", dateOfBirth=" + dateOfBirth +
			", address='" + address + '\'' +
			", maritalStatus=" + maritalStatus +
			'}';
	}
}
