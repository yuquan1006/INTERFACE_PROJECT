package org.apache.jmeter.protocol.jdbc.sampler;

import org.apache.jmeter.testbeans.BeanInfoSupport;

import java.beans.PropertyDescriptor;

public class DbunitSamplerBeanInfo extends BeanInfoSupport {

    public DbunitSamplerBeanInfo() {
        super(DbunitSampler.class);

        createPropertyGroup("varName",
                new String[]{"dataSource"});

        createPropertyGroup("operation",
                new String[]{
                        "operationType",
                        "schema",
                        "tables",
                        "backupFileName"});

        PropertyDescriptor p = property("dataSource");
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "");

        p = property("operationType");
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "backup");
        p.setValue(NOT_OTHER,Boolean.TRUE);
        p.setValue(TAGS, new String[] {"backup", "rollback"});

        p = property("schema"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, ""); // $NON-NLS-1$

        p = property("tables"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, ""); // $NON-NLS-1$

        p = property("backupFileName"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, ""); // $NON-NLS-1$

    }

}
