.data

input_addr:     .word   0x80
input_end_addr: .word   0xFF
output_addr:    .word   0x100


.text
.org 0x150


_start:
    @p input_addr a!
    dup b!

    find_end

    @p output_addr
    reverse

    halt


find_end:
    dup
    @
    '\n' -

    if found_end

    1 +
    a!
    find_end ;
    

found_end:
    1 -
    a!
    ;


reverse:
