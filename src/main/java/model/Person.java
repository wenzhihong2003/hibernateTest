package model;

/**
 * User: wenzhihong
 * Date: 13-11-25
 * Time: 下午5:47
 */
public class Person {
    private Long id;

    private String name;

    private IdCard idCard;

    public Person() {
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

    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }
}
