package yapicommon.model.enums;


/**
 * 用户角色枚举
 */
public enum InterfaceInfoStatusEnum {

    OFFLINE("关闭", 0),
    ONLINE("开启", 1);

    private final String text;

    private final Integer value;

    InterfaceInfoStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }


    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }
}
