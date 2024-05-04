package co.com.tkl.app.repositories;

import co.com.tkl.app.entities.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
