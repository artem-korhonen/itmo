import re

def pars(f, k):
    f_new = ""
    space = " " * k
    if isinstance(f, dict):
        for i, j in f.items():
            if isinstance(j, (dict, list)):
                f_new += f"{space}{i}: \n"
                f_new += pars(j, k+2)
            else:
                if isinstance(j, int):
                    f_new += f"{space}'{i}': {j}\n"
                else:
                    f_new += f"{space}{i}: {j}\n"
    if isinstance(f, list):
        for i in f:
            if isinstance(i, (dict, list)):
                f_new += f"{space}- "
                f_new += pars(i, k+2)
            else:
                if isinstance(i, int):
                    f_new += f"{space}- '{i}'\n"
                else:
                    f_new += f"{space}- {i}\n"
    f_new = re.sub("-\s*", "- ", f_new)
    return f_new

f_start = eval(open("laba4.json", "r", encoding="utf-8").read())
f_new = pars(f_start, 0)
open("lab43.yml", "w", encoding="utf-8").write(f_new)