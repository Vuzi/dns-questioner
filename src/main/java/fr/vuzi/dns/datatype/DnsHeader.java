package fr.vuzi.dns.datatype;

import fr.vuzi.dns.datatype.enums.OPCODE;
import fr.vuzi.dns.datatype.enums.RCODE;


/**
 * Dns header structure. The structure is defined by
 * the RFC 1035:
 *                                  1  1  1  1  1  1
 *    0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |                      ID                       |
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |QR|   Opcode  |AA|TC|RD|RA|   Z    |   RCODE   |
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |                    QDCOUNT                    |
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |                    ANCOUNT                    |
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |                    NSCOUNT                    |
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |                    ARCOUNT                    |
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *
 * Created by vuzi on 02/06/2016.
 */
public class DnsHeader {

    /** 16 bit identifier of the query */
    public short id;

    // Flags
    /** True for query, false for response */
    public boolean qr;

    /** The kind of query */
    public OPCODE opcode;

    /** If the responding name server is an authority in question section, false otherwise */
    public boolean aa;

    /** True if the message was truncated, false otherwise */
    public boolean tc;

    /** True if the recursion is desired, false otherwise */
    public boolean rd;

    /** True if the recursion is available, false otherwise */
    public boolean ra;

    /** Reserved */
    public short z;

    /** The response code value */
    public RCODE rcode;

    /** TYhe number of questions */
    public short qdcount;

    /** The number of resource records */
    public short ancount;

    /** The number of name server resource records */
    public short nscount;

    /** The number of additional resource records */
    public short arcount;

    /**
     * Convert the header in a raw byte array
     *
     * @return The converted structure
     */
    public byte[] toByteArray() {

        byte[] header = new byte[12];

        header[0] = (byte) ((id >> 8) & 0xFF);
        header[1] = (byte) (id & 0xFF);

        header[2] |= (qr ? 1 : 0);
        header[2] <<= 4;

        header[2] |= opcode.value;
        header[2] <<= 1;

        header[2] |= (aa ? 1 : 0);
        header[2] <<= 1;

        header[2] |= (tc ? 1 : 0);
        header[2] <<= 1;

        header[2] |= (rd ? 1 : 0);

        header[3] |= (ra ? 1 : 0);
        header[3] <<= 3;
        header[3] |= z;
        header[3] <<= 4;
        header[3] |= rcode.value;

        header[4] = (byte) ((qdcount >> 8) & 0xFF);
        header[5] = (byte) (qdcount & 0xFF);

        header[6] = (byte) ((ancount >> 8) & 0xFF);
        header[7] = (byte) (ancount & 0xFF);

        header[8] = (byte) ((nscount >> 8) & 0xFF);
        header[9] = (byte) (nscount & 0xFF);

        header[10] = (byte) ((arcount >> 8) & 0xFF);
        header[11] = (byte) (arcount & 0xFF);

        return header;
    }

    public void fromByteArray(byte[] header) {
        if(header.length < 12)
            throw new IndexOutOfBoundsException();

        id = (short) ((header[0] << 8) | header[1]);

        qr = (header[2] & 0x80) == 0x80;

        opcode = OPCODE.getValue((header[2] >> 3) & 0xF);

        aa = (header[2] & 0x4) == 0x4;

        tc = (header[2] & 0x2) == 0x2;

        rd = (header[2] & 0x1) == 0x1;

        ra = (header[3] & 0x80) == 0x80;

        z = (short) ((header[3] >> 4) & 0x7);

        rcode = RCODE.getValue(header[3] & 0xF);

        qdcount = (short) ((header[4] << 8) | header[5] & 0xFF);

        ancount = (short) ((header[6] << 8) | header[7] & 0xFF);

        nscount = (short) ((header[8] << 8) | header[9] & 0xFF);

        arcount = (short) ((header[10] << 8) | header[11] & 0xFF);
    }
}
