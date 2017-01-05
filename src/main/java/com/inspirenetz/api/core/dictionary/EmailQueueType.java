package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 28/3/14.
 */
public enum EmailQueueType {

    ACCOUNT_ACTIVATION(1),
    PASSWORD_RESET(2),
    REDEMPTION_TRACKING(3),
    CUSTOMER_SIGNUP_BY_MERCHANT(4),
    ONLINE_ORDER_TRACKING(5),
    COOKED_FOOD_USER_ACTIVATION(6),
    COOKED_FOOD_ONLINE_ORDER_TRACKING(7),
    REDEMPTION_NOTIFICATION(8),
    EVOUCHER(9);


    private int value;

    EmailQueueType(int value) {

        this.value = value;

    }
}
