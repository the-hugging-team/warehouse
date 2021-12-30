package com.the.hugging.team.warehouse;

import com.the.hugging.team.repositories.ActivityTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ActivityTypeServiceTest {

    @ParameterizedTest
    @DisplayName("Should get activity type by slug")
    @ValueSource(strings = {"activities.users.create", "activities.users.edit", "activities.companies.create", "activities.companies.edit", "activities.companies.delete",
            "activities.cash-registers.create", "activities.cash-registers.delete", "activities.sale", "activities.delivery", "activities.storage.create", "activities.storage.edit", "activities.storage.delete"})
    void shouldGetActivityTypeBySlug(String slug) {
        Assertions.assertEquals(slug, ActivityTypeRepository.getInstance().getBySlug(slug).get().getSlug());
    }
}
