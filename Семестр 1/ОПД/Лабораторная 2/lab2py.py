b1 = 2
q = 3
n = 4
a = 0
b = 0
c = 0
d = 0

ac = 0


ac = q
c = ac
ac = n
ac -= 1
ac -= 1
n = ac


while ac > 0:
    ac = c
    b = ac
    ac = q
    ac -= 1
    a = ac
    while ac > 0:
        ac = c
        ac += b
        c = ac
        ac = a
        ac -= 1
        a = ac
    ac = n
    ac -= 1
    n = ac


ac = b1
a = ac
while ac > 0:
    ac = d
    ac += c
    d = ac
    ac = a
    ac -= 1
    a = ac

print(q, n, a, b, c, d)