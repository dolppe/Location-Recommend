# 필요한 모듈 import
import csv
import json
import pandas as pd
import numpy as np



f = open('dong_code.csv','r')
rdr = csv.reader(f)
dongList = dict()

for line in rdr:
    dongList[line[2]] = line[3]

dfList_org = []
dfList_temp = []



print("데이터 로드 중")
for idx in range(0,24):
    
    sidx = str(idx)

    if (idx<10):
        sidx = '0' + sidx    
    
    tempString = '생활이동_행정동_202205\생활이동_행정동_2022.05_'+ sidx + '시.csv'
    temp = pd.read_csv(tempString,encoding='cp949')
    
    dfList_org.append(temp)



print("데이터 로드 완료")

dfList_temp = dfList_org


total_word = []
total_word.append('day')
total_word.append('arriveTime')
total_word.append('sex')
total_word.append('age')

#4번 반복 끝나면 변경
month = '05'
##### 반복마다 word랑 dfList 변경, dropList, selectList도 변경
##### 제일 아래에groupby도 변경

for total_idx in range(0,len(total_word)):
    dropList = ['month','startArr','moveType','averageMoveTime',
                'day','arriveTime','age','sex']
    selectList = ['endArr','peopleNum']

    dropList.remove(total_word[total_idx])
    selectList.insert(1,total_word[total_idx])


    dfList = dfList_temp
    print("데이터 정리")
    for idx in range(0,len(dfList)):
        dfList[idx].columns = ['month','day','arriveTime','startArr','endArr','sex','age','moveType','averageMoveTime','peopleNum']
        dfList[idx].sort_values(by=['endArr'])
        dfList[idx] = dfList[idx].drop(dropList,axis=1)
        dfList[idx] = dfList[idx].loc[:,selectList]
        dfList[idx].loc[dfList[idx]['peopleNum']=='*','peopleNum'] = '1.5'
        dfList[idx] = dfList[idx].astype({'peopleNum':'float'})

    func = lambda x: dongList.get(x)

    for idx in range(0,len(dfList)):
        dfList[idx] = dfList[idx].astype({'endArr':'str'})
        dfList[idx]['name'] = dfList[idx].endArr.map(func)

    result = dfList[0]
    print("결과 만들기")
    for idx in range(1,len(dfList)):
        result = pd.concat([result,dfList[idx]])

    print(len(result))

    resultList = []
    resultList.append('name')
    resultList.append(total_word[total_idx])
    ###############
    result = result.groupby(resultList).sum()



    result.to_excel(month+"_"+total_word[total_idx]+".xlsx")
    print(result)
    dfList_temp = dfList_org



