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
 * A Dest_pincode.
 */
@Entity
@Table(name = "DEST_PINCODE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dest_pincode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6, max = 6)
    @Column(name = "pincode", length = 6, nullable = false)
    private String pincode;

    @Column(name = "district")
    private String district;

    @ManyToOne
    private City pincode_city;

    @ManyToOne
    private State pincode_state;

    @ManyToOne
    private Region pincode_region;

    @ManyToOne
    private Country pincode_country;

    @ManyToOne
    private Cluster pincode_cluster;

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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public City getPincode_city() {
        return pincode_city;
    }

    public void setPincode_city(City city) {
        this.pincode_city = city;
    }

    public State getPincode_state() {
        return pincode_state;
    }

    public void setPincode_state(State state) {
        this.pincode_state = state;
    }

    public Region getPincode_region() {
        return pincode_region;
    }

    public void setPincode_region(Region region) {
        this.pincode_region = region;
    }

    public Country getPincode_country() {
        return pincode_country;
    }

    public void setPincode_country(Country country) {
        this.pincode_country = country;
    }

    public Cluster getPincode_cluster() {
        return pincode_cluster;
    }

    public void setPincode_cluster(Cluster cluster) {
        this.pincode_cluster = cluster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Dest_pincode dest_pincode = (Dest_pincode) o;

        if ( ! Objects.equals(id, dest_pincode.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dest_pincode{" +
                "id=" + id +
                ", pincode='" + pincode + "'" +
                ", district='" + district + "'" +
                '}';
    }
}
