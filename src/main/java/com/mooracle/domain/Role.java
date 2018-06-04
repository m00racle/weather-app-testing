package com.mooracle.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/** ENTRY 5: Build the Role Class
 *  1.  This class is to define Role @Entity in conjunction with the User class
 *  2.  We should build this first before building the User @Entity since it will be used by User as OneToOne
 *  3.  This role @Entity does not have its own column but it is part of other schema in the database.
 *  4.  The Role data will only came up in the role_id column in the User schema
 *  Patch -> add Role default constructor
 * */

@Entity
public class Role {
    //field declaration id
    @Id //use the javax.persistence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //field string role name (does not need a column)
    private String name;

    // Patch: default constructor for Role
    public Role(){
        this.id = null;
        this.name = null;
    }

    //Constructor (not default) but it is id will be null as initial value:
    public Role (String name){
        this.id = null;
        this.name = name;
    }

    //getters and setters for all fields:

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
