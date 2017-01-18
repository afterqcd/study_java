package com.afterqcd.study.serde.protobuf.opm;

import net.badata.protobuf.converter.Configuration;
import net.badata.protobuf.converter.Converter;

/**
 * Created by afterqcd on 2016/12/23.
 */
public class ConverterHolder {
    private static final Converter converter = Converter.create(
            Configuration.builder().withInheritedFields().build()
    );

    /**
     * Get default converter.
     * @return
     */
    public static Converter getConverter() {
        return converter;
    }
}
