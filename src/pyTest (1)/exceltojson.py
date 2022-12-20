import pandas as pd


df = pd.read_excel('data/result/final_result.xlsx')

df.to_json('final_result.json')



