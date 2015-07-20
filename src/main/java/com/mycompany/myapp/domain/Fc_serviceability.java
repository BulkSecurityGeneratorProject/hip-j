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
 * A Fc_serviceability.
 */
@Entity
@Table(name = "FC_SERVICEABILITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fc_serviceability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_enabled")
    private Boolean is_enabled;

    @NotNull
    @Column(name = "shipment_cutoff_time", nullable = false)
    private String shipment_cutoff_time;

    @ManyToOne
    private Fulfillment_center fc_id;

    @ManyToOne
    private Payment_service_mapper st_pt_id;

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

    public String getShipment_cutoff_time() {
        return shipment_cutoff_time;
    }

    public void setShipment_cutoff_time(String shipment_cutoff_time) {
        this.shipment_cutoff_time = shipment_cutoff_time;
    }

    public Fulfillment_center getFc_id() {
        return fc_id;
    }

    public void setFc_id(Fulfillment_center fulfillment_center) {
        this.fc_id = fulfillment_center;
    }

    public Payment_service_mapper getSt_pt_id() {
        return st_pt_id;
    }

    public void setSt_pt_id(Payment_service_mapper payment_service_mapper) {
        this.st_pt_id = payment_service_mapper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Fc_serviceability fc_serviceability = (Fc_serviceability) o;

        if ( ! Objects.equals(id, fc_serviceability.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fc_serviceability{" +
                "id=" + id +
                ", is_enabled='" + is_enabled + "'" +
                ", shipment_cutoff_time='" + shipment_cutoff_time + "'" +
                '}';
    }
}
