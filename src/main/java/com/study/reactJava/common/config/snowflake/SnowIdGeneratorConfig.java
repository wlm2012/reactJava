package com.study.reactJava.common.config.snowflake;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;

import java.util.EnumSet;

public class SnowIdGeneratorConfig implements BeforeExecutionGenerator {


    @Override
    public long generate(SharedSessionContractImplementor session, Object owner, Object currentValue, EventType eventType) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return snowflake.nextId();
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EventTypeSets.INSERT_ONLY;
    }
}