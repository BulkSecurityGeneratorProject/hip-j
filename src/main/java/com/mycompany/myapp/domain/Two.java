package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Two.
 */
@Entity
@Table(name = "TWO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Two implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first")
    private String first;

    @Column(name = "last")
    private String last;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "oye")
    private String oye;

    @Column(name = "eyo")
    private String eyo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOye() {
        return oye;
    }

    public void setOye(String oye) {
        this.oye = oye;
    }

    public String getEyo() {
        return eyo;
    }

    public void setEyo(String eyo) {
        this.eyo = eyo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Two two = (Two) o;

        if ( ! Objects.equals(id, two.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Two{" +
                "id=" + id +
                ", first='" + first + "'" +
                ", last='" + last + "'" +
                ", age='" + age + "'" +
                ", email='" + email + "'" +
                ", oye='" + oye + "'" +
                ", eyo='" + eyo + "'" +
                '}';
    }
}
