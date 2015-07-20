package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Payment_service_mapper.
 */
@Entity
@Table(name = "PAYMENT_SERVICE_MAPPER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Payment_service_mapper implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Payment_type pt_id;

    @ManyToOne
    private Service_type st_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Payment_type getPt_id() {
        return pt_id;
    }

    public void setPt_id(Payment_type payment_type) {
        this.pt_id = payment_type;
    }

    public Service_type getSt_id() {
        return st_id;
    }

    public void setSt_id(Service_type service_type) {
        this.st_id = service_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Payment_service_mapper payment_service_mapper = (Payment_service_mapper) o;

        if ( ! Objects.equals(id, payment_service_mapper.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Payment_service_mapper{" +
                "id=" + id +
                ", description='" + description + "'" +
                '}';
    }
}
