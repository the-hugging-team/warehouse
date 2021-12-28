package com.the.hugging.team.services;

import com.the.hugging.team.entities.*;
import com.the.hugging.team.repositories.CashRegisterRepository;
import com.the.hugging.team.repositories.DeliveryRepository;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.wizard.beans.PaymentBean;
import javafx.collections.ObservableList;

public class DeliveryService {
    private static DeliveryService INSTANCE = null;
    private final DeliveryRepository deliveryRepository = DeliveryRepository.getInstance();
    private final CashRegisterRepository cashRegisterRepository = CashRegisterRepository.getInstance();

    private final TransactionTypeService transactionTypeService = TransactionTypeService.getInstance();
    private final CashRegisterService cashRegisterService = CashRegisterService.getInstance();
    private final Session session = Session.getInstance();

    public static DeliveryService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeliveryService();
        }

        return INSTANCE;
    }

    public Delivery addDelivery(Delivery delivery) {
        deliveryRepository.save(delivery);
        return delivery;
    }

    public void addDeliveryFromBean(PaymentBean paymentBean, Double finalPrice) {
        Delivery delivery = new Delivery();
        Transaction transaction = new Transaction();
        Invoice currentInvoice = paymentBean.getInvoice();
        CashRegister mainCashRegister = cashRegisterService.getMainCashRegister();

        delivery.setInvoice(currentInvoice);

        transaction.setCashRegister(mainCashRegister);
        transaction.setTransactionType(transactionTypeService.getTransactionTypeBySlug(TransactionType.DELIVERY));
        transaction.setAmount(finalPrice);

        delivery.setTransaction(transaction);

        addDelivery(delivery);

        attachProductsToDelivery(paymentBean.getProductsData(), delivery);

        updateCashRegister(mainCashRegister, finalPrice);
    }

    private void attachProductsToDelivery(ObservableList<Product> products, Delivery delivery) {
        for (Product product : products) {
            DeliveryProduct deliveryProduct = new DeliveryProduct();
            deliveryProduct.setProduct(product);
            deliveryProduct.setDelivery(delivery);
            deliveryProduct.setQuantity(product.getQuantity());
            deliveryProduct.setProductQuantityType(product.getProductQuantityType());

            deliveryRepository.saveDeliveryProduct(deliveryProduct);
        }
    }

    private void updateCashRegister(CashRegister cashRegister, Double finalPrice) {
        cashRegister.setBalance(cashRegister.getBalance() - finalPrice);
        cashRegisterRepository.update(cashRegister);
    }
}
