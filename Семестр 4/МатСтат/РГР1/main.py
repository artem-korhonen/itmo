import csv
import math
import matplotlib.pyplot as plt
import pandas as pd

file_path = 'RGR1_A-7_X1-X4.csv'
column_name = 'X4'

df = pd.read_csv(file_path)
data = df[column_name].dropna().tolist()
n = len(data)
variation_series = sorted(data)

n = len(data)
variation_series = sorted(data)


def get_mean(data_list):
    return sum(data_list) / len(data_list)

def get_variance(data_list, ddof=0):
    mu = get_mean(data_list)
    return sum((x - mu) ** 2 for x in data_list) / (len(data_list) - ddof)

def get_median(sorted_data):
    n_size = len(sorted_data)
    mid = n_size // 2
    if n_size % 2 == 0:
        return (sorted_data[mid - 1] + sorted_data[mid]) / 2
    return sorted_data[mid]

def get_quantile(sorted_data, q):
    idx = q * (len(sorted_data) - 1)
    lower = int(math.floor(idx))
    upper = int(math.ceil(idx))
    weight = idx - lower
    res = sorted_data[lower] * (1 - weight) + sorted_data[upper] * weight
    return res

mean_val = get_mean(data)
var_biased = get_variance(data, ddof=0)
var_unbiased = get_variance(data, ddof=1)
std_val = math.sqrt(var_unbiased)
median_val = get_median(variation_series)
q1 = get_quantile(variation_series, 0.25)
q3 = get_quantile(variation_series, 0.75)

print(f"--- Статистика для {column_name} ---")
print(f"Среднее: {mean_val:.4f}")
print(f"Медиана: {median_val:.4f}")
print(f"Дисперсия (несмещенная): {var_unbiased:.4f}")
print(f"Cтандартное отклонение: {std_val:.4f}")
print(f"Квантили: Q1={q1:.4f}, Q3={q3:.4f}")

# Группировка данных для гистограммы
num_bins = int(1 + 3.322 * math.log10(n))  # формула Стёрджеса
min_val, max_val = min(data), max(data)
bin_width = (max_val - min_val) / num_bins
bin_edges = [min_val + i * bin_width for i in range(num_bins + 1)]

counts = [0] * num_bins
for x in data:
    for i in range(num_bins):
        if bin_edges[i] <= x < bin_edges[i+1] or (i == num_bins - 1 and x == max_val):
            counts[i] += 1
            break

bin_midpoints = [(bin_edges[i] + bin_edges[i+1]) / 2 for i in range(num_bins)]

# Группированные показатели
grouped_mean = sum(counts[i] * bin_midpoints[i] for i in range(num_bins)) / n
grouped_var = sum(counts[i] * (bin_midpoints[i] - grouped_mean)**2 for i in range(num_bins)) / n
grouped_std = math.sqrt(grouped_var)

print(f"\n--- Группированные показатели ---")
print(f"Среднее группированное: {grouped_mean:.4f}")
print(f"Стандартное отклонение группированное: {grouped_std:.4f}")


plt.figure(figsize=(12, 8))

# Эмпирическая функция распределения
plt.subplot(2, 1, 1)
x_ecdf = variation_series
y_ecdf = [(i + 1) / n for i in range(n)]
plt.step(x_ecdf, y_ecdf, where='post', color='green')
plt.title('Эмпирическая функция распределения')
plt.xlabel('Значение')
plt.ylabel('F(x)')
plt.grid(True, alpha=0.3)

# Гистограмма
plt.subplot(2, 1, 2)
densities = [c / (n * bin_width) for c in counts]
plt.bar(bin_midpoints, densities, width=bin_width, color='skyblue', edgecolor='black', alpha=0.7)

plt.axvline(grouped_mean, color='red', linestyle='dashed', linewidth=2, label=f'Среднее: {grouped_mean:.2f}')
plt.axvspan(grouped_mean - grouped_std, grouped_mean + grouped_std, color='red', alpha=0.1, label='Интервал [x-sigma, x+sigma]')

plt.title(f'Гистограмма распределения {column_name}')
plt.xlabel('Значение')
plt.ylabel('Плотность')
plt.legend()
plt.grid(True, alpha=0.3)

plt.tight_layout()
plt.show()