package fr.vuzi;


import fr.vuzi.dns.DnsRequest;
import fr.vuzi.dns.DnsResponse;
import fr.vuzi.dns.datatype.enums.OPCODE;
import fr.vuzi.dns.datatype.enums.RCODE;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {

    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("Usage : dns-questionner <hostname>");
            System.exit(1);
        }

        try {
            DnsRequest request = new DnsRequest();
            request.header.id = (short) 0xdb42;
            request.header.qr = false;
            request.header.opcode = OPCODE.QUERY;
            request.header.aa = false;
            request.header.tc = false;
            request.header.rd = true;
            request.header.ra = false;
            request.header.z = 0;
            request.header.rcode = RCODE.NO_ERROR;

            request.header.qdcount = 1;

            request.question.qname = new String[] { args[0] };
            request.question.qtype = 0x1;
            request.question.qclass = 0x1;

            byte[] datagram = request.toByteArray();

            int i = 0;
            System.out.println("Request > ");
            for(byte bb : datagram) {
                System.out.print(String.format("%02x ", bb & 0xFF));
                if((i + 1) % 5 == 0)
                    System.out.print(' ');
                if((i + 1) % 10 == 0)
                    System.out.print('\n');
                i++;
            }
            System.out.println("\n-------------------------------------");

            InetAddress googleDns = InetAddress.getByName("8.8.8.8");
            DatagramPacket dataSent = new DatagramPacket(datagram, datagram.length, googleDns, 53);
            DatagramSocket socket = new DatagramSocket();

            socket.send(dataSent);

            DatagramPacket dataReceived = new DatagramPacket(new byte[512], 512);
            socket.receive(dataReceived);

            byte[] bytes = new byte[dataReceived.getLength()];
            System.arraycopy(dataReceived.getData(), dataReceived.getOffset(), bytes, 0, dataReceived.getLength());

            System.out.println("Response > ");

            DnsResponse response = new DnsResponse();
            response.fromByteArray(bytes);

            i = 0;
            for(byte val : bytes) {
                System.out.print(String.format("%02x ", val & 0xFF));
                if((i + 1) % 5 == 0)
                    System.out.print(' ');
                if((i + 1) % 10 == 0)
                    System.out.print('\n');

                i++;
            }
            System.out.println("\n-------------------------------------\n");

            if(response.answer.ipAddress != null)
                System.out.println(String.format("Address of %s -> %s",
                        request.question.qname[0], response.answer.ipAddress.getHostAddress()));
            else
                System.out.println(String.format("Address of %s -> %s", request.question.qname[0], "not found"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}