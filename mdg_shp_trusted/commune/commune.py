import oracledb
import json
import csv

coms = {}
# Raw region + column P_CODE and R_CODE
with open('Communes.csv', 'r') as csvfile:
    file_content = csv.reader(csvfile, delimiter=',')
    i = 0
    for row in file_content:
        if i == 0:
            i+=1
            continue
        coms[row[1].upper()] = {}
        coms[row[1].upper()]['P_CODE'] = row[2]
        coms[row[1].upper()]['C_CODE'] = row[3]
        coms[row[1].upper()]['DIST_PCODE'] = row[5]
        coms[row[1].upper()]['R_CODE'] = row[9]

# Cleaned Region (with spatial data)
import geojson

with open('Communes.geojson', 'r') as f:
    geojson_data = geojson.load(f)
    connection = oracledb.connect(
        user="election",
        password="election",
        dsn="localhost/XEPDB1"
    )
    cursor = connection.cursor()
    for each in geojson_data["features"]:
        p_code = coms[each["properties"]["COMMUNE"].upper()]["P_CODE"]
        c_code = coms[each["properties"]["COMMUNE"].upper()]["C_CODE"]
        dist_pcode = coms[each["properties"]["COMMUNE"].upper()]["DIST_PCODE"]
        r_code = coms[each["properties"]["COMMUNE"].upper()]["R_CODE"]
        commune = each["properties"]["COMMUNE"].upper()
        str_geojson = json.dumps(each["geometry"])
        
        script = "INSERT INTO imported_communes(p_code, c_code, dist_pcode, r_code, nom, geojson) values(:p_code, :c_code, :dist_pcode, :r_code, :commune, :geojson)"
        cursor.execute(script, {
            "p_code": p_code,
            "c_code": c_code,
            "dist_pcode": dist_pcode,
            "r_code": r_code,
            "commune": commune,
            "geojson": str_geojson
        })
    cursor.execute("commit")
    print('All ok')
    cursor.close()
    connection.close()