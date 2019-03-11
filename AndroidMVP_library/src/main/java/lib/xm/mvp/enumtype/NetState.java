package lib.xm.mvp.enumtype;

/**
 * Created by boka_lyp on 2016/2/18.
 */
public enum NetState {

    NETWORKTYPE_INVALID(0), NETWORKTYPE_WAP(1), NETWORKTYPE_2G(2), NETWORKTYPE_3G(3), NETWORKTYPE_WIFI(4);
    int typeCode;

    NetState(int typeCode) {
        this.typeCode = typeCode;
    }

    public static NetState parse(int i) {
        switch (i) {
            case 0:
                return NETWORKTYPE_INVALID;
            case 1:
                return NETWORKTYPE_WAP;
            case 2:
                return NETWORKTYPE_2G;
            case 3:
                return NETWORKTYPE_3G;
            case 4:
                return NETWORKTYPE_WIFI;
            default:
                return NETWORKTYPE_INVALID;
        }
    }

    public int getTypeCode() {
        return this.typeCode;
    }

    public String getName() {
        return this.name();
    }


}
