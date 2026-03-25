r1, r2, i1, r3, i2, i3, i4 = map(int, input())
s1, s2, s3 = map(str, [(r1+i1+i2+i4)%2, (r2+i1+i3+i4)%2, (r3+i2+i3+i4)%2])
s = s1+s2+s3
d = {
    "000": "000",
    "100": "r1",
    "010": "r2",
    "110": "i1",
    "001": "r3",
    "101": "i2",
    "011": "i3",
    "111": "i4"
}

err = d[s]

if err == "i1":
    i1 = 0 if i1 == 1 else 1
elif err == "i2":
    i2 = 0 if i2 == 1 else 1
elif err == "i3":
    i3 = 0 if i3 == 1 else 1
elif err == "i4":
    i4 = 0 if i4 == 1 else 1

i1, i2, i3, i4 = map(str, [i1, i2, i3, i4])
i = i1+i2+i3+i4

print("Ответ - " + i)
if err != "000":
    print("Ошибка в бите " + err)