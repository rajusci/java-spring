package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.PasswordHistory;

import java.util.Date;

/**
 * Created by ameenci on 9/10/14.
 */
public class PasswordHistoryBuilder {
    private Long pasHistoryId;
    private Long pasHistoryUserNo;
    private String pasHistoryPassword;
    private Date pasChangedAt;

    private PasswordHistoryBuilder() {
    }

    public static PasswordHistoryBuilder aPasswordHistory() {
        return new PasswordHistoryBuilder();
    }

    public PasswordHistoryBuilder withPasHistoryId(Long pasHistoryId) {
        this.pasHistoryId = pasHistoryId;
        return this;
    }

    public PasswordHistoryBuilder withPasHistoryUserNo(Long pasHistoryUserNo) {
        this.pasHistoryUserNo = pasHistoryUserNo;
        return this;
    }

    public PasswordHistoryBuilder withPasHistoryPassword(String pasHistoryPassword) {
        this.pasHistoryPassword = pasHistoryPassword;
        return this;
    }

    public PasswordHistoryBuilder withPasChangedAt(Date pasChangedAt) {
        this.pasChangedAt = pasChangedAt;
        return this;
    }

    public PasswordHistoryBuilder but() {
        return aPasswordHistory().withPasHistoryId(pasHistoryId).withPasHistoryUserNo(pasHistoryUserNo).withPasHistoryPassword(pasHistoryPassword).withPasChangedAt(pasChangedAt);
    }

    public PasswordHistory build() {
        PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setPasHistoryId(pasHistoryId);
        passwordHistory.setPasHistoryUserNo(pasHistoryUserNo);
        passwordHistory.setPasHistoryPassword(pasHistoryPassword);
        passwordHistory.setPasChangedAt(pasChangedAt);
        return passwordHistory;
    }
}
