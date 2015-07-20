package com.mycompany.myapp.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mycompany.myapp.domain.util.CustomDateTimeDeserializer;
import com.mycompany.myapp.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Shipment_transactions.
 */
@Entity
@Table(name = "SHIPMENT_TRANSACTIONS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Shipment_transactions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "order_ref", nullable = false)
    private String orderRef;

    @NotNull
    @Column(name = "invoice_id", nullable = false)
    private String invoiceId;

    @NotNull
    @Column(name = "shipment_id", nullable = false)
    private String shipmentId;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @Column(name = "awb")
    private String awb;

    @Column(name = "status")
    private String status;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "time", nullable = false)
    private DateTime time;

    @Column(name = "lbh")
    private String lbh;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "in_scan_time", nullable = false)
    private DateTime in_scan_time;

    @Column(name = "description")
    private String description;

    @Column(name = "cost", precision=10, scale=2, nullable = false)
    private BigDecimal cost;

    @ManyToOne
    private Fulfillment_center fulfillment_center;

    @ManyToOne
    private Dest_pincode dest_pincode;

    @ManyToOne
    private Courier courier;

    @ManyToOne
    private Payment_service_mapper payment_service_mapper;

    @ManyToOne
    private Master_bag master_bag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAwb() {
        return awb;
    }

    public void setAwb(String awb) {
        this.awb = awb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public String getLbh() {
        return lbh;
    }

    public void setLbh(String lbh) {
        this.lbh = lbh;
    }

    public DateTime getIn_scan_time() {
        return in_scan_time;
    }

    public void setIn_scan_time(DateTime in_scan_time) {
        this.in_scan_time = in_scan_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Fulfillment_center getFulfillment_center() {
        return fulfillment_center;
    }

    public void setFulfillment_center(Fulfillment_center fulfillment_center) {
        this.fulfillment_center = fulfillment_center;
    }

    public Dest_pincode getDest_pincode() {
        return dest_pincode;
    }

    public void setDest_pincode(Dest_pincode dest_pincode) {
        this.dest_pincode = dest_pincode;
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

    public Master_bag getMaster_bag() {
        return master_bag;
    }

    public void setMaster_bag(Master_bag master_bag) {
        this.master_bag = master_bag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Shipment_transactions shipment_transactions = (Shipment_transactions) o;

        if ( ! Objects.equals(id, shipment_transactions.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Shipment_transactions{" +
                "id=" + id +
                ", orderRef='" + orderRef + "'" +
                ", invoiceId='" + invoiceId + "'" +
                ", shipmentId='" + shipmentId + "'" +
                ", amount='" + amount + "'" +
                ", awb='" + awb + "'" +
                ", status='" + status + "'" +
                ", time='" + time + "'" +
                ", lbh='" + lbh + "'" +
                ", in_scan_time='" + in_scan_time + "'" +
                ", description='" + description + "'" +
                ", cost='" + cost + "'" +
                '}';
    }
}
