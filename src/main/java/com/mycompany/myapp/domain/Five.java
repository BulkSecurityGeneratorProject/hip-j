package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Five.
 */
@Entity
@Table(name = "FIVE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Five implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "a")
    private String a;

    @Column(name = "s")
    private String s;

    @Column(name = "d")
    private String d;

    @Column(name = "f")
    private String f;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Five five = (Five) o;

        if ( ! Objects.equals(id, five.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Five{" +
                "id=" + id +
                ", a='" + a + "'" +
                ", s='" + s + "'" +
                ", d='" + d + "'" +
                ", f='" + f + "'" +
                '}';
    }
}
