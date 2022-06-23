package com.backend.service.concretions;

import com.backend.exception.UserException;
import com.backend.model.Product;
import com.backend.model.ShoppingBasket;
import com.backend.model.UserApplication;
import com.backend.repository.UserRepository;
import com.backend.service.abstractions.IProductService;
import com.backend.service.abstractions.IShoppingBasketService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class Shopping Basket Service
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@Service
public class ShoppingBasketImpl implements IShoppingBasketService {
    private final UserRepository userRepository;
    private final IProductService productService;

    /**
     * Shopping Basket constructor
     * Init shopping basket product service and user repository
     * @param userRepository User repository
     * @param productService Product service
     */
    public ShoppingBasketImpl(UserRepository userRepository, IProductService productService) {
        this.userRepository = userRepository;
        this.productService = productService;
    }

    /**
     * Get user shopping basket by user id
     *
     * @param id User Id
     * @return user shopping basket
     * @throws UserException if user Id is invalifd
     */
    @Override
    public ShoppingBasket getShoppingBasket(Long id) {
        Optional<UserApplication> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserException("Invalid User Id");
        }
        return user.get().getShoppingBasket();
    }

    /**
     * Add article on user shopping basket
     *
     * @param id User Id
     * @param articleId Product Id
     * @return update user shopping basket
     * @throws UserException if user Id is invalid
     */
    @Override
    public ShoppingBasket addArticle(Long id, Long articleId) {
        Optional<UserApplication> user = userRepository.findById(id);
        Product article = productService.getProductById(articleId);

        if (!user.isPresent()) {
            throw new UserException("Invalid User Id");
        }
        user.get().AddShoppingBasketArticle(article);
        UserApplication save = userRepository.save(user.get());
        return save.getShoppingBasket();
    }

    /**
     * remove article on user shopping basket
     *
     * @param id User Id
     * @param articleId Product Id
     * @return update user shopping basket
     * @throws UserException if user Id is invalid
     */
    @Override
    public ShoppingBasket removeArticle(Long id, Long articleId) {
        Optional<UserApplication> user = userRepository.findById(id);
        Product article = productService.getProductById(articleId);

        if (!user.isPresent()) {
            throw new UserException("Invalid User Id");
        }
        user.get().RemoveShoppingBasketArticle(article);
        userRepository.save(user.get());
        return user.get().getShoppingBasket();
    }
}
