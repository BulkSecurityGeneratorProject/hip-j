package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OneTwo.
 */
@Entity
@Table(name = "ONETWO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OneTwo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "one")
    private String one;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OneTwo oneTwo = (OneTwo) o;

        if ( ! Objects.equals(id, oneTwo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OneTwo{" +
                "id=" + id +
                ", one='" + one + "'" +
                '}';
    }
}
