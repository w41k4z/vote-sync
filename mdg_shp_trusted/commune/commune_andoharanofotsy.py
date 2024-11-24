import oracledb
import json

connection = oracledb.connect(
    user="election",
    password="election",
    dsn="localhost/XEPDB1"
)
cursor = connection.cursor()

# Cleaned Region (with spatial data)
import geojson

with open('Communes.geojson', 'r') as f:
    geojson_data = geojson.load(f)
    for each in geojson_data["features"]:
        p_code = each["properties"]["P_CODE"]
        commune = each["properties"]["COMMUNE"]
        if commune == 'Andoharanofotsy':
            str_geojson = json.dumps(each["geometry"])
            
            script = "INSERT INTO communes(id_municipalite, code, id_district, nom, geojson, etat) values(:municipalityId, :code, :districtId, :commune, :geojson, :status)"
            cursor.execute(script, {
                "municipalityId": 1,
                "code": "110511",
                "districtId": 47,
                "commune": commune.upper(),
                "geojson": str_geojson,
                "status": 0
            })
    cursor.execute("commit")
    print('All ok')
    cursor.close()
    connection.close()