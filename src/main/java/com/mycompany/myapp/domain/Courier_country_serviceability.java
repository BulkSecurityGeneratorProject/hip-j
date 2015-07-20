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
 * A Courier_country_serviceability.
 */
@Entity
@Table(name = "COURIER_COUNTRY_SERVICEABILITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Courier_country_serviceability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_enabled")
    private Boolean is_enabled;

    @NotNull
    @Column(name = "shipment_capacity", nullable = false)
    private Integer shipment_capacity;

    @ManyToOne
    private Courier courier;

    @ManyToOne
    private Payment_service_mapper payment_service_mapper;

    @ManyToOne
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public Integer getShipment_capacity() {
        return shipment_capacity;
    }

    public void setShipment_capacity(Integer shipment_capacity) {
        this.shipment_capacity = shipment_capacity;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Courier_country_serviceability courier_country_serviceability = (Courier_country_serviceability) o;

        if ( ! Objects.equals(id, courier_country_serviceability.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Courier_country_serviceability{" +
                "id=" + id +
                ", is_enabled='" + is_enabled + "'" +
                ", shipment_capacity='" + shipment_capacity + "'" +
                '}';
    }
}
