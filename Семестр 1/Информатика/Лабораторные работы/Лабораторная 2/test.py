a = input().split()

for i, j in enumerate(a, 1):
    for v, k in enumerate(a[i:], i+1):
        if j[0] == k[0] and j[1] == k[1] and j[2] == k[2] and j[3] == k[3] and not (j[4] == k[4]):
            print(j[:4]+"X" + f" ({i}-{v})")
        elif j[0] == k[0] and j[1] == k[1] and j[2] == k[2] and not (j[3] == k[3]) and j[4] == k[4]:
            print(j[:3] + "X" + j[4] + f" ({i}-{v})")
        elif j[0] == k[0] and j[1] == k[1] and not (j[2] == k[2]) and j[3] == k[3] and j[4] == k[4]:
            print(j[:2] + "X" + j[3:] + f" ({i}-{v})")
        elif j[0] == k[0] and not (j[1] == k[1]) and j[2] == k[2] and j[3] == k[3] and j[4] == k[4]:
            print(j[:1] + "X" + j[2:] + f" ({i}-{v})")
        elif not (j[0] == k[0]) and j[1] == k[1] and j[2] == k[2] and j[3] == k[3] and j[4] == k[4]:
            print("X" + j[1:] + f" ({i}-{v})")


# 00001 00010 00100 00101 00110 01000 01010 01011 01100 01101 01110 01111 10000 10001 10011 10101 10110 10111 11001 11010 11110 11111
# 00X01 X0001 00X10 0X010 0010X 001X0 0X100 0X101 X0101 0X110 X0110 010X0 01X00 0101X 01X10 X1010 01X11 0110X 011X0 011X1 0111X X1110 X1111 1000X 100X1 10X01 1X001 10X11 101X1 1011X 1X110 1X111 11X10 1111X
# X0X01 X0X01 0XX10 0XX10 0X10X 0X1X0 0X10X 0X1X0 XX110 XX110 01XX0 01XX0 01X1X 01X1X X1X10 X1X10 011XX 011XX X111X X111X 10XX1 10XX1 1X11X 1X11X


# 00X00 00X11 0010X X0100 001X1 0X101 X0111 01X01 X1101 10X10 101X0 1X100 1011X 1X111 11X00 11X11 1110X 111X1 