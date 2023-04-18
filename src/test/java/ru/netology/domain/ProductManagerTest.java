package ru.netology.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.repository.ProductRepository;

public class ProductManagerTest {

    Book book1 = new Book(001, "Book1", 200, "Author1");
    Book book2 = new Book(002, "Book2", 250, "Author2");
    Smartphone smartphone1 = new Smartphone(003, "Smartphone1", 300, "Manufacturer1");
    Smartphone smartphone2 = new Smartphone(004, "Smartphone2", 350, "Manufacturer2");

    ProductRepository repository = new ProductRepository();
    ProductManager productManager = new ProductManager(repository);

    @Test
    public void testFindAll() {
        productManager.add(book1);
        productManager.add(book2);
        productManager.add(smartphone1);
        productManager.add(smartphone2);

        Product[] exp = {book1, book2, smartphone1, smartphone2};
        Product[] act = repository.findAll();

        Assertions.assertArrayEquals(exp, act);
    }

    @Test
    public void testNotFoundId() {
        productManager.add(book1);
        productManager.add(book2);
        productManager.add(smartphone1);
        productManager.add(smartphone2);

        Assertions.assertThrows(NotFoundException.class, () -> {
            repository.removeById(100);
        });
    }

    @Test
    public void testRemoveNotFoundId() {
        productManager.add(book1);
        productManager.add(book2);
        productManager.add(smartphone1);
        productManager.add(smartphone2);

        Assertions.assertThrows(NotFoundException.class, () -> {
            repository.removeById(10);
        });
    }

    @Test
    public void testAddRemoveProduct() {
        productManager.add(book1);
        productManager.add(book2);
        productManager.add(smartphone1);
        productManager.add(smartphone2);
        repository.removeById(003);
        repository.add(smartphone1);

        Product[] exp = {book1, book2, smartphone2, smartphone1};
        Product[] act = repository.findAll();

        Assertions.assertArrayEquals(exp, act);

    }

    @Test
    public void testAddExistId() {
        productManager.add(book1);
        productManager.add(book2);
        productManager.add(smartphone1);
        productManager.add(smartphone2);

        Assertions.assertThrows(AlreadyExistsException.class, () -> {
            repository.add(book2);
        });
    }

    @Test
    public void testRepositoryDeleteById() {
        productManager.add(book1);
        productManager.add(book2);
        productManager.add(smartphone1);
        productManager.add(smartphone2);
        repository.removeById(003);

        Product[] exp = {book1, book2, smartphone2};
        Product[] act = repository.findAll();

        Assertions.assertArrayEquals(exp, act);
    }

    @Test
    public void testBookAuthor() {
        productManager.add(book2);

        String expAuthor = "Author2";
        String actAuthor = book2.getAuthor();

        Assertions.assertEquals(expAuthor, actAuthor);
    }

    @Test
    public void testSmartphoneManufacturer() {
        productManager.add(smartphone2);

        String expSmartphone = "Manufacturer2";
        String actSmartphone = smartphone2.getManufacturer();

        Assertions.assertEquals(expSmartphone, actSmartphone);
    }

    @Test
    public void testProductId() {
        productManager.add(smartphone2);

        int expId = 004;
        int actId = smartphone2.getId();

        Assertions.assertEquals(expId, actId);
    }

    @Test
    public void testProductName() {
        productManager.add(book1);

        String expName = "Book1";
        String actName = book1.getName();

        Assertions.assertEquals(expName, actName);
    }

    @Test
    public void testProductCost() {
        productManager.add(smartphone1);

        int expCost = 300;
        int actCost = smartphone1.getCost();

        Assertions.assertEquals(expCost, actCost);
    }

    @Test
    public void testNotMatches() {
        productManager.add(book1);
        productManager.add(book2);
        productManager.add(smartphone1);
        productManager.add(smartphone2);

        Product[] expProducts = {};
        Product[] actProducts = productManager.searchBy("Abracadabra");

        Assertions.assertArrayEquals(expProducts, actProducts);
    }

    @Test
    public void testSearchBy() {
        productManager.add(book1);
        productManager.add(book2);
        productManager.add(smartphone1);
        productManager.add(smartphone2);

        Product[] expProducts = {book1, book2};
        Product[] actProducts = productManager.searchBy("Book");

        Assertions.assertArrayEquals(expProducts, actProducts);
    }
}
