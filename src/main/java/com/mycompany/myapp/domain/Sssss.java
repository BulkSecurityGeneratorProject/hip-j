package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Sssss.
 */
@Entity
@Table(name = "SSSSS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sssss implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "wwewwe")
    private String wwewwe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWwewwe() {
        return wwewwe;
    }

    public void setWwewwe(String wwewwe) {
        this.wwewwe = wwewwe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Sssss sssss = (Sssss) o;

        if ( ! Objects.equals(id, sssss.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sssss{" +
                "id=" + id +
                ", wwewwe='" + wwewwe + "'" +
                '}';
    }
}
