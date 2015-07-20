package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OneThree.
 */
@Entity
@Table(name = "ONETHREE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OneThree implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "three_four")
    private String threeFour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThreeFour() {
        return threeFour;
    }

    public void setThreeFour(String threeFour) {
        this.threeFour = threeFour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OneThree oneThree = (OneThree) o;

        if ( ! Objects.equals(id, oneThree.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OneThree{" +
                "id=" + id +
                ", threeFour='" + threeFour + "'" +
                '}';
    }
}
