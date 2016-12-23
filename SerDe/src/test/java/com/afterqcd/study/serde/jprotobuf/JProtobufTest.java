package com.afterqcd.study.serde.jprotobuf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by afterqcd on 2016/12/21.
 */
public class JProtobufTest {
    @Test
    public void testSerDeUser() throws Exception {
        User zhangSan = new User();
        zhangSan.setName("zhang san");
        zhangSan.setAge(15);

        Codec<User> userCodec = ProtobufProxy.create(User.class, true);
        byte[] bytes = userCodec.encode(zhangSan);

        User zhangSan1 = userCodec.decode(bytes);
        Assert.assertEquals("zhang san", zhangSan1.getName());
        Assert.assertEquals(15, zhangSan1.getAge());
    }
}

