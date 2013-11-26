package org.hibernate.tutorial.hbm;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * User: wenzhihong
 * Date: 11/5/13
 * Time: 2:39 PM
 */
public class Group {
    private Long id;

    private String name;

    private Set<Person> persons = Sets.newHashSet();

    private Boolean deleted =Boolean.TRUE;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public void addPerson(Person p) {
        persons.add(p);
        p.setGroup(this);
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
