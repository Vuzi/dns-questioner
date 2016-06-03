package fr.vuzi.dns.datatype;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class DnsAnswer {

    public String[] name;
    public short type;
    public short classData;
    public int ttl;
    public short rdlenght;

    public InetAddress ipAddress;

    public byte[] toByteArray() {
        // TODO
        return new byte[0];
    }

    public int fromByteArray(byte[] bytes, int offset) {
        int i = offset;
        int iPrevious = -1;

        List<String> names = new ArrayList<>();

        while(bytes[i] != 0x0) {
            if((bytes[i] & 0xC0) == 0xC0) {
                // Save the previous i value
                if(iPrevious < 0)
                    iPrevious = i;

                // Pointer
                i = (((bytes[i] & 0x3F) << 8) | bytes[i + 1] & 0xFF);

            } else {
                int size = bytes[i] & 0xFF;

                names.add(new String(bytes, i + 1, size));

                i += size + 1;
            }
        }

        if(iPrevious >= 0)
            i = iPrevious + 2;
        else
            i = i + 1; // null byte

        name = names.toArray(new String[names.size()]);

        type = (short) ((bytes[i++] << 8) | bytes[i++]);

        classData = (short) ((bytes[i++] << 8) | bytes[i++]);

        ttl = ((bytes[i++] << 24) | (bytes[i++] << 16) | (bytes[i++] << 8) | bytes[i++]);

        rdlenght = (short) ((bytes[i++] << 8) | bytes[i++]);

        if(type == 0x1) {
            try {
                ipAddress = InetAddress.getByAddress(new byte[] { bytes[i++], bytes[i++], bytes[i++], bytes[i++] });
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return i;
    }
}


