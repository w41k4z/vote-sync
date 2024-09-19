import oracledb
import json
import csv

connection = oracledb.connect(
    user="election",
    password="election",
    dsn="localhost/XEPDB1"
)
cursor = connection.cursor()

foks = {}
with open('../liste-bv.csv', 'r') as bvfile:
    file_content = csv.reader(bvfile, delimiter=';')
    i = 0
    for row in file_content:
        if i == 0:
            i+=1
            continue
        bv_code = row[6]
        fokontany = row[4].upper()
        if fokontany in ['ANDAVAMAMBA AMBILANIBE', 'AMPANGABE ANJANAKINIFOLO', 'AMBODIVOANJO AMBOHIJATOVO FARA', 'IVANDRY']: 
            key = fokontany
            code = row[6][0:8]
            foks[key] = {'id': code}
        
# Raw region + column P_CODE and R_CODE
with open('Fokontany.csv', 'r') as csvfile:
    file_content = csv.reader(csvfile, delimiter=',')
    i = 0
    for row in file_content:
        if i == 0:
            i+=1
            continue
        fokontany = row[1].upper()
        if fokontany in ['ANDAVAMAMBA AMBILANIBE', 'AMPANGABE ANJANAKINIFOLO', 'AMBODIVOANJO AMBOHIJATOVO FARA', 'IVANDRY']:
            key = fokontany
            foks[key]['P_CODE'] = row[2]
            foks[key]['COM_PCODE'] = row[5]

# Cleaned Region (with spatial data)
import geojson

with open('Fokontany.geojson', 'r') as f:
    geojson_data = geojson.load(f)
    for each in geojson_data["features"]:
        p_code = each["properties"]["P_CODE"]
        fokontany = each["properties"]["FOKONTANY"].upper()
        if fokontany in ['ANDAVAMAMBA AMBILANIBE', 'AMPANGABE ANJANAKINIFOLO', 'AMBODIVOANJO AMBOHIJATOVO FARA', 'IVANDRY']:
            key = fokontany
            code = foks[key]["id"]
            com_pcode = foks[key]["COM_PCODE"]
            str_geojson = json.dumps(each["geometry"])
            
            script = "INSERT INTO imported_fokontany(code, p_code, com_pcode, nom, geojson) values(:code, :p_code, :com_pcode, :fokontany, :geojson)"
            cursor.execute(script, {
                "code": code,
                "p_code": p_code,
                "com_pcode": com_pcode,
                "fokontany": fokontany,
                "geojson": str_geojson
            })
    cursor.execute("commit")
    print('All ok')
    cursor.close()
    connection.close()