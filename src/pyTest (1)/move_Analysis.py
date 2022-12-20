import csv
import json
import pandas as pd
import numpy as np

df = pd.read_csv('생활이동_행정동_202205\생활이동_행정동_2022.05_02시.csv',encoding='cp949')
df.columns = ['month','day','arriveTime','startArr','endArr','sex','age','moveType','averageMoveTime','peopleNum']

df = df.drop(['month','startArr','age','sex','moveType','averageMoveTime'],axis=1)
df = df.loc[:,['endArr','peopleNum']]
df.sort_values('endArr')


print(df)
print(df[df['endArr']==1101053])
    
f = open('dong_code.csv','r')
rdr = csv.reader(f)
dic = dict()

totalAnalysis = pd.DataFrame(columns=['name','num'])

for line in rdr:
    dic[line[2]] = line[3]

loc = 0

for data in dic:
    if data.isdigit() is False:
        continue
    idata = int(data)
    ndf = df[df['endArr']==idata]
    ndf = ndf.sort_index()
    ndf = ndf.reset_index()
    total = 0
    for idx in range(0,len(ndf)):
        temp = 0
        if ndf.iloc[idx,2] is "*":
            temp = 1.5
        else:
            temp = float(ndf.iloc[idx,2])
        total += temp
    loc += 1
    totalAnalysis.loc[loc] = [dic[data],total]

totalAnalysis.to_excel('test2.xlsx')
        
        

    






'''
groups = df.groupby('endArr')
result = dict(list(groups))

for dong in result:
    print(type(result[dong]['peopleNum'][0]))
        


#print(groups.describe())







f = open('dong_code.csv','r')
f2 = open('생활이동_행정동_202205\생활이동_행정동_2022.05_00시.csv','r')
rdr = csv.reader(f)
rdr2 = csv.reader(f2)



class moveData:
    nameCode = dict()
    totalMove = 0
    
dongList = []


for line in rdr:
    temp = moveData()
    dic = dict()
    dic[line[2]] = line[3]
    temp.nameCode = dic
    dongList.append(temp)

for line in rdr2:
    
    line[4] 



'''