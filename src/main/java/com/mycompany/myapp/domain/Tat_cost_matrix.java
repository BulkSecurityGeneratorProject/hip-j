package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tat_cost_matrix.
 */
@Entity
@Table(name = "TAT_COST_MATRIX")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tat_cost_matrix implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "basic_cost", precision=10, scale=2, nullable = false)
    private BigDecimal basic_cost;

    @NotNull
    @Column(name = "fixed_addon_cost", precision=10, scale=2, nullable = false)
    private BigDecimal fixed_addon_cost;

    @NotNull
    @Column(name = "variable_addon_percent", precision=10, scale=2, nullable = false)
    private BigDecimal variable_addon_percent;

    @NotNull
    @Column(name = "tat", nullable = false)
    private Long tat;

    @ManyToOne
    private City source_city;

    @ManyToOne
    private City destination_city;

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

    public BigDecimal getBasic_cost() {
        return basic_cost;
    }

    public void setBasic_cost(BigDecimal basic_cost) {
        this.basic_cost = basic_cost;
    }

    public BigDecimal getFixed_addon_cost() {
        return fixed_addon_cost;
    }

    public void setFixed_addon_cost(BigDecimal fixed_addon_cost) {
        this.fixed_addon_cost = fixed_addon_cost;
    }

    public BigDecimal getVariable_addon_percent() {
        return variable_addon_percent;
    }

    public void setVariable_addon_percent(BigDecimal variable_addon_percent) {
        this.variable_addon_percent = variable_addon_percent;
    }

    public Long getTat() {
        return tat;
    }

    public void setTat(Long tat) {
        this.tat = tat;
    }

    public City getSource_city() {
        return source_city;
    }

    public void setSource_city(City city) {
        this.source_city = city;
    }

    public City getDestination_city() {
        return destination_city;
    }

    public void setDestination_city(City city) {
        this.destination_city = city;
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

        Tat_cost_matrix tat_cost_matrix = (Tat_cost_matrix) o;

        if ( ! Objects.equals(id, tat_cost_matrix.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tat_cost_matrix{" +
                "id=" + id +
                ", basic_cost='" + basic_cost + "'" +
                ", fixed_addon_cost='" + fixed_addon_cost + "'" +
                ", variable_addon_percent='" + variable_addon_percent + "'" +
                ", tat='" + tat + "'" +
                '}';
    }
}
