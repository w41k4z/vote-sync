import oracledb
import json
import geojson

tana_district_id = 26 # By default, should be 1. Change according to your database

connection = oracledb.connect(
    user="election",
    password="election",
    dsn="localhost/XEPDB1"
)
cursor = connection.cursor()

coms = {}
with open('District.geojson', 'r') as f:
    geojson_data = geojson.load(f)
    for each in geojson_data["features"]:
        district = each["properties"]["DISTRICT"]
        if district == 'Antananarivo Renivohitra':
            code = 110701
            str_geojson = json.dumps(each["geometry"])
            
            script = "INSERT INTO municipalites(code, nom, id_district, nom_district, geojson) values(:code, :nom, :id_district, :nom_district, :geojson)"
            # script = "UPDATE municipalites SET geojson = :geojson WHERE code = :code"
            cursor.execute(script, {
                "code": code,
                "nom": "Antananarivo Renivohitra",
                "id_district": tana_district_id,
                "nom_district": "Antananarivo Renivohitra",
                "geojson": str_geojson
            })
            break
    cursor.execute("commit")
    print('All ok')
    cursor.close()
    connection.close()