package com.inspirenetz.api.test.core.fixture;


import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.SpielText;
import com.inspirenetz.api.test.core.builder.MessageSpielBuilder;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameenci on 8/9/14.
 */
public class MessageSpielFixture {


    public static MessageSpiel standardMessageSpiel(){

        Set<SpielText> childSet =new HashSet<>(0);

        SpielText spielText =new SpielText();

        spielText.setSptChannel(1);

        spielText.setSptLocation(2L);

        childSet.add(spielText);

        MessageSpiel messageSpiel = MessageSpielBuilder.aMessageSpiel()

                .withMsiName("testmessage")
                .withMsiTariffClass("nothing")
                .withSpielTexts(childSet)
                .build();


        return messageSpiel;


    }


    public static MessageSpiel updatedStandMessageSpiel(MessageSpiel messageSpiel) {

        messageSpiel.setMsiName("test2");

        return messageSpiel;

    }


    public static Set<MessageSpiel> standardMessageSpiels() {

        Set<MessageSpiel> messageSpiels = new HashSet<MessageSpiel>(0);

        Set<SpielText> childSet =new HashSet<>(0);

        SpielText spielText =new SpielText();

        spielText.setSptChannel(2);

        spielText.setSptLocation(2L);

        childSet.add(spielText);

        Set<SpielText> childSet1=new HashSet<>(0);

        SpielText spielText1 =new SpielText();

        spielText.setSptChannel(1);

        spielText.setSptLocation(2L);

        childSet.add(spielText1);

        MessageSpiel message  = MessageSpielBuilder.aMessageSpiel()

                .withMsiName("Test Message1")
                .withSpielTexts(childSet)
                .build();

        messageSpiels.add(message);

//        MessageSpiel message1 = MessageSpielBuilder.aMessageSpiel()
//
//                .withMsiName("Test Message111")
//                .withSpielTexts(childSet1)
//
//                .build();
//        messageSpiels.add(message1);



        return messageSpiels;



    }
}
