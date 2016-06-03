package fr.vuzi.dns;

import fr.vuzi.dns.datatype.DnsAnswer;
import fr.vuzi.dns.datatype.DnsHeader;
import fr.vuzi.dns.datatype.DnsQuestion;

import java.util.Arrays;

public class DnsResponse {

    public final DnsHeader header;
    public final DnsQuestion question;
    public final DnsAnswer answer;

    public DnsResponse() {
        this.header = new DnsHeader();
        this.question = new DnsQuestion();
        this.answer = new DnsAnswer();
    }

    /**
     * Use the provided raw byte array to set the structure values
     * @param bytes The bytes to use
     */
    public void fromByteArray(byte[] bytes) {
        int offset = 0;
        header.fromByteArray(bytes);
        offset = 12;
        offset = question.fromByteArray(bytes, offset);
        answer.fromByteArray(bytes, offset);
    }

    /**
     * Convert the request in a raw byte array
     *
     * @return The converted structure
     */
    public byte[] toByteArray() {
        byte[] headerValues = header.toByteArray();
        byte[] questionValues = answer.toByteArray();

        byte[] result = Arrays.copyOf(headerValues, headerValues.length + questionValues.length);
        System.arraycopy(questionValues, 0, result, headerValues.length, questionValues.length);

        return result;
    }
}