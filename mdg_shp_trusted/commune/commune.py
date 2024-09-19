import oracledb
import json
import csv

connection = oracledb.connect(
    user="election",
    password="election",
    dsn="localhost/XEPDB1"
)
cursor = connection.cursor()

coms = {}
with open('../liste-bv.csv', 'r') as bvfile:
    file_content = csv.reader(bvfile, delimiter=';')
    i = 0
    communes = []
    for row in file_content:
        if i == 0:
            i+=1
            continue
        bv_code = row[6]
        commune = row[3].upper()
        if commune in ['4E ARRONDISSEMENT', '5E ARRONDISSEMENT']: 
            region = row[1].upper()
            district = row[2].upper()
            key = commune
            if key in communes:
                continue
            communes.append(key)
            code = row[6][0:6]
            coms[key] = {'id': code}
        
# Raw region + column P_CODE and R_CODE
with open('Communes.csv', 'r') as csvfile:
    file_content = csv.reader(csvfile, delimiter=',')
    i = 0
    for row in file_content:
        if i == 0:
            i+=1
            continue
        commune = row[1].upper()
        if commune in ['4E ARRONDISSEMENT', '5E ARRONDISSEMENT']:
            key = commune
            coms[key]['P_CODE'] = row[2]
            coms[key]['DIST_PCODE'] = row[5]

# Cleaned Region (with spatial data)
import geojson

with open('Communes.geojson', 'r') as f:
    geojson_data = geojson.load(f)
    for each in geojson_data["features"]:
        p_code = each["properties"]["P_CODE"]
        commune = each["properties"]["COMMUNE"].upper()
        if commune in ['4E ARRONDISSEMENT', '5E ARRONDISSEMENT']:
            key = commune
            code = coms[key]["id"]
            dist_pcode = coms[key]["DIST_PCODE"]
            str_geojson = json.dumps(each["geometry"])
            
            script = "INSERT INTO imported_communes(code, p_code, dist_pcode, nom, geojson) values(:code, :p_code, :dist_pcode, :commune, :geojson)"
            cursor.execute(script, {
                "code": code,
                "p_code": p_code,
                "dist_pcode": dist_pcode,
                "commune": commune,
                "geojson": str_geojson
            })
    cursor.execute("commit")
    print('All ok')
    cursor.close()
    connection.close()