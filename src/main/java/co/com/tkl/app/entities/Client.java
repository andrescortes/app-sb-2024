package co.com.tkl.app.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
//            fetch = FetchType.EAGER//fetch all items at time by default is lazy, that means not fetch sub objects
    )
//    @JoinColumn(name = "client_id")
    @JoinTable(
            name = "tbl_clients_to_addresses",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_address"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_address"})
    )
    private Set<Address> addresses;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private Set<Invoice> invoices;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
//    @JoinColumn(name = "client_detail_id")
    private ClientDetail clientDetail;

    public Client() {
        addresses = new HashSet<>();
        invoices = new HashSet<>();
    }

    public Client(String name, String lastname) {
        this();
        this.name = name;
        this.lastname = lastname;
    }

    public Client addInvoice(Invoice invoice) {
        invoices.add(invoice);
        invoice.setClient(this);
        return this;
    }

    public void removeInvoice(Invoice invoiceRetrieved) {
        this.getInvoices().remove(invoiceRetrieved);
        invoiceRetrieved.setClient(null);
    }

    public void setClientDetailUpdate(ClientDetail detail) {
        this.clientDetail = detail;
        detail.setClient(this);
    }

    public void removeClientDetail(ClientDetail detail) {
        detail.setClient(null);
        this.clientDetail = null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("addresses=").append(addresses);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", lastname='").append(lastname).append('\'');
        sb.append(", invoices=").append(invoices);
        sb.append(", clientDetail=").append(clientDetail);
        sb.append('}');
        return sb.toString();
    }
}
