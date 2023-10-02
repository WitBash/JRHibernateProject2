package com.bashko;

import com.bashko.config.XMLSessionFactory;
import com.bashko.dao.*;
import com.bashko.entity.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;


@Log4j2
public class Main {

    private final SessionFactory sessionFactory;
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;


    public Main() {
        this.sessionFactory = new XMLSessionFactory().getSessionFactory();

        this.actorDAO = new ActorDAO(sessionFactory);
        this.addressDAO = new AddressDAO(sessionFactory);
        this.categoryDAO = new CategoryDAO(sessionFactory);
        this.cityDAO = new CityDAO(sessionFactory);
        this.countryDAO = new CountryDAO(sessionFactory);
        this.customerDAO = new CustomerDAO(sessionFactory);
        this.filmDAO = new FilmDAO(sessionFactory);
        this.filmTextDAO = new FilmTextDAO(sessionFactory);
        this.inventoryDAO = new InventoryDAO(sessionFactory);
        this.languageDAO = new LanguageDAO(sessionFactory);
        this.paymentDAO = new PaymentDAO(sessionFactory);
        this.rentalDAO = new RentalDAO(sessionFactory);
        this.staffDAO = new StaffDAO(sessionFactory);
        this.storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();

        Customer customer = main.createCustomer();

        List<Film> availableFilms = main.getAllAvailableFilmsForRent(customer);

        Film film = availableFilms.get(Math.round((float) Math.random() * availableFilms.size()));
        Rental rental = main.rentFilm(customer, film);

        Thread.sleep(5000); // for to see difference between rental_date and return_date

        main.returnFilmToStore(rental);

        main.createNewFilm();
    }

    /**
     * The Method creates new film which available for rent
     *
     * @return new film
     */
    private Film createNewFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            Language language = languageDAO.getByNameIfExist("English");

            Category category = categoryDAO.getByName("Travel");
            Set<Category> categorySet = new HashSet<>();
            categorySet.add(category);

            Actor actor1 = actorDAO.getByFirstNameAndLastNameIfExist("FRED", "COSTNER");
            Actor actor2 = actorDAO.getByFirstNameAndLastNameIfExist("HELEN", "VOIGHT");
            Set<Actor> actorSet = new HashSet<>();
            actorSet.add(actor1);
            actorSet.add(actor2);

            Film newFilmToDB = Film.builder()
                    .title("TRAVEL TO FUTURE")
                    .releaseYear(Year.now())
                    .language(language)
                    .rentalDuration(Byte.valueOf( "3"))
                    .rentalRate(BigDecimal.valueOf(5.99))
                    .replacementCost(BigDecimal.valueOf(24.99))
                    .rating(Rating.NC17)
                    .specialFeatures("Trailers,Commentaries")
                    .categories(categorySet)
                    .actors(actorSet)
                    .build();
            filmDAO.save(newFilmToDB);

            log.info("Saved new film {} to DB", newFilmToDB);

            Store store1 = storeDAO.getById(Byte.parseByte("1"));
            Store store2 = storeDAO.getById(Byte.parseByte("2"));

            Inventory inventory1 = Inventory.builder()
                    .film(newFilmToDB)
                    .store(store1)
                    .build();
            Inventory inventory2 = Inventory.builder()
                    .film(newFilmToDB)
                    .store(store2)
                    .build();
            inventoryDAO.save(inventory1);
            log.info("Saved {} to DB", inventory1);
            inventoryDAO.save(inventory2);
            log.info("Saved {} to DB", inventory2);
            transaction.commit();
            return newFilmToDB;
        }
    }

    /**
     * Method returns list available films for rent
     *
     * @param customer
     * @return list films available for rent
     */
    private List<Film> getAllAvailableFilmsForRent(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            Store store = customer.getStore();
            List<Film> availableFilms = filmDAO.getAvailableFilms(store);

            transaction.commit();

            return availableFilms;
        }
    }

    /**
     * The Method saves rental and payment for rent to database
     *
     * @param film film, which chose customer
     * @return the current rental
     * @param customer client taking film by rent
     */
    private Rental rentFilm(Customer customer, Film film) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            Store store = customer.getStore();
            Inventory inventory = inventoryDAO.getInventoryByFilmAndByStore(film, store);

            Rental newRental = Rental.builder()
                    .rentalDate(LocalDateTime.now())
                    .inventory(inventory)
                    .customer(customer)
                    .staff(store.getStaff())
                    .build();
            rentalDAO.save(newRental);
            log.info("Saved {} to DB", newRental);
            Payment paymentForNewRental = Payment.builder()
                    .customer(customer)
                    .staff(store.getStaff())
                    .rental(newRental)
                    .amount(inventory.getFilm().getRentalRate())
                    .paymentDate(LocalDateTime.now())
                    .build();
            paymentDAO.save(paymentForNewRental);
            log.info("Saved {} to DB", paymentForNewRental);

            transaction.commit();

            return newRental;
        }
    }

    /**
     * The method returns film to store and enters data about to return time
     *
     * @param rental the entity which customer took to rent before
     */
    private void returnFilmToStore(Rental rental) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            Rental tempRental = rentalDAO.getById(rental.getId());
            tempRental.setReturnDate(LocalDateTime.now());
            rentalDAO.update(tempRental);

            transaction.commit();
            log.info("Updated {} in DB", tempRental);
        }
    }

    /**
     * The Method creates new customer
     * data which be "hard code" will be to get from UI
     *
     * @return saved object Customer
     */
    private Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            Store store = storeDAO.getItems(0, 1).get(0);
            City city = cityDAO.getByName("Stockport");

            Address address = Address.builder()
                    .address("Wall str., 1")
                    .district("First")
                    .city(city)
                    .phone("+3857941249547")
                    .build();
            addressDAO.save(address);
            log.info("Saved {} to DB", address);
            Customer newCustomer = Customer.builder()
                    .store(store)
                    .firstName("Mark")
                    .lastName("Spencer")
                    .email("mark_spencer@spencer.com")
                    .address(address)
                    .active(true)
                    .build();
            customerDAO.save(newCustomer);
            log.info("Saved {} to DB", newCustomer);

            transaction.commit();

            return newCustomer;
        }
    }
}