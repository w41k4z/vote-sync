import oracledb
import json
import csv

dist = {}
# Raw region
with open('../result.csv', 'r') as csvfile:
    file_content = csv.reader(csvfile, delimiter=';')
    i = 0
    districts = []
    for row in file_content:
        if i == 0:
            i+=1
            continue
        the_district = row[1]
        if the_district == 'ANTANANARIVO-ATSIMOND':
            the_district = 'ANTANANARIVO ATSIMONDRANO'
        if the_district == 'ANTANANARIVO AVARADRA':
            the_district = 'ANTANANARIVO AVARADRANO'
        if the_district == 'MIDONGY SUD':
            the_district = 'MIDONGY ATSIMO'
        if the_district == 'MANANARA-NORD':
            the_district = 'MANANARA AVARATRA'
        if the_district == 'ANTANAMBAO MANAMPON':
            the_district = 'ANTANAMBAO MANAMPONTSY'
        if the_district == 'ANOSIBE AN ALA':
            the_district = 'ANOSIBE-AN\'ALA'
        if the_district == 'ANKAZOABO SUD':
            the_district = 'ANKAZOABO'
        if the_district == 'BETIOKY SUD':
            the_district = 'BETIOKY ATSIMO'
        if the_district == 'TAOLANARO':
            the_district = 'TAOLAGNARO'
        if the_district == 'AMBOASARY SUD':
            the_district = 'AMBOASARY ATSIMO'
        if the_district.startswith('ANTANANARIVO_'):
            the_district = 'ANTANANARIVO RENIVOHITRA'
        if the_district not in districts:
            districts.append(the_district)
            dist[the_district] = {"id": row[5].replace(' ', '')[0:4]}
            
# Raw region + column P_CODE and R_CODE
with open('District.csv', 'r') as csvfile:
    file_content = csv.reader(csvfile, delimiter=',')
    i = 0
    for row in file_content:
        if i == 0:
            i+=1
            continue
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