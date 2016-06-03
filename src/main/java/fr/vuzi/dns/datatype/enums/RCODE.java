package fr.vuzi.dns.datatype.enums;


public enum RCODE {
    NO_ERROR(0), FORMAT_ERROR(1), SERVER_FAILURE(2), NAME_ERROR(3), NOT_IMPLEMENTED(4), REFUSED(5);

    public final int value;

    RCODE(int value) {
        this.value = value;
    }

    public static RCODE getValue(int value) {
        for(RCODE rcode : RCODE.values()) {
            if(rcode.value == value)
                return rcode;
        }

        throw new ArrayIndexOutOfBoundsException();
    }
}