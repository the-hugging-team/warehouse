package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Delivery;
import com.the.hugging.team.entities.Invoice;
import com.the.hugging.team.entities.Transaction;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.repositories.InvoiceRepository;
import com.the.hugging.team.repositories.TransactionRepository;
import com.the.hugging.team.repositories.UserRepository;
import com.the.hugging.team.services.DeliveryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeliveryServiceTest {

    private static final Invoice newInvoice = InvoiceRepository.getInstance().getById(2).orElse(null);
    private static final Transaction newTransaction = TransactionRepository.getInstance().getById(2).orElse(null);
    //private static DeliveryProduct newDeliveryProducts = ;
    private static final User newUser = UserRepository.getInstance().getById(2).orElse(null);
    private final DeliveryService deliveryService = DeliveryService.getInstance();

    @Test//TODO
    @DisplayName("Should add delivery")
    void shouldAddDelivery() {
        Delivery delivery = new Delivery();
        delivery.setInvoice(newInvoice);
        delivery.setTransaction(newTransaction);
        //delivery.setDeliveryProducts(newDeliveryProducts);
        delivery.setCreatedBy(newUser);
    }

}
