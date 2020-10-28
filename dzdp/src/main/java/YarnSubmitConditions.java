import java.util.List;
import java.util.Map;

/**
 * @Author: lcz
 * @DateTime: 2020-10-28 16:59
 * @Description: TODO
 */
public class YarnSubmitConditions {
    private List<String> otherArgs;
    private String applicationJar;
    private String mainClass;
    private String appName;
    private String[] additionalJars;
    private String sparkYarnJars;
    public String[] files;
    public String yarnResourcemanagerAddress;
    public String sparkFsDefaultFS;
    private String driverMemory;
    private String numExecutors;
    private String executorMemory;
    private String executorCores;
    private String sparkHome;
    private String deployMode;
    private String master;
    public Map<String, String> sparkProperties;

    public List<String> getOtherArgs() {
        return otherArgs;
    }

    public void setOtherArgs(List<String> otherArgs) {
        this.otherArgs = otherArgs;
    }

    public String getApplicationJar() {
        return applicationJar;
    }

    public void setApplicationJar(String applicationJar) {
        this.applicationJar = applicationJar;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String[] getAdditionalJars() {
        return additionalJars;
    }

    public void setAdditionalJars(String[] additionalJars) {
        this.additionalJars = additionalJars;
    }

    public String getSparkYarnJars() {
        return sparkYarnJars;
    }

    public void setSparkYarnJars(String sparkYarnJars) {
        this.sparkYarnJars = sparkYarnJars;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public String getYarnResourcemanagerAddress() {
        return yarnResourcemanagerAddress;
    }

    public void setYarnResourcemanagerAddress(String yarnResourcemanagerAddress) {
        this.yarnResourcemanagerAddress = yarnResourcemanagerAddress;
    }

    public Map<String, String> getSparkProperties() {
        return sparkProperties;
    }

    public void setSparkProperties(Map<String, String> sparkProperties) {
        this.sparkProperties = sparkProperties;
    }

    public String getSparkFsDefaultFS() {
        return sparkFsDefaultFS;
    }

    public void setSparkFsDefaultFS(String sparkFsDefaultFS) {
        this.sparkFsDefaultFS = sparkFsDefaultFS;
    }

    public String getDriverMemory() {
        return driverMemory;
    }

    public void setDriverMemory(String driverMemory) {
        this.driverMemory = driverMemory;
    }

    public String getNumExecutors() {
        return numExecutors;
    }

    public void setNumExecutors(String numExecutors) {
        this.numExecutors = numExecutors;
    }

    public String getExecutorMemory() {
        return executorMemory;
    }

    public void setExecutorMemory(String executorMemory) {
        this.executorMemory = executorMemory;
    }

    public String getExecutorCores() {
        return executorCores;
    }

    public void setExecutorCores(String executorCores) {
        this.executorCores = executorCores;
    }

    public String getSparkHome() {
        return sparkHome;
    }

    public void setSparkHome(String sparkHome) {
        this.sparkHome = sparkHome;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public void setDeployMode(String deployMode) {
        this.deployMode = deployMode;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
