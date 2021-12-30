package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.*;
import com.the.hugging.team.repositories.DeliveryRepository;
import com.the.hugging.team.repositories.InvoiceRepository;
import com.the.hugging.team.repositories.TransactionRepository;
import com.the.hugging.team.repositories.UserRepository;
import com.the.hugging.team.services.DeliveryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeliveryServiceTest {

    private static Invoice newInvoice = InvoiceRepository.getInstance().getById(2).orElse(null);
    private static Transaction newTransaction = TransactionRepository.getInstance().getById(2).orElse(null);
    //private static DeliveryProduct newDeliveryProducts = ;
    private static User newUser = UserRepository.getInstance().getById(2).orElse(null);
    private final DeliveryService deliveryService = DeliveryService.getInstance();

    @Test
    @DisplayName("Should add delivery")
    void shouldAddDelivery(){
        Delivery delivery = new Delivery();
        delivery.setInvoice(newInvoice);
        delivery.setTransaction(newTransaction);
        //delivery.setDeliveryProducts(newDeliveryProducts);
        //delivery.setCreatedAt();
        delivery.setCreatedBy(newUser);
    }

}
