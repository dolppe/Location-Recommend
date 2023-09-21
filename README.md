# Location-Recommend
모임에서 약속장소를 추천해주는 어플리케이션.

## Summary
각 시작 위치에 따른 중간장소를 계산하여 주변의 장소를 추천해줍니다.  
장소 추천시 해당 장소의 특징을 알려줍니다.  
(선호 연령대, 선호 성별, 해당 시간의 평균 혼잡도 등)

장소의 특징은 서울 생활이동 데이터를 이용하여 결정합니다.  

![img_move-preview](https://github.com/dolppe/Location-Recommend/assets/35285591/dfc84b2d-0f2e-4b85-be31-510858d87b18)
(https://data.seoul.go.kr/dataVisual/seoul/seoulLivingMigration.do)  

생활이동 데이터를 분석하고, 클러스터링을 이용하여 장소의 특징을 뽑아냅니다.


## Structure
크게 data, src로 구성되며 리소스는 data, 코드는 src 폴더에 존재합니다.  
src 폴더에는 data_src, ktttt 폴더로 구성됩니다.  
data_src는 python을 이용한 데이터 분석 코드가 존재합니다.  
ktttt는 해당 데이터를 이용하여 약속장소를 추천해주는 어플리케이션 관련 코드입니다.

```sh
Location-Recommend
 ┣ data
 ┃ ┣ 생활이동 데이터 폴더                         
 ┃ ┃ ┗ 생활이동 데이터 엑셀                       -> 원본 데이터
 ┃ ┣ reference_data                             -> 참조 데이터
 ┃ ┣ result                                     -> 클러스터링 결과 폴더
 ┃ ┗ saved                                      -> 분석 등 중간 저장 폴더
 ┣ src                                          -> main 코드 폴더
 ┃ ┣ data_src                                   -> 데이터 처리 코드
 ┃ ┃ ┣ data_cluster                             
 ┃ ┃ ┃ ┣ cluster.ipynb                          -> 클러스터링 코드
 ┃ ┃ ┃ ┗ groupby.ipynb                          -> 그룹분류 코드
 ┃ ┃ ┣ new_analysis.ipynb                       -> 데이터 분석 코드
 ┃ ┃ ┗ exceltojson.py                           -> json 변환 코드
 ┃ ┣ ktttt                                      -> 약속장소 추천 어플리케이션 코드
 ┃ ┃ ┣ app/src/main                             
 ┃ ┃ ┃ ┣ assets                                 -> 데이터 분석 결과 폴더
 ┃ ┃ ┃ ┣ java/com/example/ktttt                 
 ┃ ┃ ┃ ┃ ┣ MainListAdapter.kt                   -> 리스트 어뎁터 모듈
 ┃ ┃ ┃ ┃ ┣ MapActivity.kt                       -> App main 코드
 ┃ ┃ ┃ ┃ ┣ ResultData.ky                        -> 데이터 분석 파일 처리 코드
 ┗ ┗ ┗ ┗ ┗ SmallestEnclosingCircle.java         -> 중간 장소 계산 코드
 
```

## Requirements
- Python 3.9.7
- Android Studio (Giraffe, 2022.3.1)
