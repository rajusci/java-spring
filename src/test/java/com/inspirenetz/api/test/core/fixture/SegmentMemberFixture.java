package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.test.core.builder.SegmentMemberBuilder;

import javax.swing.text.Segment;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 29/5/14.
 */
public class SegmentMemberFixture {

    public static SegmentMember standardSegmentMember() {

        SegmentMember segmentMember = SegmentMemberBuilder.aSegmentMember()
                .withSgmSegmentId(200L)
                .withSgmCustomerNo(126L)
                .build();

        return segmentMember;

    }


    public static Set<SegmentMember> standardSegmentMembers() {

        Set<SegmentMember>  segmentMembers = new HashSet<>(0);

        SegmentMember segmentMember1 = SegmentMemberBuilder.aSegmentMember()
                .withSgmSegmentId(200L)
                .withSgmCustomerNo(126L)
                .build();

        segmentMembers.add(segmentMember1);


        SegmentMember segmentMember2 = SegmentMemberBuilder.aSegmentMember()
                .withSgmSegmentId(201L)
                .withSgmCustomerNo(126L)
                .build();

        segmentMembers.add(segmentMember2);


        return segmentMembers;
    }

}
