import pandas as pd

data_path = 'data/'
excel_data_path = data_path + 'result/final_result.xlsx'
output_path = data_path + 'result/final_result.json'

df = pd.read_excel(excel_data_path)

df.to_json(output_path)



