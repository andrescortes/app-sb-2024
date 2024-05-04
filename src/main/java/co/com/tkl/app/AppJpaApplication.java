package co.com.tkl.app;

import co.com.tkl.app.entities.Address;
import co.com.tkl.app.entities.Client;
import co.com.tkl.app.entities.ClientDetail;
import co.com.tkl.app.entities.Course;
import co.com.tkl.app.entities.Invoice;
import co.com.tkl.app.entities.Student;
import co.com.tkl.app.repositories.ClientDetailRepository;
import co.com.tkl.app.repositories.ClientRepository;
import co.com.tkl.app.repositories.CourseRepository;
import co.com.tkl.app.repositories.InvoiceRepository;
import co.com.tkl.app.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class AppJpaApplication {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientDetailRepository detailRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public static void main(String[] args) {
        SpringApplication.run(AppJpaApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner commandLineRunner() {
        return args -> {
//            manyToOne();
//            manyToOneFindById();
//            oneToMany();
//            oneToManyFindById();
//            removeAddress();
//            oneToManyTwoBinding();
//            oneToOneBidirectionalFindById();
//            manyToManyRemove();
//            manyToManyAddBidirectional();
//            manyToManyBidirectionalRemove();
            manyToManyBidirectionalFindById();
        };
    }

    private void manyToManyBidirectionalFindById() {
        Optional<Student> studentOpt = studentRepository.findOne(1L);
        Optional<Student> student2Opt = studentRepository.findOne(2L);
        Student student = studentOpt.get();
        Student student2 = student2Opt.get();

        Course course = courseRepository.findOne(1L).get();

        Course course2 = courseRepository.findOne(2L).get();

//        student.setCourses(Set.of(course, course2));
//        student2.setCourses(Set.of(course2));
        student.addACourse(course);
        student.addACourse(course2);
        student2.addACourse(course2);

        Iterable<Student> students = studentRepository.saveAll(Set.of(student, student2));

        students.forEach(st -> System.out.println("Student = " + st));
    }

    private void manyToManyBidirectionalRemove() {
        Optional<Student> studentOpt = studentRepository.findById(1L);
        Optional<Student> student2Opt = studentRepository.findById(2L);
        Student student = studentOpt.get();
        Student student2 = student2Opt.get();

        Course course = courseRepository.findById(1L).get();

        Course course2 = courseRepository.findById(2L).get();

        student.setCourses(Set.of(course, course2));
        student2.setCourses(Set.of(course2));

        Iterable<Student> students = studentRepository.saveAll(Set.of(student, student2));

        students.forEach(st -> System.out.println("Student = " + st));

        Optional<Student> studentOptDb = studentRepository.findOne(1L);
        studentOptDb.ifPresent(st -> {
            Optional<Course> courseOptional = courseRepository.findOne(2L);
            courseOptional.ifPresent(co -> {
                st.removeCourse(co);
                Student saved = studentRepository.save(st);
                System.out.println("saved = " + saved);
            });
        });
    }

    private void manyToManyAddBidirectional() {
        Student student = new Student("Carlos", "Parra");
        Student student2 = new Student("Maria", "Carmen");

        Course course = new Course();
        course.setName("Physics");
        course.setInstructor("Marlon Hincapie");

        Course course2 = new Course();
        course2.setName("Math");
        course2.setInstructor("Carlos Perez");

//        student.setCourses(Set.of(course, course2));
//        student2.setCourses(Set.of(course2));
        student.addACourse(course);
        student.addACourse(course2);
        student.addACourse(course2);

        Iterable<Student> students = studentRepository.saveAll(Set.of(student, student2));

        students.forEach(st -> System.out.println("Student = " + st));
    }

    private void manyToManyRemove() {
        Student student = Student.builder()
                .name("Jano")
                .lastname("Pura")
                .build();
        Student student2 = Student.builder()
                .name("Maria")
                .lastname("Herza")
                .build();

        Course course = new Course();
        course.setName("Physics");
        course.setInstructor("Marlon Hincapie");

        Course course2 = new Course();
        course2.setName("Math");
        course2.setInstructor("Carlos Perez");

        student.setCourses(Set.of(course, course2));
        student2.setCourses(Set.of(course2));

        Iterable<Student> students = studentRepository.saveAll(Set.of(student, student2));

        students.forEach(st -> System.out.println("Student = " + st));
        Optional<Student> studentOptDb = studentRepository.findOne(3L);
        studentOptDb.ifPresent(st -> {
            Optional<Course> courseOptional = courseRepository.findById(3L);
            courseOptional.ifPresent(co -> {
                st.getCourses().remove(co);
                Student saved = studentRepository.save(st);
                System.out.println("saved = " + saved);
            });
        });
    }

    private void manyToManyRemoveFindById() {
        Optional<Student> studentOpt = studentRepository.findById(1L);
        Optional<Student> student2Opt = studentRepository.findById(2L);
        Student student = studentOpt.get();
        Student student2 = student2Opt.get();

        Course course = courseRepository.findById(1L).get();

        Course course2 = courseRepository.findById(2L).get();

        student.setCourses(Set.of(course, course2));
        student2.setCourses(Set.of(course2));

        Iterable<Student> students = studentRepository.saveAll(Set.of(student, student2));

        students.forEach(st -> System.out.println("Student = " + st));

        Optional<Student> studentOptDb = studentRepository.findOne(1L);
        studentOptDb.ifPresent(st -> {
            Optional<Course> courseOptional = courseRepository.findById(2L);
            courseOptional.ifPresent(co -> {
                st.getCourses().remove(co);
                Student saved = studentRepository.save(st);
                System.out.println("saved = " + saved);
            });
        });
    }

    private void manyToManyFindById() {
        Optional<Student> studentOpt = studentRepository.findById(1L);
        Optional<Student> student2Opt = studentRepository.findById(2L);
        Student student = studentOpt.get();
        Student student2 = student2Opt.get();

        Course course = courseRepository.findById(1L).get();

        Course course2 = courseRepository.findById(2L).get();

        student.setCourses(Set.of(course, course2));
        student2.setCourses(Set.of(course2));

        Iterable<Student> students = studentRepository.saveAll(Set.of(student, student2));

        students.forEach(st -> System.out.println("Student = " + st));
    }

    private void manyToMany() {
        Student student = Student.builder()
                .name("Jano")
                .lastname("Pura")
                .build();
        Student student2 = Student.builder()
                .name("Maria")
                .lastname("Herza")
                .build();

        Course course = new Course();
        course.setName("Physics");
        course.setInstructor("Marlon Hincapie");

        Course course2 = new Course();
        course2.setName("Math");
        course2.setInstructor("Carlos Perez");

        student.setCourses(Set.of(course, course2));
        student2.setCourses(Set.of(course2));

        Iterable<Student> students = studentRepository.saveAll(Set.of(student, student2));

        students.forEach(st -> System.out.println("Student = " + st));
    }

    private void oneToOneBidirectionalFindById() {
        Optional<Client> opt = clientRepository.findOne(1L);
        opt.ifPresent(client -> {
            ClientDetail clientDetail = ClientDetail.builder()
                    .isPremium(true)
                    .points(13000)
                    .build();

            client.setClientDetailUpdate(clientDetail);

            Client saved = clientRepository.save(client);

            System.out.println("saved = " + saved);
        });
    }

    private void oneToOneBidirectional() {
        ClientDetail clientDetail = ClientDetail.builder()
                .isPremium(true)
                .points(13000)
                .build();
        Client client = new Client("Pedro", "Gonzales");

        client.setClientDetailUpdate(clientDetail);

        Client saved = clientRepository.save(client);
        System.out.println("saved = " + saved);
    }

    private void oneToOne() {
//        Client client = new Client();
//        client.setName("Erba");
//        client.setLastname("Pola");
//        clientRepository.save(client);//como no es una relacion en cascada, toca persistirlo primero
//        ClientDetail clientDetail = ClientDetail.builder()
//                .points(15000)
//                .isPremium(true)
//                .client(client)
//                .build();
//        ClientDetail saved = detailRepository.save(clientDetail);
//        System.out.println("saved = " + saved.getId());
        ClientDetail clientDetail = ClientDetail.builder()
                .isPremium(true)
                .points(13000)
                .build();
        ClientDetail detail = detailRepository.save(clientDetail);

        Client client = new Client("Pedro", "Bermudez");
        client.setClientDetailUpdate(detail);
        Client saved = clientRepository.save(client);
        System.out.println("saved = " + saved);

    }

    private void oneToManyRemoveTwoBindingFindById() {
        Optional<Client> optional = clientRepository.findOne(1L);
        optional.ifPresent(client -> {
            Invoice invoice = new Invoice("Laptops", 1200L);
            Invoice invoice2 = new Invoice("Bikes", 1400L);

//        client.setInvoices(List.of(invoice,invoice2));
            client
                    .addInvoice(invoice)
                    .addInvoice(invoice2);
            clientRepository.save(client);
        });
        Optional<Client> clientOptional = clientRepository.findOne(1L);
        clientOptional.ifPresent(clientRetrieved -> {
            Optional<Invoice> invoiceOpt = invoiceRepository.findById(2L);
            invoiceOpt.ifPresent(invoiceRetrieved -> {
                clientRetrieved.removeInvoice(invoiceRetrieved);

                Client saved = clientRepository.save(clientRetrieved);
                System.out.println("saved = " + saved);
            });
        });
    }

    public void oneToManyTwoBindingFindById() {
        Optional<Client> optional = clientRepository.findOne(1L);
        optional.ifPresent(cl -> {
            Invoice invoice = new Invoice("Laptops", 1200L, cl);
            Invoice invoice2 = new Invoice("Bikes", 1400L, cl);

//        client.setInvoices(List.of(invoice,invoice2));
            cl
                    .addInvoice(invoice)
                    .addInvoice(invoice2);
            Client saved = clientRepository.save(cl);

            System.out.println("saved = " + saved);
        });
    }

    public void oneToManyTwoBinding() {
        Client client = new Client("Maria", "Salazar");
        Invoice invoice = new Invoice("Laptops", 1200L, client);
        Invoice invoice2 = new Invoice("Bikes", 1400L, client);

//        client.setInvoices(List.of(invoice,invoice2));
        client
                .addInvoice(invoice)
                .addInvoice(invoice2);
        Client saved = clientRepository.save(client);

        System.out.println("saved = " + saved);
    }

    public void removeAddress() {
        Address address1 = new Address("street The Pink", 12);
        Address address2 = new Address("street Mount", 15);
        Client client = new Client("Carlos", "Poveda");
        client.getAddresses().add(address1);
        client.getAddresses().add(address2);

        Client saved = clientRepository.save(client);
        System.out.println("saved = " + saved);

//        Optional<Client> clientOptional = clientRepository.findById(3L);
        Optional<Client> clientOptional = clientRepository.findOneWithAddresses(3L);
        clientOptional.ifPresent(cl -> {
            System.out.println("cl = " + cl);
            cl.getAddresses().remove(address1);//other query but the connection is closed, in a web app it's not happens
            Client saved1 = clientRepository.save(cl);
            System.out.println("clientRemoveAddress = " + saved1);
        });
    }

    public void oneToManyFindById() {
        Optional<Client> opt = clientRepository.findById(2L);
        if (opt.isPresent()) {
            Address address1 = new Address("street The Pink", 12);
            Address address2 = new Address("street Mount", 15);
            Client saved = opt.get();
            saved.setAddresses(Set.of(address1, address2));

            Client client = clientRepository.save(saved);
            System.out.println("client = " + client);

        }
    }

    public void oneToMany() {
        Address address1 = new Address("street The Pink", 12);
        Address address2 = new Address("street Mount", 15);
        Client client = new Client("Carlos", "Poveda");
        client.getAddresses().add(address1);
        client.getAddresses().add(address2);

        Client saved = clientRepository.save(client);
        System.out.println("saved = " + saved);
    }

    public void manyToOneFindById() {
        Optional<Client> clientOpt = clientRepository.findById(1L);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            Invoice invoice = new Invoice();
            invoice.setDescription("Items");
            invoice.setTotal(25400L);
            invoice.setClient(client);
            Invoice saved = invoiceRepository.save(invoice);
            System.out.println("invoiced = " + saved);
        }
    }

    public void manyToOne() {
        Client client = new Client();
        client.setName("John");
        client.setLastname("Zuluaga");

        clientRepository.save(client);

        Invoice invoice = new Invoice();
        invoice.setDescription("Items");
        invoice.setTotal(25400L);
        invoice.setClient(client);

        Invoice invoiceDb = invoiceRepository.save(invoice);
        System.out.println("invoiceDb = " + invoiceDb);
    }
}
