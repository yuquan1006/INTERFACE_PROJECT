package org.apache.jmeter.protocol.jdbc.sampler;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.protocol.jdbc.config.DataSourceElement;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.util.JOrphanUtils;
import org.dbunit.database.*;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.operation.TransactionOperation;

import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DbunitSampler  extends AbstractSampler implements  TestBean{
    private static final long serialVersionUID = 3039485835104016927L;

    private static final Set<String> APPLIABLE_CONFIG_CLASSES = new HashSet<>(
            Arrays.asList("org.apache.jmeter.config.gui.SimpleConfigGui"));
    private static final String ENCODING = StandardCharsets.UTF_8.name();
    private static final String COMMA = ","; // $NON-NLS-1$
    private static final String backupFileSuffix = ".xml";

    private transient String dataSource;
    private transient String operationType;
    private transient String schema;
    private transient String tables;
    private transient String backupFileName;

    /**
     * Does configElement apply to Sampler
     *
     * @param configElement {@link ConfigTestElement}
     * @return boolean
     */
    @Override
    public boolean applies(ConfigTestElement configElement) {
        String guiClass = configElement.getProperty(TestElement.GUI_CLASS).getStringValue();
        return APPLIABLE_CONFIG_CLASSES.contains(guiClass);
    }

    /**
     * Obtains statistics about the given Entry, and packages the information
     * into a SampleResult.
     *
     * @param e the Entry (TODO seems to be unused)
     * @return information about the sample
     */
    @Override
    public SampleResult sample(Entry e) {
        SampleResult res = new SampleResult();
        res.setSampleLabel(getName());
        res.setSamplerData(toString());
        res.setDataType(SampleResult.TEXT);
        res.setContentType("text/plain"); // $NON-NLS-1$
        res.setDataEncoding(ENCODING);

        // Assume we will be successful
        res.setSuccessful(true);
        res.setResponseMessageOK();
        res.setResponseCodeOK();

        res.sampleStart();
        IDatabaseConnection conn = null;
        try {
            if(JOrphanUtils.isBlank(this.getDataSource())) {
                throw new IllegalArgumentException("Variable Name must not be null in "+getName());
            }

            try {
                DataSource dataSource = DataSourceElement.getDataSource(this.getDataSource());
                String driverClassName = DataSourceElement.getDriverClassName(this.getDataSource());
                conn = getConn(dataSource, this.getSchema(), driverClassName);
            } finally {
                res.connectEnd();
            }
            if (this.getOperationType().equals("backup")){
                String[] tables = this.getTables().split(COMMA);
                backupCustom(conn, tables, this.getBackupFileName());
                res.setResponseData("backup schema:" + this.getSchema() + " tables:" +this.getTables()+" success", StandardCharsets.UTF_8.name());
            } else if (this.getOperationType().equals("rollback")){
                rollback(conn, this.getBackupFileName());
                res.setResponseData("rollback schema:" + this.getSchema() + " backupFileName:" +this.getBackupFileName()+" success", StandardCharsets.UTF_8.name());
            }
            res.setResponseHeaders(DataSourceElement.getConnectionInfo(this.getDataSource()));
        } catch (SQLException ex) {
            final String errCode = Integer.toString(ex.getErrorCode());
            res.setResponseMessage(ex.toString());
            res.setResponseCode(ex.getSQLState()+ " " +errCode);
            res.setResponseData(ex.getMessage().getBytes());
            res.setSuccessful(false);
        } catch (Exception ex) {
            res.setResponseMessage(ex.toString());
            res.setResponseCode("000");
            res.setResponseData(ObjectUtils.defaultIfNull(ex.getMessage(), "NO MESSAGE").getBytes());
            res.setSuccessful(false);
        }

        // TODO: process warnings? Set Code and Message to success?
        res.sampleEnd();
        return res;
    }

    private void rollback(IDatabaseConnection conn, String backupFileName) throws Exception {
        //当前 jmx 路径
        String baseDir = FileServer.getFileServer().getBaseDir();
        File backupFile = new File(baseDir + File.separator + backupFileName + backupFileSuffix);
        // get the temp file
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        InputStream inputStream = new FileInputStream(backupFile);
        IDataSet ds =builder.build(inputStream);

        // recover database
        try {
            DatabaseOperation operation = new TransactionOperation(DatabaseOperation.CLEAN_INSERT);
            operation.execute(conn, ds);
        } finally {
            conn.close();
            JOrphanUtils.closeQuietly(inputStream);
        }
    }

    private void backupCustom(IDatabaseConnection conn,String[] tableNames,String backupFileName) throws DataSetException, IOException {
        // back up specific files
        QueryDataSet qds = new QueryDataSet(conn);
        for (String tableName : tableNames) {
            qds.addTable(tableName);
        }
        //当前 jmx 路径
        String baseDir = FileServer.getFileServer().getBaseDir();
        File backupFile = new File(baseDir + File.separator  + backupFileName + backupFileSuffix);
        FlatXmlDataSet.write(qds, new FileWriter(backupFile), "UTF8"); //注意: 这里应该是UTF8,而不能是UTF-8
    }

    private IDatabaseConnection getConn(DataSource dataSource, String schema, String driverClassName) throws SQLException {
        IDatabaseConnection conn = new DatabaseDataSourceConnection(dataSource, schema);

        DatabaseConfig dbConfig = conn.getConfig();

        if (driverClassName.contains("oracle")){
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,  new OracleDataTypeFactory());
        } else if (driverClassName.contains("mysql")){
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,  new MySqlDataTypeFactory());
            dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER,  new MySqlMetadataHandler());
        }

        return conn;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    public String getBackupFileName() {
        return backupFileName;
    }

    public void setBackupFileName(String backupFileName) {
        this.backupFileName = backupFileName;
    }

    @Override
    public String toString() {
        return "DbunitSampler{" +
                "dataSource='" + dataSource + '\'' +
                ", operationType='" + operationType + '\'' +
                ", schema='" + schema + '\'' +
                ", tables='" + tables + '\'' +
                ", backupFileName='" + backupFileName + '\'' +
                "} " + super.toString();
    }
}
