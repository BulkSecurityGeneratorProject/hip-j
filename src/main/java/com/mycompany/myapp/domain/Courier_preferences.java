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
 * A Courier_preferences.
 */
@Entity
@Table(name = "COURIER_PREFERENCES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Courier_preferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

    @NotNull
    @Column(name = "shipment_limit", nullable = false)
    private Integer shipment_limit;

    @Column(name = "is_enabled")
    private Boolean is_enabled;

    @ManyToOne
    private Cluster source_cluster;

    @ManyToOne
    private Cluster destination_cluster;

    @ManyToOne
    private Payment_service_mapper payment_service_mapper;

    @ManyToOne
    private Courier courier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getShipment_limit() {
        return shipment_limit;
    }

    public void setShipment_limit(Integer shipment_limit) {
        this.shipment_limit = shipment_limit;
    }

    public Boolean getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public Cluster getSource_cluster() {
        return source_cluster;
    }

    public void setSource_cluster(Cluster cluster) {
        this.source_cluster = cluster;
    }

    public Cluster getDestination_cluster() {
        return destination_cluster;
    }

    public void setDestination_cluster(Cluster cluster) {
        this.destination_cluster = cluster;
    }

    public Payment_service_mapper getPayment_service_mapper() {
        return payment_service_mapper;
    }

    public void setPayment_service_mapper(Payment_service_mapper payment_service_mapper) {
        this.payment_service_mapper = payment_service_mapper;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Courier_preferences courier_preferences = (Courier_preferences) o;

        if ( ! Objects.equals(id, courier_preferences.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Courier_preferences{" +
                "id=" + id +
                ", priority='" + priority + "'" +
                ", shipment_limit='" + shipment_limit + "'" +
                ", is_enabled='" + is_enabled + "'" +
                '}';
    }
}
