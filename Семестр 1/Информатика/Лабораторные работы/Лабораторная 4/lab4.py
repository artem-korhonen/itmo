f_start = open("lab4.json", "r", encoding="utf-8").read()
f = f_start.replace("{", "").replace("}", "").replace("\"", "").replace(",", "").replace("  ", " ").splitlines()
f_new = "\n".join(i[2:] for i in f if i.strip())
open("lab4.yml", "w", encoding="utf-8").write(f_new)
