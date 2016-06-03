package fr.vuzi.dns;


import fr.vuzi.dns.datatype.DnsHeader;
import fr.vuzi.dns.datatype.DnsQuestion;

import java.util.Arrays;

public class DnsRequest {

    public final DnsHeader header;
    public final DnsQuestion question;

    public DnsRequest() {
        this.header = new DnsHeader();
        this.question = new DnsQuestion();
    }

    /**
     * Use the provided raw byte array to set the structure values
     * @param bytes The bytes to use
     */
    public void fromByteArray(byte[] bytes) {
        header.fromByteArray(bytes);
        question.fromByteArray(bytes, 12);
    }

    /**
     * Convert the request in a raw byte array
     *
     * @return The converted structure
     */
    public byte[] toByteArray() {
        byte[] headerValues = header.toByteArray();
        byte[] questionValues = question.toByteArray();

        byte[] result = Arrays.copyOf(headerValues, headerValues.length + questionValues.length);
        System.arraycopy(questionValues, 0, result, headerValues.length, questionValues.length);

        return result;
    }
}
