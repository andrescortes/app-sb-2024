package co.com.tkl.app.repositories;

import co.com.tkl.app.entities.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT c from Client c left join fetch c.addresses where c.id = ?1")
    Optional<Client> findOneWithAddresses(Long id);

    @Query("SELECT c from Client c left join fetch c.invoices where c.id = ?1")
    Optional<Client> findOneWithInvoices(Long id);

    @Query("SELECT c from Client c left join fetch c.addresses left join fetch c.invoices left join c.clientDetail where c.id = ?1")
    Optional<Client> findOne(Long id);


}
