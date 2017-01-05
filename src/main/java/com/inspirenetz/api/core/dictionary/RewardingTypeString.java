package com.inspirenetz.api.core.dictionary;

/**
 * Created by saneeshci on 27/9/14.
 */
public class RewardingTypeString {

    public static final String EVENT_REGISTRATION = "1";
    public static final String SIGNUP_BONUS="Signup Bonus";
    public static final String ADD_REFERRAL = "Add Referral";

    public static String getRewardingTypeString(int type){

        switch (type){

            case CustomerRewardingType.SIGNUP_BONUS: return SIGNUP_BONUS;

            case CustomerRewardingType.ADD_REFERRAL: return ADD_REFERRAL;

            default:return "";
        }

    }

}
