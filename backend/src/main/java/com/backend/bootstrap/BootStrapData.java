package com.backend.bootstrap;

import com.backend.model.Product;
import com.backend.repository.PaymentRepository;
import com.backend.repository.ProductRepository;
import com.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

/**
 * Class init H2database
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * H2Database constructor.
     * @param paymentRepository Payment Repository
     * @param productRepository Product Repository
     * @param userRepository    User Repository
     */
    public BootStrapData(PaymentRepository paymentRepository, ProductRepository productRepository,
                         UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /**
     * Init H2Database.
     *
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Create product");

        Product p1 = new Product(15.5, "produit_1");
        productRepository.save(p1);

        Product p2 = new Product(8, "produit_2");
        productRepository.save(p2);

        Product p3 = new Product(40, "produit_3");
        productRepository.save(p3);

        Product p4 = new Product(5.6, "produit_4");
        productRepository.save(p4);
    }
}
