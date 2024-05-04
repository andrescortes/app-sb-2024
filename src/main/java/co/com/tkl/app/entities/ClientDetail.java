package co.com.tkl.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
@Entity
@Table(name = "client_details")
public class ClientDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isPremium;
    private Integer points;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientDetail{");
        sb.append("Id=").append(id);
        sb.append(", isPremium=").append(isPremium);
        sb.append(", points=").append(points);
        sb.append('}');
        return sb.toString();
    }
}
