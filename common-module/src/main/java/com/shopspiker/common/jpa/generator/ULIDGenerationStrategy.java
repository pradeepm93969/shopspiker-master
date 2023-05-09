package com.shopspiker.common.jpa.generator;

import com.github.f4b6a3.ulid.UlidCreator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

public class ULIDGenerationStrategy implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) throws HibernateException {
        Serializable id = UlidCreator.getUlid().toString();
        return id;
    }
}
