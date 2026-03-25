import pandas as pd
from matplotlib import pyplot as plt
import seaborn as sns
import csv

f = list(csv.reader(open("lab52.csv")))

d1 = [[], [], [], []]
d2 = [[], [], [], []]
d3 = [[], [], [], []]
d4 = [[], [], [], []]

data = {"18/09/18": d1, "18/10/18": d2, "20/11/18": d3, "18/12/18": d4}
labels = ["Открытие", "Мин", "Макс", "Закрытие"]

for i in f[1:]:
    data[i[2]][0].append(i[4])
    data[i[2]][1].append(i[5])
    data[i[2]][2].append(i[6])
    data[i[2]][3].append(i[7])

f = open("lab521.csv", "w", encoding="utf-8")

f.write("Дата,Значение\n")
for date, d in data.items():
    for i in range(len(d)):
        for j in d[i]:
            f.write(f"{date} - {labels[i]},{j}\n")

f = open("lab521.csv", "r", encoding="utf-8")

df = pd.read_csv(f)

sns.boxplot(x=df["Дата"], y=df["Значение"])
plt.xticks(rotation=30, size=7)
plt.show()