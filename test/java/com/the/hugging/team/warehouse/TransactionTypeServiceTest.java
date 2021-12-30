package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.TransactionType;
import com.the.hugging.team.services.TransactionTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TransactionTypeServiceTest {

    @ParameterizedTest
    @DisplayName("Should get transaction by slug")
    @ValueSource(strings = {"transaction_types.buy", "transaction_types.sell"})
    void shouldGetTransactionTypeBySlug(String slug) {
        TransactionType transactionType = new TransactionType();
        transactionType.setSlug(slug);
        transactionType.setName(slug.contains("buy") ? "Buy" : "Sell");
        Assertions.assertEquals(transactionType, TransactionTypeService.getInstance().getTransactionTypeBySlug(slug));
    }
}
