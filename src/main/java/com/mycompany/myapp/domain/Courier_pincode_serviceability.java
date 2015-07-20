package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Courier_pincode_serviceability.
 */
@Entity
@Table(name = "COURIER_PINCODE_SERVICEABILITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Courier_pincode_serviceability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(name = "pincode", length = 6, nullable = false)
    private String pincode;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @ManyToOne
    private Courier courier;

    @ManyToOne
    private Payment_service_mapper payment_service_mapper;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Payment_service_mapper getPayment_service_mapper() {
        return payment_service_mapper;
    }

    public void setPayment_service_mapper(Payment_service_mapper payment_service_mapper) {
        this.payment_service_mapper = payment_service_mapper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Courier_pincode_serviceability courier_pincode_serviceability = (Courier_pincode_serviceability) o;

        if ( ! Objects.equals(id, courier_pincode_serviceability.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Courier_pincode_serviceability{" +
                "id=" + id +
                ", pincode='" + pincode + "'" +
                ", capacity='" + capacity + "'" +
                '}';
    }
}
