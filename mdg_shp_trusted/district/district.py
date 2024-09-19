import oracledb
import json
import csv

dist = {}
# Raw region
with open('../liste-bv.csv', 'r') as csvfile:
    file_content = csv.reader(csvfile, delimiter=';')
    i = 0
    districts = []
    for row in file_content:
        if i == 0:
            i+=1
            continue
        the_district = row[2]
        bv_code = row[6]
        if bv_code.startswith("1"):
            if the_district not in districts:
                districts.append(the_district)
                dist[the_district] = {"id": bv_code[0:4]}
            
# Raw region + column P_CODE and R_CODE
with open('District.csv', 'r') as csvfile:
    file_content = csv.reader(csvfile, delimiter=',')
    i = 0
    for row in file_content:
        if i == 0:
            i+=1
            continue
        if (row[3].startswith('MDG1')):
            dist[row[2].upper()]['P_CODE'] = row[3]
            dist[row[2].upper()]['D_CODE'] = row[4]
            dist[row[2].upper()]['REG_PCODE'] = row[6]
            dist[row[2].upper()]['R_CODE'] = row[7]

# Cleaned Region (with spatial data)
import geojson

with open('District.geojson', 'r') as f:
    geojson_data = geojson.load(f)
    connection = oracledb.connect(
        user="election",
        password="election",
        dsn="localhost/XEPDB1"
    )
    cursor = connection.cursor()
    for each in geojson_data["features"]:
        if each["properties"]["P_CODE"].startswith('MDG1'):
            id = dist[each["properties"]["DISTRICT"].upper()]["id"]
            p_code = dist[each["properties"]["DISTRICT"].upper()]["P_CODE"]
            d_code = dist[each["properties"]["DISTRICT"].upper()]["D_CODE"]
            reg_pcode = dist[each["properties"]["DISTRICT"].upper()]["REG_PCODE"]
            r_code = dist[each["properties"]["DISTRICT"].upper()]["R_CODE"]
            district = each["properties"]["DISTRICT"].upper()
            str_geojson = json.dumps(each["geometry"])
        
            script = "INSERT INTO imported_districts(code, p_code, d_code, reg_pcode, r_code, nom, geojson) values(:id, :p_code, :d_code, :reg_pcode, :r_code, :district, :geojson)"
            cursor.execute(script, {
                "id": id,
                "p_code": p_code,
                "d_code": d_code,
                "reg_pcode": reg_pcode,
                "r_code": r_code,
                "district": district,
                "geojson": str_geojson
            })
            print('Tafiditra \n')
    cursor.execute("commit")
    cursor.close()
    connection.close()