package fr.pinguet62.springspecification.core.builder.database.autoconfigure;

/**
 * {@code static} constants redefinition, because {@code enum} entries cannot be used into annotations.
 */
public class SpringSpecificationBeans {

    public static final String DATASOURCE_PROPERTIES_NAME = "springSpecification.dataSourceProperties";

    public static final String DATASOURCE_NAME = "springSpecification.dataSource";

    public static final String XA_DATASOURCE_NAME = "springSpecification.xaDataSource";

    public static final String DATASOURCE_MBEAN_NAME = "springSpecification.dataSourceMBean";

    public static final String DATASOURCE_INITIALIZER_NAME = "springSpecification.dataSourceInitializer";

    public static final String JPA_PROPERTIES_NAME = "springSpecification.jpaProperties";

    public static final String JPA_VENDOR_ADAPTER_NAME = "springSpecification.jpaVendorAdapter";

    public static final String ENTITY_MANAGER_FACTORY_BUILDER_NAME = "springSpecification.entityManagerFactoryBuilder";

    public static final String ENTITY_MANAGER_FACTORY_NAME = "springSpecification.entityManagerFactory";

    public static final String TRANSACTION_MANAGER_NAME = "springSpecification.transactionManager";

    public static final String TOMCAT_POOL_DATASOURCE_METADATA_PROVIDER_NAME = "springSpecification.tomcatPoolDataSourceMetadataProvider";

    public static final String HIKARI_POOL_DATASOURCE_METADATA_PROVIDER_NAME = "springSpecification.hikariPoolDataSourceMetadataProvider";

    public static final String COMMONSDBCP2_POOL_DATASOURCE_METADATA_PROVIDER_NAME = "springSpecification.commonsDbcp2PoolDataSourceMetadataProvider";

}
