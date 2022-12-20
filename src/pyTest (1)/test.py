import csv
import json
import pandas as pd
import numpy as np



f = open('dong_code.csv','r')
rdr = csv.reader(f)
dongList = dict()

for line in rdr:
    dongList[line[2]] = line[3]


print(dongList)

temp = pd.read_csv('생활이동_행정동_202205\생활이동_행정동_2022.05_00시.csv',encoding='cp949')
temp.columns = ['month','day','arriveTime','startArr','endArr','sex','age','moveType','averageMoveTime','peopleNum']
print(temp)

func = lambda x: dongList.get(x)
print()
print(type(str(temp.endArr[0])))

temp = temp.astype({'endArr':'str'})

temp['name'] = temp.endArr.map(func)


print(temp)

