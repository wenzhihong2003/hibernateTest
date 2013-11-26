package model;

/**
 * User: wenzhihong
 * Date: 13-11-25
 * Time: 下午5:48
 */
public class IdCard {
    private Long id;

    private String cardNo;

    private Person person;

    public IdCard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
