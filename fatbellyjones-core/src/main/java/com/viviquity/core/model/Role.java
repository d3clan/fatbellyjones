package com.viviquity.core.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends BaseEntity<Long> {

    private Long id;
    private String name;
    private List<User> users;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @return the roleNames
     */
    @OneToMany(cascade = { CascadeType.ALL })
    public List<User> getUsers() {
	return users;
    }

    /**
     * @param roleNames
     *            the roleNames to set
     */
    public void setUsers(List<User> users) {
	this.users = users;
    }

    @Column(name = "name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}
