ORG 0x03BB

NUM1:   WORD    0x0000
NUM2:   WORD    0xFFFF
NUM3:   WORD    0xABCD

RES1:   WORD    0x0
RES2:   WORD    0x0
RES3:   WORD    0x0

CHECK1: WORD    0xFFFF
CHECK2: WORD    0x0000
CHECK3: WORD    0x5432

FINAL1: WORD    0x0
FINAL2: WORD    0x0
FINAL3: WORD    0x0

FINAL:  WORD    0x1

START:  CLA
    CALL    TEST1
    CALL    TEST2
    CALL    TEST3
    LD      FINAL1
    BEQ     ERROR
    LD      FINAL2
    BEQ     ERROR
    LD      FINAL3
    BEQ     ERROR
    HLT
ERROR:  LD  0x0
    ST      FINAL
    HLT

TEST1:  LD  NUM1
    PUSH
    WORD    0x0F03
    POP
    ST      RES1
    CMP     CHECK1
    BEQ     DONE1
ERROR1: LD  0x0
    ST      FINAL1
    RET
DONE1:  LD  0x1
    ST      FINAL1
    RET

TEST2:  LD  NUM2
    PUSH
    WORD    0x0F03
    POP
    ST      RES2
    CMP     CHECK2
    BEQ     DONE2
ERROR2: LD  0x0
    ST      FINAL2
    RET
DONE2:  LD  0x1
    ST      FINAL2
    RET

TEST3:  LD  NUM3
    PUSH
    WORD    0x0F03
    POP
    ST      RES3
    CMP     CHECK3
    BEQ     DONE3
ERROR3: LD  0x0 
    ST      FINAL3
    RET
DONE3:  LD  0x1
    ST      FINAL3
    RET
