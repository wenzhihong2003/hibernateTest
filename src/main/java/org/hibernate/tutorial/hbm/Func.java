package org.hibernate.tutorial.hbm;

import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;

/**
 * User: wenzhihong
 * Date: 10/28/13
 * Time: 4:58 PM
 */
public class Func implements Serializable {
    private Long id;

    private String name;

    private String url;

    private Func parentFunc;

    private Set<Func> childrenFuncs = Sets.newHashSet();

    public Func() {
    }

    public Func(String name, String url, Func parentFunc) {
        this.name = name;
        this.url = url;
        this.parentFunc = parentFunc;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Func getParentFunc() {
        return parentFunc;
    }

    public void setParentFunc(Func parentFunc) {
        this.parentFunc = parentFunc;
    }

    public Set<Func> getChildrenFuncs() {
        return childrenFuncs;
    }

    public void setChildrenFuncs(Set<Func> childrenFuncs) {
        this.childrenFuncs = childrenFuncs;
    }
}
