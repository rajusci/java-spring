package com.inspirenetz.api.test.core.fixture;


import com.inspirenetz.api.core.domain.PasswordHistory;
import com.inspirenetz.api.test.core.builder.PasswordHistoryBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameenci on 9/10/14.
 */
public class PasswordHistoryFixture {

    public static PasswordHistory standardPasswordHistory() {

        PasswordHistory passwordHistory = PasswordHistoryBuilder.aPasswordHistory()
                .withPasHistoryPassword("TEST PASSWORD")
                .withPasHistoryUserNo(1L)
                .withPasChangedAt(new Date())
                .build();


        return passwordHistory;

    }
}
