def pars(f, tag):
    f_mass = []

    if isinstance(f, dict):
        for i, j in f.items():
            tag1 = tag
            tag1 += i + ","
            f_mass += pars(j, tag1)
    else:
        f_mass.append(tag + f)

    return f_mass


f_start = eval(open("lab4.json", "r", encoding="utf-8").read())
f = pars(f_start, "")
f_new = "\n".join(f)
open("lab45.csv", "w", encoding="utf-8").write(f_new)