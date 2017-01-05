package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.NotificationCampaignListeners;
import com.inspirenetz.api.core.domain.NotificationCampaign;
import com.inspirenetz.api.test.core.builder.NotificationCampaignBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
public class NotificationCampaignFixture {




    public static NotificationCampaign standardNotificationCampaign() {


        NotificationCampaign notificationCampaign = NotificationCampaignBuilder.aNotificationCampaign()
                .withNtcName("TEST")
                .withNtcMerchantNo(1L)
                .withNtcTargetListeners(NotificationCampaignListeners.PUBLIC)
                .withNtcTargetChannels(":")
                .withNtcTargetSegments("69")

                .withNtcTargetMobile("8867987369:9495177881")
                .withNtcTargetEmail("fayizmuhamed@gmail.com:fayizkoyissan@customerinspire.com")
                .withNtcSmsContent("Test sms")
                .withNtcEmailContent("Test email")
                .withNtcEmailSubject("Test subject")
                .build();


        return notificationCampaign;


    }


    public static NotificationCampaign updatedStandardNotificationCampaign(NotificationCampaign notificationCampaign) {

        notificationCampaign.setNtcTargetChannels("1");

        return notificationCampaign;

    }


    public static Set<NotificationCampaign> standardNotificationCampaigns() {

        Set<NotificationCampaign> notificationCampaigns = new HashSet<NotificationCampaign>(0);

        NotificationCampaign notificationCampaignA  = NotificationCampaignBuilder.aNotificationCampaign()
                .withNtcMerchantNo(100L)
                .withNtcTargetListeners(NotificationCampaignListeners.SEGMENT)
                .withNtcTargetChannels("1")
                .withNtcSmsContent("Test sms")
                .build();

        notificationCampaigns.add(notificationCampaignA);



        NotificationCampaign notificationCampaignB = NotificationCampaignBuilder.aNotificationCampaign()
                .withNtcMerchantNo(100L)
                .withNtcTargetListeners(NotificationCampaignListeners.SEGMENT)
                .withNtcTargetChannels("2")
                .withNtcEmailSubject("Test Email")
                .withNtcEmailContent("Test Email Content")
                .build();

        notificationCampaigns.add(notificationCampaignB);

        return notificationCampaigns;

    }
}
