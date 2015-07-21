package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Test05.
 */
@Entity
@Table(name = "TEST05")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Test05 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "qwer")
    private String qwer;

    @Column(name = "asdf")
    private String asdf;

    @Column(name = "zxcv")
    private String zxcv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQwer() {
        return qwer;
    }

    public void setQwer(String qwer) {
        this.qwer = qwer;
    }

    public String getAsdf() {
        return asdf;
    }

    public void setAsdf(String asdf) {
        this.asdf = asdf;
    }

    public String getZxcv() {
        return zxcv;
    }

    public void setZxcv(String zxcv) {
        this.zxcv = zxcv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Test05 test05 = (Test05) o;

        if ( ! Objects.equals(id, test05.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Test05{" +
                "id=" + id +
                ", qwer='" + qwer + "'" +
                ", asdf='" + asdf + "'" +
                ", zxcv='" + zxcv + "'" +
                '}';
    }
}
