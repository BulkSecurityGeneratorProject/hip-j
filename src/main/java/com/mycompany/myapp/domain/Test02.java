package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Test02.
 */
@Entity
@Table(name = "TEST02")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Test02 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "asdf")
    private String asdf;

    @Column(name = "zxcv")
    private String zxcv;

    @Column(name = "qwer")
    private String qwer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getQwer() {
        return qwer;
    }

    public void setQwer(String qwer) {
        this.qwer = qwer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Test02 test02 = (Test02) o;

        if ( ! Objects.equals(id, test02.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Test02{" +
                "id=" + id +
                ", asdf='" + asdf + "'" +
                ", zxcv='" + zxcv + "'" +
                ", qwer='" + qwer + "'" +
                '}';
    }
}
