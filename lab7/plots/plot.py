import numpy as np
import matplotlib.pyplot as plt
import csv

asym_time = []
cond_time = []

with open('asym5.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 5
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 5.0
    asym_time.append(avg)

with open('asym10.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 10
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 10.0
    asym_time.append(avg)

with open('asym15.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 15
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 15.0
    asym_time.append(avg)

with open('asym20.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 20
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 20.0
    asym_time.append(avg)

with open('asym25.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 25
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 25.0
    asym_time.append(avg)

with open('cond5.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 5
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 5.0
    cond_time.append(avg)

with open('cond6.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 6
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 6.0
    cond_time.append(avg)

with open('cond7.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 7
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 7.0
    cond_time.append(avg)

with open('cond8.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 8
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 8.0
    cond_time.append(avg)

with open('cond9.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 9
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 9.0
    cond_time.append(avg)

with open('cond10.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 10
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 10.0
    cond_time.append(avg)

with open('cond11.csv', 'rt') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    phil_wait = [0] * 11
    for row in csv_reader:
        phil_wait[int(row[0])] += int(row[1])
    avg = sum(phil_wait) / 11.0
    cond_time.append(avg)

plt.subplot(2, 1, 1)
plt.title('Philosophers wait time')
plt.plot(np.arange(5, 26, step=5), asym_time, 'ro', label='Asym')
plt.legend()
plt.xticks(np.arange(5, 26, step=5))
plt.ylabel('Avg time spent waiting [ms]')
plt.grid(True)

plt.subplot(2, 1, 2)
plt.plot(np.arange(5, 12, step=1), cond_time, 'bo', label='Conductor')
plt.legend()
plt.xlabel('Number of philosophers')
plt.xticks(np.arange(5, 12, step=1))
plt.ylabel('Avg time spent waiting [ms]')
plt.grid(True)

plt.savefig('plot.png')
plt.show()
