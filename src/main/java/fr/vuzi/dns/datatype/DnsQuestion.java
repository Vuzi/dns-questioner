package fr.vuzi.dns.datatype;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Dns question structure. The structure is defined by
 * the RFC 1035:
 *
 *                                  1  1  1  1  1  1
 *    0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |                                               |
 *  /                     QNAME                     /
 *  /                                               /
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |                     QTYPE                     |
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |                     QCLASS                    |
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *
 * Created by vuzi on 02/06/2016.
 */
public class DnsQuestion {

    /** List of name to be requested */
    public String[] qname;

    /** The type code */
    public short qtype;

    /** The class of the query */
    public short qclass;

    /**
     * Convert the question in a raw byte array
     *
     * @return The converted structure
     */
    public byte[] toByteArray() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            for (String label : qname) {
                for (String elem : label.split("\\.")) {
                    outputStream.write(elem.length());
                    outputStream.write(elem.getBytes());
                }
            }

            outputStream.write(0);

            outputStream.write((qtype >> 8) & 0xFF);
            outputStream.write((qtype) & 0xFF);

            outputStream.write((qclass >> 8) & 0xFF);
            outputStream.write((qclass) & 0xFF);

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int fromByteArray(byte[] question, int offset) {
        int i = offset;

        List<String> qnames = new ArrayList<>();

        while(question[i] != 0x0) {
            int size = question[i] & 0xFF;

            qnames.add(new String(question, i + 1, size));

            i += size + 1;
        }

        qname = qnames.toArray(new String[qnames.size()]);

        i++; // Null byte

        qtype = (short) ((question[i++] << 8) | question[i++]);
        qclass = (short) ((question[i++] << 8) | question[i++]);

        return i;
    }
}
