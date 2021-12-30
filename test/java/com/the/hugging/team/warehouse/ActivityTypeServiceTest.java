package com.the.hugging.team.warehouse;

import com.the.hugging.team.services.ActivityTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ActivityTypeServiceTest {

    @ParameterizedTest
    @DisplayName("Should get activity type by slug")
    @ValueSource(strings = {"activity-types.users.create", "activity-types.users.edit", "activity-types.companies.create", "activity-types.companies.edit", "activity-types.companies.delete",
            "activity-types.cash-registers.create", "activity-types.cash-registers.delete", "activity-types.sale", "activity-types.delivery", "activity-types.storage.create", "activity-types.storage.edit", "activity-types.storage.delete"})
    void shouldGetActivityTypeBySlug(String slug) {
        Assertions.assertEquals(slug, ActivityTypeService.getInstance().getActivityTypeBySlug(slug).getSlug());
    }
}
