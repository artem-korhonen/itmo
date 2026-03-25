import re

f_start = open("lab4.json", "r", encoding="utf-8").read()
f = re.sub("[\{\}\"\,]", "", f_start)
f = re.sub("  ", " ", f)
f_new = "\n".join(i[2:] for i in f.splitlines() if i.strip())

open("lab42.yml", "w", encoding="utf-8").write(f_new)