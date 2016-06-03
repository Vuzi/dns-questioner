# dns-questioner
Tool for questioning the 8.8.8.8 Google's DNS

# Usage

Simply compile/run the jar, and use it with any hostname:

    ./java -jar dns-questioner vuzi.fr

Output:

    Request > 
    db 42 01 00 00  01 00 00 00 00  
    00 00 01 67 08  76 69 6c 6c 65  
    72 65 7a 02 66  72 00 00 01 00  
    01 
    -------------------------------------
    Response > 
    db 42 81 80 00  01 00 01 00 00  
    00 00 01 67 08  76 69 6c 6c 65  
    72 65 7a 02 66  72 00 00 01 00  
    01 c0 0c 00 01  00 01 00 00 29  
    36 00 04 25 bb  7b b2 
    -------------------------------------

    Address of g.villerez.fr -> 37.187.123.178
