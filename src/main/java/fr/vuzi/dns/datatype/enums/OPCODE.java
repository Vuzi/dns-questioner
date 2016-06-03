package fr.vuzi.dns.datatype.enums;

public enum OPCODE {
    QUERY(0), IQUERY(1), STATUS(2);

    public final int value;

    OPCODE(int value) {
        this.value = value;
    }

    public static OPCODE getValue(int value) {
        for(OPCODE opcode : OPCODE.values()) {
            if(opcode.value == value)
                return opcode;
        }

        throw new ArrayIndexOutOfBoundsException();
    }
}