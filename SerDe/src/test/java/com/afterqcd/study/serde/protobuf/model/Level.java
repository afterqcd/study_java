package com.afterqcd.study.serde.protobuf.model;

import com.afterqcd.study.serde.protobuf.opm.ProtoEnum;
import com.afterqcd.study.serde.protobuf.opm.ProtoEnumNumber;
import com.afterqcd.study.serde.protobuf.dto.Dto;

/**
 * Created by afterqcd on 2016/12/21.
 */
@ProtoEnum(Dto.Level.class)
public enum Level {
    @ProtoEnumNumber(0) Debug("debug"),
    @ProtoEnumNumber(1) Info("info"),
    @ProtoEnumNumber(2) Warn("warn"),
    @ProtoEnumNumber(3) Error("error");

    private final String desc;
    Level(String desc) {
        this.desc = desc;
    }
}
