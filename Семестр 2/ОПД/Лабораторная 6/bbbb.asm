ORG 0x000
V0: WORD    $DEF,   0x180
V1: WORD    $INT1,   0x180
V2: WORD    $INT2,   0x180
V3: WORD    $DEF,   0x180
V4: WORD    $DEF,   0x180
V5: WORD    $DEF,   0x180
V6: WORD    $DEF,   0x180
V7: WORD    $DEF,   0x180

ORG 0x10
X:  WORD    0x0000
MIN:    WORD    0xFFEE
MAX:    WORD    0x0012

ORG 0x15
DEF:    IRET

ORG 0x20
INT1:   PUSH
    HLT
    LD  X
    ASL
    ASL
    ASL
    SUB X
    NEG
    DEC
    OUT 2
    POP
    HLT
    IRET

ORG 0x30
INT2:   PUSH
    HLT
    IN  4
    OR  X
    ST  X
    POP
    HLT
    IRET

ORG 0x40
CHECK:
CHECK1: CMP MIN
    BPL CHECK2
    LD  MIN
CHECK2: CMP MAX
    BMI RETURN
    LD  MIN
RETURN: RET

ORG 0x50
START:  DI
    LD  #0x9
    OUT 0x3
    LD  #0xA
    OUT 0x5
    EI
    JUMP    PROG

ORG 0x60
PROG:   EI
    CLA

PLOOP:   
    DI
    LD  X
    INC
    CALL    CHECK
    ST  X
    EI
    JUMP    PLOOP
