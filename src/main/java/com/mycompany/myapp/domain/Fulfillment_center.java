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
 * A Fulfillment_center.
 */
@Entity
@Table(name = "FULFILLMENT_CENTER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fulfillment_center implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "fcid", nullable = false)
    private String fcid;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "fc_type", nullable = false)
    private String fc_type;

    @NotNull
    @Column(name = "franchise", nullable = false)
    private String franchise;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(name = "pincode", length = 6, nullable = false)
    private String pincode;

    @NotNull
    @Column(name = "o2s_tat", nullable = false)
    private Integer o2s_tat;

    @Column(name = "is_enabled")
    private Boolean is_enabled;

    @ManyToOne
    private City fc_city;

    @ManyToOne
    private State fc_state;

    @ManyToOne
    private Cluster fc_cluster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFcid() {
        return fcid;
    }

    public void setFcid(String fcid) {
        this.fcid = fcid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFc_type() {
        return fc_type;
    }

    public void setFc_type(String fc_type) {
        this.fc_type = fc_type;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Integer geto2s_tat() {
        return o2s_tat;
    }

    public void seto2s_tat(Integer o2s_tat) {
        this.o2s_tat = o2s_tat;
    }

    public Boolean getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public City getFc_city() {
        return fc_city;
    }

    public void setFc_city(City city) {
        this.fc_city = city;
    }

    public State getFc_state() {
        return fc_state;
    }

    public void setFc_state(State state) {
        this.fc_state = state;
    }

    public Cluster getFc_cluster() {
        return fc_cluster;
    }

    public void setFc_cluster(Cluster cluster) {
        this.fc_cluster = cluster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Fulfillment_center fulfillment_center = (Fulfillment_center) o;

        if ( ! Objects.equals(id, fulfillment_center.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fulfillment_center{" +
                "id=" + id +
                ", fcid='" + fcid + "'" +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", fc_type='" + fc_type + "'" +
                ", franchise='" + franchise + "'" +
                ", address1='" + address1 + "'" +
                ", address2='" + address2 + "'" +
                ", address3='" + address3 + "'" +
                ", pincode='" + pincode + "'" +
                ", o2s_tat='" + o2s_tat + "'" +
                ", is_enabled='" + is_enabled + "'" +
                '}';
    }
}
