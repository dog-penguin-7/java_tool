package jackyun;

public class ResponseModel {
    private String name;
    private Integer platValue;
    private String apiType;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlatValue() {
        return platValue;
    }
    public void setPlatValue(Integer platValue) {
        this.platValue = platValue;
    }

    public String getApiType() {
        return apiType;
    }
    public void setApiType(String apiType) {
        this.apiType = apiType;
    }
}