.data

input_addr:     .word   0x80
output_addr:    .word   0x84

INPUT:          .word   0x0
TMP:            .word   0x0
REV:            .word   0x0
COUNT:          .word   0x20
ONE:            .word   0x1

.text
.org 0x90

_start:
    load        input_addr
    load_acc

    store_addr  INPUT
    store_addr  TMP

    load_imm    0
    store_addr  REV

    load_imm    0x20
    store_addr  COUNT

LOOP:
    load_addr   COUNT
    beqz        END

    load_addr   REV
    shiftl      ONE
    store_addr  REV

    load_addr   TMP
    and         ONE
    or          REV
    store_addr  REV

    load_addr   TMP
    shiftr      ONE
    store_addr  TMP

    load_addr   COUNT
    sub         ONE
    store_addr  COUNT

    jmp         LOOP

END:
    load_addr   INPUT
    sub         REV

    beqz        YES
    jmp         NO

YES:
    load_imm    1
    store_ind   output_addr
    halt

NO:
    load_imm    0
    store_ind   output_addr
    halt