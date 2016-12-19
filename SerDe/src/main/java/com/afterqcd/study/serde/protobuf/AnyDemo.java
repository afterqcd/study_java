package com.afterqcd.study.serde.protobuf;

import com.fx.productization.datanormalizer.domain.core.dto.MerchandiseBehaviourOuterClass;
import com.fx.productization.datanormalizer.domain.core.dto.MerchandiseOuterClass;
import com.fx.productization.datanormalizer.domain.core.dto.TextOuterClass;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by afterqcd on 2016/12/19.
 */
public class AnyDemo {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        MerchandiseOuterClass.Merchandise merchandise =
                MerchandiseOuterClass.Merchandise.newBuilder()
                        .setItemId(12345L)
                        .build();
        MerchandiseBehaviourOuterClass.MerchandiseBehaviour behaviour =
                MerchandiseBehaviourOuterClass.MerchandiseBehaviour.newBuilder()
                        .addItems(Any.pack(merchandise))
                        .build();

        byte[] bytes = behaviour.toByteArray();

        MerchandiseBehaviourOuterClass.MerchandiseBehaviour behaviour1
                = MerchandiseBehaviourOuterClass.MerchandiseBehaviour.parseFrom(bytes);
        Any item = behaviour1.getItems(0);
        System.out.println("Item is a Merchandise -> " + item.is(MerchandiseOuterClass.Merchandise.class));
        System.out.println("Item is a Text -> " + item.is(TextOuterClass.Text.class));
        MerchandiseOuterClass.Merchandise merchandise1 =
                item.unpack(MerchandiseOuterClass.Merchandise.class);
        System.out.println(merchandise1.getItemId());
    }
}
