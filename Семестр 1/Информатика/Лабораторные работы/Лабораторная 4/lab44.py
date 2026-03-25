import json
import re
from time import time

import yaml


def pars(f, k):
    f_new = ""
    space = "       " * k
    f_new += f"<wml>\n{space}"
    if isinstance(f, dict):
        for i, j in f.items():
            if isinstance(j, (dict, list)):
                f_new += f"{space}{i}: "
                f_new += pars(j, 0)
            else:
                if isinstance(j, int):
                    f_new += f"{space}'{i}': {j}\n"
                else:
                    f_new += f"{space}{i}: {j}\n"
    if isinstance(f, list):
        for i in f:
            if isinstance(i, (dict, list)):
                f_new += f"{space}- "
                f_new += pars(i, 0)
            else:
                if isinstance(i, int):
                    f_new += f"{space}- '{i}'\n"
                else:
                    f_new += f"{space}- {i}\n"
    
    return f_new


def main_task():
    f_start = open("lab4.json", "r", encoding="utf-8").read()
    f = f_start.replace("{", "").replace("}", "").replace("\"", "").replace(",", "").replace("  ", " ").splitlines()
    f_new = "\n".join(i[2:] for i in f if i.strip())
    return f_new


def task1():
    f = open("lab4.json", "r", encoding="utf-8")
    data = json.load(f)
    f_new = yaml.dump(data, allow_unicode=True)
    return f_new


def task2():
    f_start = open("lab4.json", "r", encoding="utf-8").read()
    f = re.sub("[\{\}\"\,]", "", f_start)
    f = re.sub("  ", " ", f)
    f_new = "\n".join(i[2:] for i in f.splitlines() if i.strip())
    return f_new


def task3():
    f_start = open("lab4.json", "r", encoding="utf-8").read()
    f_new = pars(f_start, 0)
    return f_new


time_start = time()
for i in range(100):
    main_task()
time_end = time()
print(f"Выполнение основного задания: {time_end - time_start}")


time_start = time()
for i in range(100):
    task1()
time_end = time()
print(f"Выполнение доп. задания 1: {time_end - time_start}")


time_start = time()
for i in range(100):
    task2()
time_end = time()
print(f"Выполнение доп. задания 2: {time_end - time_start}")


time_start = time()
for i in range(100):
    task3()
time_end = time()
print(f"Выполнение доп. задания 3: {time_end - time_start}")